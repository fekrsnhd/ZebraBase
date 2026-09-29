package first.lib.hardware.motors.simpleCanMotors;

import first.lib.hardware.motors.Motor;

/**
 * Abstract base class for simplified CAN motors, omitting complex PID features.
 */
public abstract class SimpleCANMotor implements Motor  {

    protected final SimpleCANMotorConfig config;

    /**
     * Constructs a SimpleCANMotor with the specified configuration.
     * @param config The simplified configuration parameters for this motor.
     */
    protected SimpleCANMotor(SimpleCANMotorConfig config) {
        this.config = config;
    }

    /**
     * Retrieves the current motor configuration.
     * @return The SimpleCANMotorConfig object applied to this motor.
     */
    public SimpleCANMotorConfig getConfig() {
        return config;
    }

    /**
     * Runs the motor at the specified voltage.
     * @param volts Target voltage.
     */
    public abstract void runVoltage(double volts);

    /**
     * Gets the current applied voltage.
     * @return Applied voltage in volts.
     */
    public abstract double getAppliedVoltage();

    /**
     * Gets the current motor current draw.
     * @return Current in Amps.
     */
    public abstract double getCurrent();
}