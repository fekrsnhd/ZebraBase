package first.lib.mechanisms.drivetrains.tank;

import first.lib.hardware.motors.canMotors.CANMotor;

public class TankDriveReal extends TankDriveIO {
    private final CANMotor left;
    private final CANMotor right;

    public TankDriveReal(TankDriveConfig config) {
        super(config);
        this.left = config.leftLeader;
        this.right = config.rightLeader;
    }

    @Override
    public void periodic() {}

    @Override
    public void setVoltage(double leftVolts, double rightVolts) {
        left.runVoltage(leftVolts);
        right.runVoltage(rightVolts);
    }

    @Override
    public void stop() {
        left.stop();
        right.stop();
    }

    @Override
    public double getLeftPositionMeters() {
        return (left.getRotations() / config.gearRatio) * (2.0 * Math.PI * config.wheelRadiusMeters);
    }

    @Override
    public double getLeftVelocityMetersPerSecond() {
        return (left.getVelocity() / config.gearRatio) * (2.0 * Math.PI * config.wheelRadiusMeters);
    }

    @Override
    public double getRightPositionMeters() {
        return (right.getRotations() / config.gearRatio) * (2.0 * Math.PI * config.wheelRadiusMeters);
    }

    @Override
    public double getRightVelocityMetersPerSecond() {
        return (right.getVelocity() / config.gearRatio) * (2.0 * Math.PI * config.wheelRadiusMeters);
    }
}