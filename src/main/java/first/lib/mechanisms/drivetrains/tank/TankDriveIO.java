package first.lib.mechanisms.drivetrains.tank;

import org.littletonrobotics.junction.AutoLog;

public abstract class TankDriveIO {

    @AutoLog
    public static class TankDriveIOInputs {
        public double leftPositionMeters = 0.0;
        public double leftVelocityMetersPerSecond = 0.0;
        public double leftAppliedVoltage = 0.0;
        public double leftCurrentAmps = 0.0;

        public double rightPositionMeters = 0.0;
        public double rightVelocityMetersPerSecond = 0.0;
        public double rightAppliedVoltage = 0.0;
        public double rightCurrentAmps = 0.0;
    }

    protected final TankDriveConfig config;

    protected TankDriveIO(TankDriveConfig config) {
        this.config = config;
    }

    public abstract void periodic();
    public abstract void setVoltage(double leftVolts, double rightVolts);
    public abstract void stop();

    public abstract double getLeftPositionMeters();
    public abstract double getLeftVelocityMetersPerSecond();
    public abstract double getRightPositionMeters();
    public abstract double getRightVelocityMetersPerSecond();

    public void updateInputs(TankDriveIOInputs inputs) {
        inputs.leftPositionMeters = getLeftPositionMeters();
        inputs.leftVelocityMetersPerSecond = getLeftVelocityMetersPerSecond();
        inputs.leftAppliedVoltage = config.leftLeader.getAppliedVoltage();
        inputs.leftCurrentAmps = config.leftLeader.getCurrent();

        inputs.rightPositionMeters = getRightPositionMeters();
        inputs.rightVelocityMetersPerSecond = getRightVelocityMetersPerSecond();
        inputs.rightAppliedVoltage = config.rightLeader.getAppliedVoltage();
        inputs.rightCurrentAmps = config.rightLeader.getCurrent();
    }
}