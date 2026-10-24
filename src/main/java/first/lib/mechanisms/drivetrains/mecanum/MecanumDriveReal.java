package first.lib.mechanisms.drivetrains.mecanum;

import first.lib.hardware.motors.canMotors.CANMotor;
import org.wpilib.math.kinematics.MecanumDriveWheelPositions;
import org.wpilib.math.kinematics.MecanumDriveWheelVelocities;

public class MecanumDriveReal extends MecanumDriveIO {

    private final CANMotor frontLeft;
    private final CANMotor frontRight;
    private final CANMotor rearLeft;
    private final CANMotor rearRight;

    public MecanumDriveReal(MecanumDriveConfig config) {
        super(config);
        this.frontLeft = config.frontLeft;
        this.frontRight = config.frontRight;
        this.rearLeft = config.rearLeft;
        this.rearRight = config.rearRight;
    }

    @Override
    public void periodic() {
        // Hardware periodic tasks if required
    }

    @Override
    public void setDesiredWheelVelocities(MecanumDriveWheelVelocities speeds) {
        frontLeft.runRPM(metersPerSecondToRPM(speeds.frontLeft));
        frontRight.runRPM(metersPerSecondToRPM(speeds.frontRight));
        rearLeft.runRPM(metersPerSecondToRPM(speeds.rearLeft));
        rearRight.runRPM(metersPerSecondToRPM(speeds.rearRight));
    }

    @Override
    public void stop() {
        frontLeft.stop();
        frontRight.stop();
        rearLeft.stop();
        rearRight.stop();
    }

    @Override
    public MecanumDriveWheelPositions getWheelPositions() {
        return new MecanumDriveWheelPositions(
            rotationsToMeters(frontLeft.getRotations()),
            rotationsToMeters(frontRight.getRotations()),
            rotationsToMeters(rearLeft.getRotations()),
            rotationsToMeters(rearRight.getRotations())
        );
    }

    @Override
    public MecanumDriveWheelVelocities getWheelVelocities() {
        return new MecanumDriveWheelVelocities(
            rpsToMetersPerSecond(frontLeft.getVelocity()),
            rpsToMetersPerSecond(frontRight.getVelocity()),
            rpsToMetersPerSecond(rearLeft.getVelocity()),
            rpsToMetersPerSecond(rearRight.getVelocity())
        );
    }

    // Helper conversion methods
    private double metersPerSecondToRPM(double mps) {
        return (mps / (2.0 * Math.PI * config.wheelRadiusMeters)) * config.gearRatio * 60.0;
    }

    private double rotationsToMeters(double rotations) {
        return (rotations / config.gearRatio) * (2.0 * Math.PI * config.wheelRadiusMeters);
    }

    private double rpsToMetersPerSecond(double rps) {
        return (rps / config.gearRatio) * (2.0 * Math.PI * config.wheelRadiusMeters);
    }
}