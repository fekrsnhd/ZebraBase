package first.lib.hardware.pwm.servos;

import org.wpilib.hardware.discrete.PWM;

/**
 * Base controller for managing raw PWM outputs to servo mechanisms.
 */
public class ServoController {
    private final PWM pwmHardware;
    public final ServoControllerConfig config;

    /**
     * Constructs a new ServoController and initializes hardware bindings.
     * @param cfg The configuration detailing channel and timing bounds.
     */
    public ServoController(ServoControllerConfig cfg) {
        this.config = cfg;
        pwmHardware = new PWM(config.channel);
        if (config.outputPeriod != -1) {
            pwmHardware.setOutputPeriod(config.outputPeriod);
        }
    }

    /**
     * Directly sets the pulse width in microseconds, clamped to the configured bounds.
     * @param pulseWidth The desired pulse width in microseconds.
     */
    public void setPulseWidth(int pulseWidth) {
        pulseWidth = Math.clamp(pulseWidth, config.minRate, config.maxRate);
        pwmHardware.setPulseTimeMicroseconds(pulseWidth);
    }

    /**
     * Sets the output based on a percentage of the configured pulse width range.
     * @param percent The target output from 0.0 to 1.0.
     */
    public void setPercent(double percent) {
        int pulseWidth = (int) (config.minRate + (percent * (config.maxRate - config.minRate)));
        pwmHardware.setPulseTimeMicroseconds(pulseWidth);
    }

    /**
     * Retrieves the current pulse width output.
     * @return The current pulse width in microseconds.
     */
    public int getPulseWidth() {
        return pwmHardware.getPulseTimeMicroseconds();
    }

    /**
     * Retrieves the current output as a percentage of the configured bounds.
     * @return The current output from 0.0 to 1.0.
     */
    public double getPercent() {
        return ((pwmHardware.getPulseTimeMicroseconds() - config.minRate) * 1.0) / (config.maxRate - config.minRate);
    }

}