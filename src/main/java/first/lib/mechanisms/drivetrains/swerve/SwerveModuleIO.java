package first.lib.mechanisms.drivetrains.swerve;

import org.littletonrobotics.junction.AutoLog;
import org.wpilib.math.geometry.Rotation2d;
import org.wpilib.math.kinematics.SwerveModuleVelocity;
import org.wpilib.math.kinematics.SwerveModulePosition;

public abstract class SwerveModuleIO {

    @AutoLog
    public static class SwerveModuleIOInputs {
        public double drivePositionMeters = 0.0;
        public double driveVelocityMetersPerSecond = 0.0;
        public double driveAppliedVoltage = 0.0;
        public double driveCurrentAmps = 0.0;

        public double steerPositionRotations = 0.0;
        public double steerVelocityRPS = 0.0;
        public double steerAppliedVoltage = 0.0;
        public double steerCurrentAmps = 0.0;
        public Rotation2d absoluteEncoderPosition = new Rotation2d();
    }

    protected final SwerveModuleConfig config;

    protected SwerveModuleIO(SwerveModuleConfig config) {
        this.config = config;
    }

    public abstract void periodic();

    public abstract void setDesiredVelocity(SwerveModuleVelocity state);

    public abstract void stop();

    public SwerveModulePosition getPosition() {
        return new SwerveModulePosition(
            getDrivePositionMeters(),
            getSteerAngle()
        );
    }

    public SwerveModuleVelocity getVelocity() {
        return new SwerveModuleVelocity(
            getDriveVelocityMetersPerSecond(),
            getSteerAngle()
        );
    }

    public abstract double getDrivePositionMeters();
    public abstract double getDriveVelocityMetersPerSecond();
    public abstract Rotation2d getSteerAngle();

    public void updateInputs(SwerveModuleIOInputs inputs) {
        inputs.drivePositionMeters = getDrivePositionMeters();
        inputs.driveVelocityMetersPerSecond = getDriveVelocityMetersPerSecond();
        inputs.driveAppliedVoltage = config.driveMotor.getAppliedVoltage();
        inputs.driveCurrentAmps = config.driveMotor.getCurrent();

        inputs.steerPositionRotations = config.steerMotor.getRotations();
        inputs.steerVelocityRPS = config.steerMotor.getVelocity();
        inputs.steerAppliedVoltage = config.steerMotor.getAppliedVoltage();
        inputs.steerCurrentAmps = config.steerMotor.getCurrent();
        inputs.absoluteEncoderPosition = getSteerAngle();
    }
}