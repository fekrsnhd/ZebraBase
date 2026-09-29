package first.lib.hardware.pwm.servos;

/**
 * Represents a PWM-controlled linear actuator.
 */
public class LinearActuator extends ServoController {
    
    private double fullLengthMM = 180;

    /**
     * Constructs a LinearActuator with a full configuration.
     * @param cfg The ServoControllerConfig to apply.
     */
    public LinearActuator(ServoControllerConfig cfg) { super(cfg); }

    /**
     * Constructs a LinearActuator on a specific channel with default rates.
     * @param channel The PWM channel.
     */
    public LinearActuator(int channel) {
        ServoControllerConfig config = new ServoControllerConfig(channel, 600, 2400, -1);
        this(config);
    }

    /**
     * Constructs a LinearActuator on a specific channel with custom rates.
     * @param channel The PWM channel.
     * @param minRate Minimum pulse width in microseconds.
     * @param maxRate Maximum pulse width in microseconds.
     */
    public LinearActuator(int channel, int minRate, int maxRate) {
        ServoControllerConfig config = new ServoControllerConfig(channel, minRate, maxRate, -1);
        this(config);
    }

    /**
     * Constructs a LinearActuator on a specific channel with custom rates and output period.
     * @param channel The PWM channel.
     * @param minRate Minimum pulse width in microseconds.
     * @param maxRate Maximum pulse width in microseconds.
     * @param outputPeriod Custom output period.
     */
    public LinearActuator(int channel, int minRate, int maxRate, int outputPeriod) {
        ServoControllerConfig config = new ServoControllerConfig(channel, minRate, maxRate, outputPeriod);
        this(config);
    }

    /**
     * Sets the maximum extension length of the actuator.
     * @param lengthMM The full stroke length in millimeters.
     * @return This LinearActuator instance for method chaining.
     */
    public LinearActuator WithFullLength(double lengthMM) {
        fullLengthMM = lengthMM;
        return this;
    }

    /**
     * Sets the target position of the actuator.
     * @param posMM The target position in millimeters.
     */
    public void setPosition(double posMM) {
        setPercent(posMM / fullLengthMM);
    }
    
}