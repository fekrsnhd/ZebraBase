package first.lib.hardware.motors.canMotors;

import first.lib.hardware.motors.Motor;

/**
 * Abstract base class for CAN-based motors.
 * Extends the base Motor class and mandates implementation of core motor functions.
 */
public abstract class CANMotor implements Motor  {

    protected final CANMotorConfig config;

    /**
     * Constructs a CANMotor with the specified configuration.
     * @param config The configuration settings for this motor.
     */
    protected CANMotor(CANMotorConfig config) {
        this.config = config;
    }

    /**
     * Retrieves the current motor configuration.
     * @return The CANMotorConfig object applied to this motor.
     */
    public CANMotorConfig getConfig() {
        return config;
    }

    /**
     * Runs the motor at the specified voltage.
     * @param volts The voltage to apply to the motor.
     */
    public abstract void runVoltage(double volts);

    /**
     * Commands the motor to move to a specific position using closed-loop control.
     * @param rotations Target position in rotations.
     */
    public abstract void goToPoint(double rotations);

    /**
     * Commands the motor to run at a specific velocity using closed-loop control.
     * @param rpm Target velocity in revolutions per minute (RPM).
     */
    public abstract void runRPM(double rpm);

    /**
     * Gets the current velocity of the motor in RPM.
     * @return Current velocity in RPM.
     */
    public abstract double getRPM();

    /**
     * Gets the current velocity of the motor in RPS (Revolutions Per Second).
     * @return Current velocity in RPS.
     */
    public abstract double getVelocity(); //RPS

    /**
     * Gets the current position of the motor in rotations.
     * @return Current position in rotations.
     */
    public abstract double getRotations();

    /**
     * Gets the current applied voltage of the motor.
     * @return Applied voltage in volts.
     */
    public abstract double getAppliedVoltage();

    /**
     * Gets the current output current of the motor.
     * @return Output current in amps.
     */
    public abstract double getCurrent();

    /**
     * Sets the internal rotor position of the motor's encoder.
     * @param pos The position to set in rotations.
     */
    public abstract void setRotorPosition(double pos);

    /**
     * Sets the simulated encoder position.
     * @param rotations Simulated position in rotations.
     */
    public abstract void setSimEncoderPosition(double rotations);

    /**
     * Sets the simulated encoder velocity.
     * @param rps Simulated velocity in revolutions per second (RPS).
     */
    public abstract void setSimEncoderVelocity(double rps);
}