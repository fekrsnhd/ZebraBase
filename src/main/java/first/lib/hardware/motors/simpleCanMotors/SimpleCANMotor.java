package first.lib.hardware.motors.simpleCanMotors;

import first.lib.hardware.motors.Motor;

public abstract class SimpleCANMotor extends Motor  {

    protected final SimpleCANMotorConfig config;

    protected SimpleCANMotor(SimpleCANMotorConfig config) {
        this.config = config;
    }

    public SimpleCANMotorConfig getConfig() {
        return config;
    }

    public abstract void runVoltage(double volts);

    public abstract double getAppliedVoltage();

    public abstract double getCurrent();
}
