package first.lib.mechanisms.turret;

import org.littletonrobotics.junction.AutoLog;
import org.wpilib.command3.Trigger;

/**
 * Abstract hardware abstraction layer (IO) for a Turret mechanism.
 */
public abstract class TurretIO {

    /**
     * Data object containing logged inputs from the turret mechanism.
     */
    @AutoLog
    public static class TurretIOInputs {
        /** The current position of the turret in radians. */
        public double positionRads = 0;
        /** The current velocity of the turret in radians per second. */
        public double velocityRadsPerSec = 0;
        /** The current position of the turret in motor rotations. */
        public double rotations = 0;
    }

    protected final TurretConfig config;

    /**
     * Constructs a new IO layer for the turret.
     *
     * @param cfg The configuration settings for the turret.
     */
    protected TurretIO(TurretConfig cfg) {
        this.config = cfg;
    }

    /** Called periodically to update internal state. */
    public abstract void periodic();

    /**
     * Converts motor rotations to turret radians.
     *
     * @param rotations The motor rotations.
     * @return The equivalent angle in radians.
     */
    public double rotationsToRadians(double rotations) {
        return rotations * (2.0 * Math.PI) / config.gearing;
    }

    /**
     * Converts turret radians to motor rotations.
     *
     * @param radians The angle in radians.
     * @return The equivalent motor rotations.
     */
    public double radiansToRotations(double radians) {
        return radians * config.gearing / (2.0 * Math.PI);
    }

    /** Stops the turret motor completely. */
    public abstract void stop();

    /**
     * Commands the turret to move to a specific angle.
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
     * Applies a raw voltage to the turret motor.
     *
     * @param volts The voltage to apply.
     */
    public abstract void applyVoltage(double volts);

    /**
     * Applies a duty cycle percentage to the turret motor.
     *
     * @param duty The duty cycle.
     */
    public abstract void applyDutyCycle(double duty);

    /** @return The current angle of the turret in radians. */
    public abstract double getAngleRads();

    /** @return The current position of the motor in rotations. */
    public abstract double getRotations();

    /** @return The current velocity of the turret in radians per second. */
    public abstract double getVelocityRadsPerSec();
    
    /**
     * Creates a trigger that becomes true when the turret is at the target angle.
     *
     * @param angleRads The target angle.
     * @param toleranceRads The acceptable tolerance around the target angle.
     * @return A Trigger evaluating the condition.
     */
    public abstract Trigger atAngle(double angleRads, double toleranceRads);

    /**
     * Updates the input data object with the latest values from the hardware.
     *
     * @param inputs The inputs object to populate.
     */
    public void updateInputs(TurretIOInputs inputs) {
        inputs.positionRads = getAngleRads();
        inputs.velocityRadsPerSec = getVelocityRadsPerSec();
        inputs.rotations = getRotations();
    }
}