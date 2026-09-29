package first.lib.hardware.analog;

/**
 * A general-purpose analog device capable of mapping raw voltage into a custom scaled range.
 */
public class AnalogInputDevice extends AnalogInputController {

    private double fullScaleRange = 1.0; // Default to 0-1 scale unless specified

    /**
     * Constructs a device using a pre-built configuration.
     * 
     * @param cfg The configuration bounds.
     */
    public AnalogInputDevice(AnalogInputConfig cfg) {
        super(cfg);
    }

    /**
     * Constructs a device on a specific channel, assuming a standard 0V to 5V operating range.
     * 
     * @param channel The analog input channel.
     */
    public AnalogInputDevice(int channel) {
        super(new AnalogInputConfig(channel, 0.0, 5.0));
    }

    /**
     * Constructs a device on a specific channel with a custom operational voltage range.
     * 
     * @param channel    The analog input channel.
     * @param minVoltage Minimum expected voltage.
     * @param maxVoltage Maximum expected voltage.
     */
    public AnalogInputDevice(int channel, double minVoltage, double maxVoltage) {
        super(new AnalogInputConfig(channel, minVoltage, maxVoltage));
    }

    /**
     * Assigns a physical scalar maximum to correspond with the maximum voltage reading.
     * 
     * @param range The maximum physical value.
     * @return The current AnalogInputDevice instance for chaining.
     */
    public AnalogInputDevice withFullScaleRange(double range) {
        this.fullScaleRange = range;
        return this;
    }

    /**
     * Returns the physical value of the sensor based on the normalized voltage and full scale range.
     * 
     * @return The scaled reading.
     */
    public double ScaledValue() {
        return getNormalizedPosition() * fullScaleRange;
    }
}