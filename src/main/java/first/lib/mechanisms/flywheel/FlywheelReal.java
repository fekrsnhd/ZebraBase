package first.lib.mechanisms.flywheel;

import org.wpilib.command3.Trigger;
import first.lib.hardware.motors.canMotors.CANMotor;

/**
 * Concrete physical device proxy for flywheels.
 */
public class FlywheelReal extends FlywheelIO {

    private final CANMotor motor;

    /**
     * Maps physical constants to actual robot hardware.
     *
     * @param cfg Hardware settings logic structure.
     */
    public FlywheelReal(FlywheelConfig cfg) {
        super(cfg);
        this.motor = config.motor;
    }

    /** {@inheritDoc} */
    public void periodic() {}

    /** Stops current electrical feed. */
    public void stop() {
        motor.stop();
    }

    /**
     * Sets velocity closed-loop configuration on physical device.
     *
     * @param rpm Value required for output format targeting.
     */
    public void runRPM(double rpm) {
        motor.runRPM(rpm);
    }

    /**
     * Asserts target volts physically.
     *
     * @param volts Signal payload target.
     */
    public void applyVoltage(double volts) {
        motor.runVoltage(volts);
    }
    
    /**
     * Supplies explicit scaling on output.
     *
     * @param duty Power specification mapping.
     */
    public void applyDutyCycle(double duty) {
        motor.runDuty(duty);
    }

    /**
     * Obtains physical velocity.
     *
     * @return Output converted measurement mapping.
     */
    public double getRPM() {
        return motor.getRPM();
    }

    /**
     * Condition verifying physical limits.
     *
     * @param rpm       Target state required.
     * @param tolerance Limit acceptable margin.
     * @return System check evaluation element.
     */
    public Trigger atRPM(double rpm, double tolerance) {
        return new Trigger(() -> (Math.abs(getRPM() - rpm) <= tolerance));
    }
}