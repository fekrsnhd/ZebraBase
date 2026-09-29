package first.lib.mechanisms.flywheel;

import org.littletonrobotics.junction.AutoLog;
import org.wpilib.command3.Trigger;

/**
 * Hardware abstraction layer for a Flywheel mechanism.
 */
public abstract class FlywheelIO {

    /**
     * Auto-logged input fields for AdvantageKit.
     */
    @AutoLog
    public static class FlywheelIOInputs {
        /** Measured velocity in RPM. */
        public double velocityRPM = 0;
        /** Currently applied voltage. */
        public double appliedVolts = 0;
    }

    protected final FlywheelConfig config;

    /**
     * Constructs the IO layer for the flywheel.
     *
     * @param cfg Configuration structure.
     */
    protected FlywheelIO(FlywheelConfig cfg) {
        this.config = cfg;
    }

    /** Loop method called sequentially to update properties. */
    public abstract void periodic();

    /** Commands the flywheel motor to halt. */
    public abstract void stop();

    /**
     * Sets the flywheel to a closed-loop target RPM.
     *
     * @param rpm The target RPM.
     */
    public abstract void runRPM(double rpm);
    
    /**
     * Sends a raw voltage request to the motor controller.
     *
     * @param volts Target voltage.
     */
    public abstract void applyVoltage(double volts);

    /**
     * Sends an open-loop duty cycle request to the motor.
     *
     * @param duty Duty cycle percentage.
     */
    public abstract void applyDutyCycle(double duty);

    /**
     * Gets the current velocity of the flywheel.
     *
     * @return Velocity in RPM.
     */
    public abstract double getRPM();

    /**
     * Trigger that fires when the flywheel matches target RPM within tolerance.
     *
     * @param rpm       The target RPM.
     * @param tolerance Acceptable difference in RPM.
     * @return A WPILib Trigger.
     */
    public abstract Trigger atRPM(double rpm, double tolerance);

    /**
     * Syncs hardware/simulated properties into the logger.
     *
     * @param inputs Data structure containing AdvantageKit variables.
     */
    public void updateInputs(FlywheelIOInputs inputs) {
        inputs.velocityRPM = getRPM();
        inputs.appliedVolts = config.motor.getAppliedVoltage();
    }
}