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

import first.lib.hardware.imus.IMU6;
import first.lib.hardware.imus.Pigeon2imu;
import first.robot.GlobalConstants;

public class SwerveDriveMechanism implements Mechanism {

    private final String name;
    private final SwerveModuleIO[] modules;
    private final IMU6 gyro;
    private final SwerveDriveKinematics kinematics;
    private final SwerveDriveOdometry odometry;
    private Pose2d currentPose = new Pose2d();
    private final SwerveModuleIOInputsAutoLogged[] inputs;
    private SwerveModuleVelocity[] targetStates;

    public SwerveDriveMechanism(String name, SwerveModuleConfig[] moduleConfigs, IMU6 gyro, Translation2d... moduleTranslations) {
        this.name = name;
        this.gyro = gyro;
        this.kinematics = new SwerveDriveKinematics(moduleTranslations);
        
        boolean sim = GlobalConstants.currentMode.equals(GlobalConstants.Mode.SIM);
        this.modules = new SwerveModuleIO[moduleConfigs.length];
        this.inputs = new SwerveModuleIOInputsAutoLogged[moduleConfigs.length];
        this.targetStates = new SwerveModuleVelocity[moduleConfigs.length];

        for (int i = 0; i < moduleConfigs.length; i++) {
            this.modules[i] = sim 
                ? new SwerveModuleSimulation(moduleConfigs[i]) 
                : new SwerveModuleReal(moduleConfigs[i]);
            this.inputs[i] = new SwerveModuleIOInputsAutoLogged();
            this.targetStates[i] = new SwerveModuleVelocity(0.0, new Rotation2d());
        }

        this.odometry = new SwerveDriveOdometry(kinematics, gyro.getRotation2d(), getModulePositions());

        // configurePathPlanner();
    }

    // private void configurePathPlanner() { ... }

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

    public void driveRobotRelative(ChassisVelocities speeds) {
        SwerveModuleVelocity[] velocities = kinematics.toSwerveModuleVelocities(speeds);
        velocities = SwerveDriveKinematics.desaturateWheelVelocities(velocities, 4.5);
        
        // Cache target states for AdvantageScope visualization
        this.targetStates = velocities;

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
            driveRobotRelative(speeds);
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
            for (int i = 0; i < modules.length; i++) {
                modules[i].stop();
                targetStates[i] = new SwerveModuleVelocity(0.0, targetStates[i].angle);
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
        SwerveModuleVelocity[] measuredStates = new SwerveModuleVelocity[modules.length];

        for (int i = 0; i < modules.length; i++) {
            modules[i].periodic();
            modules[i].updateInputs(inputs[i]);
            Logger.processInputs(name + "/Module" + i, inputs[i]);
            measuredStates[i] = modules[i].getVelocity();
        }

        // --- Feed kinematics chassis speeds into the gyro simulation ---
        ChassisVelocities robotSpeeds = getRobotRelativeSpeeds();
        if (gyro instanceof Pigeon2imu && GlobalConstants.currentMode.equals(GlobalConstants.Mode.SIM)) {
            ((Pigeon2imu) gyro).simPeriodic(robotSpeeds.omega, 0.02);
        }

        currentPose = odometry.update(gyro.getRotation2d(), getModulePositions());
        
        // --- Full AdvantageKit Logging Outputs for AdvantageScope ---
        Logger.recordOutput(name + "/Pose", currentPose);
        Logger.recordOutput(name + "/GyroHeading", gyro.getHeading());
        Logger.recordOutput(name + "/ModuleStates", measuredStates);
        Logger.recordOutput(name + "/ModuleTargetStates", targetStates);
        Logger.recordOutput(name + "/ChassisSpeeds", robotSpeeds);
    }
}