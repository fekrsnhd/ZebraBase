package first.lib.hardware.motors.canMotors;

public abstract class CANMotor  {

    protected final CANMotorConfig config;

    protected CANMotor(CANMotorConfig config) {
        this.config = config;
    }

    public CANMotorConfig getConfig() {
        return config;
    }

    public abstract void runVoltage(double volts);

    public abstract void runDuty(double percent);

    public abstract void goToPoint(double rotations);

    public abstract void runRPM(double rpm);

    public abstract double getRPM();

    public abstract double getVelocity(); //RPS

    public abstract double getRotations();

    public abstract double getAppliedVoltage();

    public abstract double getCurrent();

    public abstract void stop();

    public abstract void setSimEncoderPosition(double rotations);

    public abstract void setSimEncoderVelocity(double rps);
}
