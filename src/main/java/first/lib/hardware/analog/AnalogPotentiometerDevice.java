package first.lib.hardware.analog;

/**
 * Represents a rotary potentiometer, mapping analog voltage to an angular position.
 */
public class AnalogPotentiometerDevice extends AnalogInputController {

    private final double fullRangeDegrees;
    private final double offsetDegrees;

    /**
     * Constructs a potentiometer reading an angle based on a channel.
     * 
     * @param channel          The analog input channel.
     * @param fullRangeDegrees The total mechanical sweep of the potentiometer in degrees.
     * @param offsetDegrees    An angular offset added to the final calculation.
     */
    public AnalogPotentiometerDevice(int channel, double fullRangeDegrees, double offsetDegrees) {
        super(channel);
        this.fullRangeDegrees = fullRangeDegrees;
        this.offsetDegrees = offsetDegrees;
    }

    /**
     * Constructs a potentiometer reading an angle based on a specific configuration.
     * 
     * @param config           The hardware configuration limits.
     * @param fullRangeDegrees The total mechanical sweep of the potentiometer in degrees.
     * @param offsetDegrees    An angular offset added to the final calculation.
     */
    public AnalogPotentiometerDevice(AnalogInputConfig config, double fullRangeDegrees, double offsetDegrees) {
        super(config);
        this.fullRangeDegrees = fullRangeDegrees;
        this.offsetDegrees = offsetDegrees;
    }

    /**
     * Calculates the current position of the potentiometer in degrees.
     * 
     * @return The rotational angle in degrees.
     */
    public double getAngleDegrees() {
        return (getNormalizedPosition() * fullRangeDegrees) + offsetDegrees;
    }

    /**
     * Calculates the current position of the potentiometer in radians.
     * 
     * @return The rotational angle in radians.
     */
    public double getAngleRadians() {
        return Math.toRadians(getAngleDegrees());
    }
}