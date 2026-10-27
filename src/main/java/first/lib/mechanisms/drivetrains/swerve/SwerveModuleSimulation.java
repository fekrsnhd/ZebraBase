package first.lib.mechanisms.drivetrains.swerve;

import org.wpilib.framework.RobotBase;
import org.wpilib.math.geometry.Rotation2d;
import org.wpilib.math.kinematics.SwerveModuleVelocity;

import first.lib.hardware.motors.canMotors.CANMotor;

public class SwerveModuleSimulation extends SwerveModuleIO {

    private final CANMotor driveMotor;
    private final CANMotor steerMotor;
    
    private double simulatedDrivePositionMeters = 0.0;
    private double simulatedDriveVelocityMps = 0.0;
    private double simulatedSteerRotations = 0.0;

    private double targetDriveVelocityMps = 0.0;
    private double targetSteerRotations = 0.0;

    public SwerveModuleSimulation(SwerveModuleConfig config) {
        super(config);
        this.driveMotor = config.driveMotor;
        this.steerMotor = config.steerMotor;
    }

    @Override
    public void periodic() {
        if (RobotBase.isSimulation()) {
            // Smoothly approach target drive velocity (simulates motor acceleration)
            double driveError = targetDriveVelocityMps - simulatedDriveVelocityMps;
            simulatedDriveVelocityMps += driveError * 0.2; // Acceleration responsiveness
            simulatedDrivePositionMeters += simulatedDriveVelocityMps * 0.02;

            // Update drive motor sim encoder
            double driveRotations = (simulatedDrivePositionMeters / (2.0 * Math.PI * config.wheelRadiusMeters)) * config.driveGearRatio;
            double driveRPS = (simulatedDriveVelocityMps / (2.0 * Math.PI * config.wheelRadiusMeters)) * config.driveGearRatio;
            driveMotor.setSimEncoderPosition(driveRotations);
            driveMotor.setSimEncoderVelocity(driveRPS);

            // Smoothly approach target steer angle (simulates steering motor turning speed)
            double steerError = targetSteerRotations - simulatedSteerRotations;
            simulatedSteerRotations += steerError * 0.4; // Steering responsiveness

            // Update steer motor sim encoder
            steerMotor.setSimEncoderPosition(simulatedSteerRotations);
        }
    }

    @Override
    public void setDesiredVelocity(SwerveModuleVelocity velocity) {
        targetDriveVelocityMps = velocity.velocity;
        targetSteerRotations = velocity.angle.getRotations() * config.steerGearRatio;

        // Execute normal motor control calls so logs and code structure remain intact
        double driveRPM = (velocity.velocity / (2.0 * Math.PI * config.wheelRadiusMeters)) * config.driveGearRatio * 60.0;
        driveMotor.runRPM(driveRPM);
        steerMotor.goToPoint(targetSteerRotations);
    }

    @Override
    public void stop() {
        targetDriveVelocityMps = 0.0;
        driveMotor.stop();
        steerMotor.stop();
    }

    @Override
    public double getDrivePositionMeters() {
        return simulatedDrivePositionMeters;
    }

    @Override
    public double getDriveVelocityMetersPerSecond() {
        return simulatedDriveVelocityMps;
    }

    @Override
    public Rotation2d getSteerAngle() {
        return Rotation2d.fromRotations(simulatedSteerRotations / config.steerGearRatio);
    }
}