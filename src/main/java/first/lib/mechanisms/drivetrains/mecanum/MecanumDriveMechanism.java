package first.lib.mechanisms.drivetrains.mecanum;

import org.littletonrobotics.junction.Logger;
import org.wpilib.command3.Command;
import org.wpilib.command3.Mechanism;
import org.wpilib.math.geometry.Pose2d;
import org.wpilib.math.geometry.Translation2d;
import org.wpilib.math.kinematics.ChassisVelocities;
import org.wpilib.math.kinematics.MecanumDriveKinematics;
import org.wpilib.math.kinematics.MecanumDriveOdometry;
import org.wpilib.math.kinematics.MecanumDriveWheelVelocities;
import org.wpilib.driverstation.Alliance;
import org.wpilib.driverstation.MatchState;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.config.PIDConstants;
import com.pathplanner.lib.config.RobotConfig;
import com.pathplanner.lib.controllers.PPHolonomicDriveController;
import com.pathplanner.lib.util.DriveFeedforwards;

import first.lib.hardware.imus.IMU6;
import first.robot.GlobalConstants;
import java.util.function.Supplier;

public class MecanumDriveMechanism implements Mechanism {
    private final String name;
    private final MecanumDriveIO io;
    private final IMU6 gyro;
    private final MecanumDriveKinematics kinematics;
    private final MecanumDriveOdometry odometry;
    private final MecanumDriveIOInputsAutoLogged inputs = new MecanumDriveIOInputsAutoLogged();
    private Pose2d currentPose = new Pose2d();

    public MecanumDriveMechanism(String name, MecanumDriveConfig config, IMU6 gyro, Translation2d fl, Translation2d fr, Translation2d rl, Translation2d rr) {
        this.name = name;
        this.gyro = gyro;
        this.kinematics = new MecanumDriveKinematics(fl, fr, rl, rr);
        
        boolean sim = GlobalConstants.currentMode.equals(GlobalConstants.Mode.SIM);
        this.io = sim ? new MecanumDriveSimulation(config) : new MecanumDriveReal(config);

        this.odometry = new MecanumDriveOdometry(kinematics, gyro.getRotation2d(), io.getWheelPositions());

        // configurePathPlanner();
    }

    // pathplanner wasnt working for mecanum, needs v2 sub even though its on v3
    // private void configurePathPlanner() { 
    //     try {
    //         RobotConfig config = RobotConfig.fromGUISettings();
    //         AutoBuilder.configure(
    //             this::getPose,
    //             this::resetPose,
    //             this::getRobotRelativeVelocities,
    //             this::driveRobotRelative,
    //             new PPHolonomicDriveController(
    //                 new PIDConstants(5.0, 0.0, 0.0), 
    //                 new PIDConstants(5.0, 0.0, 0.0)  
    //             ),
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
        odometry.resetPosition(gyro.getRotation2d(), io.getWheelPositions(), pose);
    }

    public ChassisVelocities getRobotRelativeVelocities() {
        return kinematics.toChassisVelocities(io.getWheelVelocities());
    }

    public void driveRobotRelative(ChassisVelocities speeds, DriveFeedforwards feedforwards) {
        MecanumDriveWheelVelocities wheelVelocities = kinematics.toWheelVelocities(speeds);
        io.setDesiredWheelVelocities(wheelVelocities);
    }

    public Command drive(Supplier<Double> vx, Supplier<Double> vy, Supplier<Double> omega) {
        return runRepeatedly(() -> {
            ChassisVelocities speeds = new ChassisVelocities(vx.get(), vy.get(), omega.get());
            var wheelVelocities = kinematics.toWheelVelocities(speeds);
            io.setDesiredWheelVelocities(wheelVelocities);
        }).named(name + ".DriveCommand");
    }

    @Override
    public Command idle() {
        return runRepeatedly(() -> io.stop()).named(name + ".Idle");
    }

    @Override public String getName() { return name; }

    public void periodic() {
        io.periodic();
        io.updateInputs(inputs);
        Logger.processInputs(name, inputs);

        currentPose = odometry.update(gyro.getRotation2d(), io.getWheelPositions());
        Logger.recordOutput(name + "/Pose", currentPose);
    }
}