package first.lib.mechanisms.drivetrains.swerve;

import first.lib.hardware.encoders.AbsoluteEncoderInterface;
import first.lib.hardware.motors.canMotors.CANMotor;
import org.wpilib.math.geometry.Rotation2d;
import org.wpilib.math.kinematics.SwerveModuleVelocity;

public class SwerveModuleReal extends SwerveModuleIO {

    private final CANMotor driveMotor;
    private final CANMotor steerMotor;
    private final AbsoluteEncoderInterface absoluteEncoder;

    public SwerveModuleReal(SwerveModuleConfig config) {
        super(config);
        this.absoluteEncoder = config.encoder;
        this.driveMotor = config.driveMotor;
        this.steerMotor = config.steerMotor;
    }

    @Override
    public void periodic() {}

    @Override
    public void setDesiredVelocity(SwerveModuleVelocity velocity) {

        // Convert linear speed (m/s) to motor RPM
        double driveRPM = (velocity.velocity / (2.0 * Math.PI * config.wheelRadiusMeters)) 
                          * config.driveGearRatio * 60.0;
        driveMotor.runRPM(driveRPM);

        // Convert steer angle to motor target rotations
        double steerRotations = velocity.angle.getRotations() * config.steerGearRatio;
        steerMotor.goToPoint(steerRotations);
    }

    @Override
    public void stop() {
        driveMotor.stop();
        steerMotor.stop();
    }

    @Override
    public double getDrivePositionMeters() {
        return (driveMotor.getRotations() / config.driveGearRatio) * (2.0 * Math.PI * config.wheelRadiusMeters);
    }

    @Override
    public double getDriveVelocityMetersPerSecond() {
        return (driveMotor.getVelocity() / config.driveGearRatio) * (2.0 * Math.PI * config.wheelRadiusMeters);
    }

    @Override
    public Rotation2d getSteerAngle() {
        // Now correctly utilizes the AbsoluteEncoderInterface implementation
        double rotations = absoluteEncoder.getAbsolutePosition() - config.absoluteEncoderOffsetRotations;
        return Rotation2d.fromRotations(rotations);
    }
}