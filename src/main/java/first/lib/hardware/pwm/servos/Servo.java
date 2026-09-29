package first.lib.hardware.pwm.servos;

/**
 * Represents a standard PWM-controlled rotary servo.
 */
public class Servo extends ServoController {
    
    private double fullAngle = 180;

    /**
     * Constructs a Servo with a full configuration.
     * @param cfg The ServoControllerConfig to apply.
     */
    public Servo(ServoControllerConfig cfg) { super(cfg); }

    /**
     * Constructs a Servo on a specific channel with default rates.
     * @param channel The PWM channel.
     */
    public Servo(int channel) {
        ServoControllerConfig config = new ServoControllerConfig(channel, 600, 2400, -1);
        this(config);
    }

    /**
     * Constructs a Servo on a specific channel with custom rates.
     * @param channel The PWM channel.
     * @param minRate Minimum pulse width in microseconds.
     * @param maxRate Maximum pulse width in microseconds.
     */
    public Servo(int channel, int minRate, int maxRate) {
        ServoControllerConfig config = new ServoControllerConfig(channel, minRate, maxRate, -1);
        this(config);
    }

    /**
     * Constructs a Servo on a specific channel with custom rates and output period.
     * @param channel The PWM channel.
     * @param minRate Minimum pulse width in microseconds.
     * @param maxRate Maximum pulse width in microseconds.
     * @param outputPeriod Custom output period.
     */
    public Servo(int channel, int minRate, int maxRate, int outputPeriod) {
        ServoControllerConfig config = new ServoControllerConfig(channel, minRate, maxRate, outputPeriod);
        this(config);
    }

    /**
     * Sets the maximum range of motion for the servo.
     * @param angle The full range of motion in degrees.
     * @return This Servo instance for method chaining.
     */
    public Servo WithFullAngle(double angle) {
        this.fullAngle = angle;
        return this;
    }

    /**
     * Sets the target angle of the servo.
     * @param angle The target angle in degrees.
     */
    public void setAngle(double angle) {
        setPercent(angle / fullAngle);
    }
    
}