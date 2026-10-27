package first.robot.mechanisms.swerve;

import static org.wpilib.units.Units.MetersPerSecond;
import static org.wpilib.units.Units.RadiansPerSecond;
import static org.wpilib.units.Units.RotationsPerSecond;

import org.wpilib.math.geometry.Translation2d;
import org.wpilib.math.system.DCMotor;
import org.wpilib.units.measure.LinearVelocity;

import first.lib.hardware.encoders.hybrid.CanCoderEncoder;
import first.lib.hardware.imus.IMU6;
import first.lib.hardware.motors.canMotors.CANMotorConfig;
import first.lib.hardware.motors.canMotors.TalonFXMotor;
import first.lib.mechanisms.drivetrains.swerve.SwerveDriveMechanism;
import first.lib.mechanisms.drivetrains.swerve.SwerveModuleConfig;

public class SwerveConstants {
    // --- Drivetrain Bus & Physical Constants ---
    public static final String kCANBusName = "swerve";
    public static final double kDriveRatio = 6.75;
    public static final double kSteerRatio = 12.8;
    public static final double kWheelRadius = 0.0508;
    public static final double kRobotWeight = 40.0;

    public static final LinearVelocity kSpeedAt12Volts = MetersPerSecond.of(5.12);
    public static final double kMaxAngularRate = RotationsPerSecond.of(0.75).in(RadiansPerSecond);

    // Front Right
    public static final int kFrontRightDriveId = 1;
    public static final int kFrontRightSteerId = 2;
    public static final int kFrontRightEncoderId = 3;
    public static final double kFrontRightEncoderOffset = 0.0;

    // Front Left
    public static final int kFrontLeftDriveId = 4;
    public static final int kFrontLeftSteerId = 5;
    public static final int kFrontLeftEncoderId = 6;
    public static final double kFrontLeftEncoderOffset = 0.0;

    // Back Right
    public static final int kBackRightDriveId = 7;
    public static final int kBackRightSteerId = 8;
    public static final int kBackRightEncoderId = 9;
    public static final double kBackRightEncoderOffset = 0.0;

    // Back Left
    public static final int kBackLeftDriveId = 10;
    public static final int kBackLeftSteerId = 11;
    public static final int kBackLeftEncoderId = 12;
    public static final double kBackLeftEncoderOffset = 0.0;

    // Example: half of trackwidth and wheelbase in meters
    private static final double kTrackWidthX = 0.5; // Front-to-back distance / 2
    private static final double kTrackWidthY = 0.5; // Left-to-right distance / 2

    public static final Translation2d kFrontRightOffset = new Translation2d(kTrackWidthX, -kTrackWidthY);
    public static final Translation2d kFrontLeftOffset  = new Translation2d(kTrackWidthX, kTrackWidthY);
    public static final Translation2d kBackRightOffset  = new Translation2d(-kTrackWidthX, -kTrackWidthY);
    public static final Translation2d kBackLeftOffset   = new Translation2d(-kTrackWidthX, kTrackWidthY);

    // --- Motor Configurations ---
    public static CANMotorConfig getDriveMotorConfig() {
        return new CANMotorConfig()
            .withBusName(kCANBusName)
            .withCurrentLimit(60)
            .withMotorType(DCMotor.getKrakenX60(1))
            .withBrakeOn(true)
            .withMotorInvert(false)
            .withPID(0.1, 0.0, 0.0)
            .withSVA(0.15, 0.12, 0.01);
    }

    public static CANMotorConfig getSteerMotorConfig() {
        return new CANMotorConfig()
            .withBusName(kCANBusName)
            .withCurrentLimit(30)
            .withMotorType(DCMotor.getKrakenX60(1))
            .withBrakeOn(true)
            .withMotorInvert(false)
            .withPID(1.0, 0.0, 0.05)
            .withSVA(0.1, 0.0, 0.0);
    }

    // --- Factory Method to Generate Drivetrain ---
    public static SwerveDriveMechanism createDrivetrain(IMU6 imu) {
        CANMotorConfig driveConfig = getDriveMotorConfig();
        CANMotorConfig steerConfig = getSteerMotorConfig();

        SwerveModuleConfig frontRightModule = new SwerveModuleConfig(
            new TalonFXMotor(driveConfig.withId(kFrontRightDriveId)),
            new TalonFXMotor(steerConfig.withId(kFrontRightSteerId)),
            new CanCoderEncoder(kFrontRightEncoderId, kCANBusName),
            kDriveRatio, kSteerRatio, kWheelRadius, kRobotWeight, kFrontRightEncoderOffset
        );

        SwerveModuleConfig frontLeftModule = new SwerveModuleConfig(
            new TalonFXMotor(driveConfig.withId(kFrontLeftDriveId)),
            new TalonFXMotor(steerConfig.withId(kFrontLeftSteerId)),
            new CanCoderEncoder(kFrontLeftEncoderId, kCANBusName),
            kDriveRatio, kSteerRatio, kWheelRadius, kRobotWeight, kFrontLeftEncoderOffset
        );

        SwerveModuleConfig backRightModule = new SwerveModuleConfig(
            new TalonFXMotor(driveConfig.withId(kBackRightDriveId)),
            new TalonFXMotor(steerConfig.withId(kBackRightSteerId)),
            new CanCoderEncoder(kBackRightEncoderId, kCANBusName),
            kDriveRatio, kSteerRatio, kWheelRadius, kRobotWeight, kBackRightEncoderOffset
        );

        SwerveModuleConfig backLeftModule = new SwerveModuleConfig(
            new TalonFXMotor(driveConfig.withId(kBackLeftDriveId)),
            new TalonFXMotor(steerConfig.withId(kBackLeftSteerId)),
            new CanCoderEncoder(kBackLeftEncoderId, kCANBusName),
            kDriveRatio, kSteerRatio, kWheelRadius, kRobotWeight, kBackLeftEncoderOffset
        );

        return new SwerveDriveMechanism(
            kCANBusName,
            new SwerveModuleConfig[]{frontRightModule, frontLeftModule, backRightModule, backLeftModule},
            imu,
            kFrontRightOffset,
            kFrontLeftOffset,
            kBackRightOffset,
            kBackLeftOffset
        );
    }
}