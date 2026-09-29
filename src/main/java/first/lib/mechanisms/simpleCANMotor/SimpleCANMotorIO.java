package first.lib.mechanisms.simpleCANMotor;

import org.littletonrobotics.junction.AutoLog;
import first.lib.hardware.motors.canMotors.CANMotor;

/**
 * Abstract hardware abstraction layer for a simple CAN motor.
 * Defines the contract for interacting with the motor in both real and simulated environments.
 */
public abstract class SimpleCANMotorIO {
    
    /**
     * Data object containing the logged inputs from the CAN motor.
     */
    @AutoLog
    public static class SimpleCANMotorIOInputs {
        /** The current position of the motor in rotations. */
        public double position = 0;
        /** The current draw of the motor in amps. */
        public double current = 0;
        /** The voltage currently applied to the motor. */
        public double volts = 0;
        /** The current velocity of the motor in rotations per second. */
        public double velocity = 0;
    }

    protected final CANMotor canMotor;

    /**
     * Constructs a new SimpleCANMotorIO instance.
     *
     * @param motor The underlying CANMotor instance to wrap.
     */
    protected SimpleCANMotorIO(CANMotor motor) {
        this.canMotor = motor;
    }

    /**
     * Runs the motor at a specified voltage.
     *
     * @param volts The voltage to apply.
     */
    public abstract void runVoltage(double volts);

    /**
     * Runs the motor at a specified duty cycle.
     *
     * @param percent The duty cycle percentage (typically -1.0 to 1.0).
     */
    public abstract void runDuty(double percent);

    /**
     * Commands the motor to move to a specific position.
     *
     * @param rotations The target position in rotations.
     */
    public abstract void goToPoint(double rotations);

    /**
     * Commands the motor to run at a specific RPM.
     *
     * @param rpm The target speed in revolutions per minute.
     */
    public abstract void runRPM(double rpm);

    /**
     * Gets the current speed of the motor in RPM.
     *
     * @return The current RPM.
     */
    public abstract double getRPM();

    /**
     * Gets the current velocity of the motor in rotations per second (RPS).
     *
     * @return The velocity in RPS.
     */
    public abstract double getVelocity();

    /**
     * Gets the current position of the motor in rotations.
     *
     * @return The position in rotations.
     */
    public abstract double getRotations();

    /**
     * Gets the voltage currently applied to the motor.
     *
     * @return The applied voltage.
     */
    public abstract double getAppliedVoltage();

    /**
     * Gets the current drawn by the motor.
     *
     * @return The current in amps.
     */
    public abstract double getCurrent();

    /**
     * Stops the motor completely.
     */
    public abstract void stop();

    /**
     * Sets the simulated encoder position.
     *
     * @param rotations The simulated position in rotations.
     */
    public abstract void setSimEncoderPosition(double rotations);

    /**
     * Sets the simulated encoder velocity.
     *
     * @param rps The simulated velocity in rotations per second.
     */
    public abstract void setSimEncoderVelocity(double rps);

    /**
     * Updates the input data object with the latest values from the hardware.
     *
     * @param inputs The inputs object to populate.
     */
    public void updateInputs(SimpleCANMotorIOInputs inputs) {
        inputs.position = canMotor.getRotations();
        inputs.velocity = canMotor.getVelocity();
        inputs.current = canMotor.getCurrent();
        inputs.volts = canMotor.getAppliedVoltage();
    }
}