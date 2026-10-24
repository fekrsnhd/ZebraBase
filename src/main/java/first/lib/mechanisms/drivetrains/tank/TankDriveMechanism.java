package first.lib.mechanisms.drivetrains.tank;

import java.util.function.Supplier;

import org.littletonrobotics.junction.Logger;
import org.wpilib.command3.Command;
import org.wpilib.command3.Mechanism;
import org.wpilib.math.geometry.Pose2d;
import org.wpilib.math.kinematics.DifferentialDriveKinematics;
import org.wpilib.math.kinematics.DifferentialDriveOdometry;
import org.wpilib.math.kinematics.DifferentialDriveWheelVelocities;
import org.wpilib.math.kinematics.ChassisVelocities;
import org.wpilib.driverstation.Alliance;
import org.wpilib.driverstation.MatchState;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.config.RobotConfig;
import com.pathplanner.lib.controllers.PPLTVController;
import com.pathplanner.lib.util.DriveFeedforwards;

import first.lib.hardware.imus.imu6;
import first.robot.globalConstants;

public class TankDriveMechanism implements Mechanism {
    private final String name;
    private final TankDriveIO io;
    private final imu6 gyro;
    private final DifferentialDriveKinematics kinematics;
    private final DifferentialDriveOdometry odometry;
    private final TankDriveIOInputsAutoLogged inputs = new TankDriveIOInputsAutoLogged();
    private Pose2d currentPose = new Pose2d();

    public TankDriveMechanism(String name, TankDriveConfig config, imu6 gyro) {
        this.name = name;
        this.gyro = gyro;
        this.kinematics = new DifferentialDriveKinematics(config.trackWidthMeters);
        
        boolean sim = globalConstants.currentMode.equals(globalConstants.Mode.SIM);
        this.io = sim ? new TankDriveSimulation(config) : new TankDriveReal(config);
        this.odometry = new DifferentialDriveOdometry(gyro.getRotation2d(), 0, 0);

        // configurePathPlanner();
    }

    // private void configurePathPlanner() {
    //     try {
    //         RobotConfig config = RobotConfig.fromGUISettings();
    //         AutoBuilder.configure(
    //             this::getPose,
    //             this::resetPose,
    //             this::getRobotRelativeVelocities,
    //             this::driveRobotRelative,
    //             new PPLTVController(0.02),
    //             config,
    //             () -> {
    //                 var alliance = MatchState.getAlliance();
    //                 return alliance.isPresent() && alliance.get() == Alliance.RED;
    //             }
    //         );
    //     } catch (Exception e) {
    //         System.out.println("Failed to load PathPlanner RobotConfig: " + e.getMessage());
    //         for (StackTraceElement element : e.getStackTrace()) {
    //             System.out.println(element);
    //         }
    //     }
    // }

    public Pose2d getPose() {
        return odometry.getPose();
    }

    public void resetPose(Pose2d pose) {
        odometry.resetPosition(gyro.getRotation2d(), inputs.leftPositionMeters, inputs.rightPositionMeters, pose);
    }

    public ChassisVelocities getRobotRelativeVelocities() {
        return kinematics.toChassisVelocities(
            new DifferentialDriveWheelVelocities(inputs.leftVelocityMetersPerSecond, inputs.rightVelocityMetersPerSecond)
        );
    }

    public void driveRobotRelative(ChassisVelocities speeds, DriveFeedforwards feedforwards) {
        var wheelVelocities = kinematics.toWheelVelocities(speeds);
        // Simple velocity-to-voltage conversion or feedforward mapping
        io.setVoltage(wheelVelocities.left * 2.0, wheelVelocities.right * 2.0);
    }

    public Command arcadeDrive(Supplier<Double> xSpeed, Supplier<Double> zRotation) {
        return runRepeatedly(() -> {
            double leftVolt = (xSpeed.get() + zRotation.get()) * 12.0;
            double rightVolt = (xSpeed.get() - zRotation.get()) * 12.0;
            io.setVoltage(leftVolt, rightVolt);
        }).named(name + ".ArcadeDrive");
    }

    @Override
    public Command idle() {
        return runRepeatedly(() -> io.stop()).named(name + ".Idle");
    }

    @Override public String getName() { return name; }

    public DifferentialDriveKinematics getKinematics() { return kinematics; }

    public void periodic() {
        io.periodic();
        io.updateInputs(inputs);
        Logger.processInputs(name, inputs);

        currentPose = odometry.update(gyro.getRotation2d(), inputs.leftPositionMeters, inputs.rightPositionMeters);
        Logger.recordOutput(name + "/Pose", currentPose);
    }
}