package first.lib.mechanisms.elevator;

import org.littletonrobotics.junction.AutoLog;
import org.wpilib.command3.Trigger;

/**
 * Hardware abstraction layer for the Elevator mechanism.
 * Defines standard interactions for physical hardware or simulation.
 */
public abstract class ElevatorIO {

    /**
     * AdvantageKit auto-logged inputs structure for the elevator.
     */
    @AutoLog
    public static class ElevatorIOInputs {
        /** The current position of the elevator. */
        public double position = 0;
        /** The current velocity of the elevator. */
        public double velocity = 0;
        /** The current motor rotations. */
        public double rotations = 0;
    }

    protected final ElevatorConfig config;

    /**
     * Constructs the IO layer with a given configuration.
     *
     * @param cfg The elevator configuration.
     */
    protected ElevatorIO(ElevatorConfig cfg) {
        this.config = cfg;
    }

    /**
     * Periodic method called repeatedly to update hardware/simulation state.
     */
    public abstract void periodic();

    /**
     * Converts motor rotations into linear meters based on mechanism geometry.
     *
     * @param rotations The number of motor rotations.
     * @return The corresponding linear height in meters.
     */
    public double rotationsToMeters(double rotations) {
        return rotations * (2.0 * Math.PI * config.radiusMeters) / config.gearing;
    }

    /**
     * Converts linear meters into motor rotations based on mechanism geometry.
     *
     * @param meters The linear height in meters.
     * @return The corresponding number of motor rotations.
     */
    public double metersToRotations(double meters) {
        return meters * config.gearing / (2.0 * Math.PI * config.radiusMeters);
    }

    /**
     * Stops the elevator motor.
     */
    public abstract void stop();

    /**
     * Commands the elevator to travel to a specified height.
     *
     * @param heightMeters The target height in meters.
     */
    public abstract void goToHeight(double heightMeters);

    /**
     * Commands the elevator to travel to a specified rotational point.
     *
     * @param pointRotations The target position in rotations.
     */
    public abstract void goToPoint(double pointRotations);

    /**
     * Applies a direct voltage to the elevator motor.
     *
     * @param volts The voltage to apply.
     */
    public abstract void applyVoltage(double volts);

    /**
     * Applies a duty cycle percentage to the elevator motor.
     *
     * @param duty The duty cycle to apply (range usually -1.0 to 1.0).
     */
    public abstract void applyDutyCycle(double duty);

    /**
     * Gets the current height of the elevator.
     *
     * @return The height in meters.
     */
    public abstract double getHeight();

    /**
     * Gets the current motor position.
     *
     * @return The position in rotations.
     */
    public abstract double getRotations();

    /**
     * Gets the current linear velocity of the elevator.
     *
     * @return The velocity in meters per second.
     */
    public abstract double getVelocityMetersPerSecond();

    /**
     * Creates a trigger that returns true when the elevator reaches a specified height.
     *
     * @param height    The target height in meters.
     * @param tolerance The acceptable error tolerance in meters.
     * @return A WPILib Trigger object.
     */
    public abstract Trigger atHeight(double height, double tolerance);

    /**
     * Updates the logged input struct with the latest telemetry.
     *
     * @param inputs The AdvantageKit input struct.
     */
    public void updateInputs(ElevatorIOInputs inputs) {
        inputs.position = getHeight();
        inputs.velocity = getVelocityMetersPerSecond();
        inputs.rotations = getRotations();
    }
}