package first.lib.mechanisms.drivetrains.swerve;

import java.util.function.Supplier;

import org.littletonrobotics.junction.Logger;
import org.wpilib.command3.Command;
import org.wpilib.command3.Mechanism;
import org.wpilib.math.geometry.Pose2d;
import org.wpilib.math.geometry.Rotation2d;
import org.wpilib.math.geometry.Translation2d;
import org.wpilib.math.kinematics.ChassisVelocities;
import org.wpilib.math.kinematics.SwerveDriveKinematics;
import org.wpilib.math.kinematics.SwerveDriveOdometry;
import org.wpilib.math.kinematics.SwerveModulePosition;
import org.wpilib.math.kinematics.SwerveModuleVelocity;
import org.wpilib.driverstation.Alliance;
import org.wpilib.driverstation.MatchState;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.config.PIDConstants;
import com.pathplanner.lib.config.RobotConfig;
import com.pathplanner.lib.controllers.PPHolonomicDriveController;
import com.pathplanner.lib.util.DriveFeedforwards;

import first.lib.hardware.imus.imu6;
import first.lib.mechanisms.drivetrains.swerve.SwerveModuleIOInputsAutoLogged;
import first.robot.globalConstants;

public class SwerveDriveMechanism implements Mechanism {

    private final String name;
    private final SwerveModuleIO[] modules;
    private final imu6 gyro;
    private final SwerveDriveKinematics kinematics;
    private final SwerveDriveOdometry odometry;
    private Pose2d currentPose = new Pose2d();
    private final SwerveModuleIOInputsAutoLogged[] inputs;

    public SwerveDriveMechanism(String name, SwerveModuleConfig[] moduleConfigs, imu6 gyro, Translation2d... moduleTranslations) {
        this.name = name;
        this.gyro = gyro;
        this.kinematics = new SwerveDriveKinematics(moduleTranslations);
        
        boolean sim = globalConstants.currentMode.equals(globalConstants.Mode.SIM);
        this.modules = new SwerveModuleIO[moduleConfigs.length];
        this.inputs = new SwerveModuleIOInputsAutoLogged[moduleConfigs.length];

        for (int i = 0; i < moduleConfigs.length; i++) {
            this.modules[i] = sim 
                ? new SwerveModuleSimulation(moduleConfigs[i]) 
                : new SwerveModuleReal(moduleConfigs[i]);
            this.inputs[i] = new SwerveModuleIOInputsAutoLogged();
        }

        this.odometry = new SwerveDriveOdometry(kinematics, gyro.getRotation2d(), getModulePositions());

        // configurePathPlanner();
    }

    // private void configurePathPlanner() {
    //     try {
    //         RobotConfig config = RobotConfig.fromGUISettings();
    //         AutoBuilder.configure(
    //             this::getPose,
    //             this::resetPose,
    //             this::getRobotRelativeSpeeds,
    //             this::driveRobotRelative,
    //             new PPHolonomicDriveController(
    //                 new PIDConstants(5.0, 0.0, 0.0), 
    //                 new PIDConstants(5.0, 0.0, 0.0)  
    //             ),
    //             config,
    //             () -> {
    //                 var alliance = MatchState.getAlliance();
    //                 return alliance.isPresent() && alliance.get() == Alliance.RED;
    //             },
    //             this
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
        odometry.resetPosition(gyro.getRotation2d(), getModulePositions(), pose);
    }

    public ChassisVelocities getRobotRelativeSpeeds() {
        SwerveModuleVelocity[] moduleVelocities = new SwerveModuleVelocity[modules.length];
        for (int i = 0; i < modules.length; i++) {
            moduleVelocities[i] = modules[i].getVelocity();
        }
        return kinematics.toChassisVelocities(moduleVelocities);
    }

    public void driveRobotRelative(ChassisVelocities speeds, DriveFeedforwards feedforwards) {
        SwerveModuleVelocity[] velocities = kinematics.toSwerveModuleVelocities(speeds);
        velocities = SwerveDriveKinematics.desaturateWheelVelocities(velocities, 4.5);
        for (int i = 0; i < modules.length; i++) {
            modules[i].setDesiredVelocity(velocities[i]);
        }
    }

    public Command drive(Supplier<Double> vxMetersPerSecond, Supplier<Double> vyMetersPerSecond, Supplier<Double> omegaRadiansPerSecond) {
        return runRepeatedly(() -> {
            Rotation2d robotAngle = getPose().getRotation();
            Translation2d fieldVel = new Translation2d(vxMetersPerSecond.get(), vyMetersPerSecond.get());
            Translation2d robotVel = fieldVel.rotateBy(robotAngle.unaryMinus());

            ChassisVelocities speeds = new ChassisVelocities(robotVel.getX(), robotVel.getY(), omegaRadiansPerSecond.get());
            driveRobotRelative(speeds, null);
        }).named(name + ".DriveCommand");
    }

    public SwerveModulePosition[] getModulePositions() {
        SwerveModulePosition[] positions = new SwerveModulePosition[modules.length];
        for (int i = 0; i < modules.length; i++) {
            positions[i] = modules[i].getPosition();
        }
        return positions;
    }

    @Override
    public Command idle() {
        return runRepeatedly(() -> {
            for (var module : modules) {
                module.stop();
            }
        }).named(name + ".Idle");
    }

    @Override
    public String getName() {
        return name;
    }

    public void resetHeading() {
        gyro.resetYaw();
    }

    public void periodic() {
        for (int i = 0; i < modules.length; i++) {
            modules[i].periodic();
            modules[i].updateInputs(inputs[i]);
            Logger.processInputs(name + "/Module" + i, inputs[i]);
        }

        currentPose = odometry.update(gyro.getRotation2d(), getModulePositions());
        
        Logger.recordOutput(name + "/Pose", currentPose);
        Logger.recordOutput(name + "/GyroHeading", gyro.getHeading());
    }
}