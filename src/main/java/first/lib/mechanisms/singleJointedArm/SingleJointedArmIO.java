package first.lib.mechanisms.singleJointedArm;

import org.littletonrobotics.junction.AutoLog;
import org.wpilib.command3.Trigger;

/**
 * Abstract hardware abstraction layer (IO) for a single-jointed arm.
 * Defines the contract for interacting with the arm in both real and simulated modes.
 */
public abstract class SingleJointedArmIO {

    /**
     * Data object containing logged inputs from the arm mechanism.
     */
    @AutoLog
    public static class SingleJointedArmIOInputs {
        /** The current position of the arm in radians. */
        public double positionRads = 0;
        /** The current velocity of the arm in radians per second. */
        public double velocityRadsPerSec = 0;
        /** The current position of the arm in motor rotations. */
        public double rotations = 0;
    }

    protected final SingleJointedArmConfig config;

    /**
     * Constructs a new IO layer for the arm.
     *
     * @param cfg The configuration settings for the arm.
     */
    protected SingleJointedArmIO(SingleJointedArmConfig cfg) {
        this.config = cfg;
    }

    /**
     * Called periodically to update internal state (e.g., simulation physics).
     */
    public abstract void periodic();

    /**
     * Converts motor rotations to mechanism radians.
     *
     * @param rotations The motor rotations.
     * @return The equivalent angle in radians.
     */
    public double rotationsToRadians(double rotations) {
        return rotations * (2.0 * Math.PI) / config.gearing;
    }

    /**
     * Converts mechanism radians to motor rotations.
     *
     * @param radians The angle in radians.
     * @return The equivalent motor rotations.
     */
    public double radiansToRotations(double radians) {
        return radians * config.gearing / (2.0 * Math.PI);
    }

    /** Stops the arm motor completely. */
    public abstract void stop();

    /**
     * Commands the arm to move to a specific angle.
     *
     * @param angleRads The target angle in radians.
     */
    public abstract void goToAngle(double angleRads);

    /**
     * Commands the motor to move to a specific rotation point.
     *
     * @param pointRotations The target position in motor rotations.
     */
    public abstract void goToPoint(double pointRotations);

    /**
     * Applies a raw voltage to the arm motor.
     *
     * @param volts The voltage to apply.
     */
    public abstract void applyVoltage(double volts);

    /**
     * Applies a duty cycle percentage to the arm motor.
     *
     * @param duty The duty cycle (typically -1.0 to 1.0).
     */
    public abstract void applyDutyCycle(double duty);

    /** @return The current angle of the arm in radians. */
    public abstract double getAngleRads();

    /** @return The current position of the motor in rotations. */
    public abstract double getRotations();

    /** @return The current velocity of the arm in radians per second. */
    public abstract double getVelocityRadsPerSec();
    
    /**
     * Creates a trigger that becomes true when the arm is at the target angle.
     *
     * @param angleRads The target angle.
     * @param toleranceRads The acceptable tolerance around the target angle.
     * @return A Trigger object evaluating the condition.
     */
    public abstract Trigger atAngle(double angleRads, double toleranceRads);

    /**
     * Updates the input data object with the latest values from the hardware.
     *
     * @param inputs The inputs object to populate.
     */
    public void updateInputs(SingleJointedArmIOInputs inputs) {
        inputs.positionRads = getAngleRads();
        inputs.velocityRadsPerSec = getVelocityRadsPerSec();
        inputs.rotations = getRotations();
    }
}