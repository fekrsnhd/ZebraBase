package first.lib.hardware.analog;

import org.wpilib.hardware.discrete.AnalogInput;

/**
 * Base controller managing low-level hardware bindings for analog sensors.
 */
public class AnalogInputController {

    private final AnalogInput input;
    private final AnalogInputConfig config;

    /**
     * Constructs a controller from a full configuration object.
     * 
     * @param config The hardware configuration.
     */
    protected AnalogInputController(AnalogInputConfig config) {
        this.config = config;
        this.input = new AnalogInput(config.getChannel());
    }

    /**
     * Constructs a controller using only an input channel, defaulting the voltage bounds.
     * 
     * @param channel The analog input channel.
     */
    protected AnalogInputController(int channel) {
        this(AnalogInputConfig.builder().channel(channel).build());
    }

    /**
     * Retrieves the current raw voltage read by the hardware.
     * 
     * @return Voltage measured from the sensor.
     */
    public double getVoltage() {
        return input.getVoltage();
    }

    /**
     * Retrieves the raw analog-to-digital converter (ADC) value.
     * 
     * @return The unscaled 12-bit ADC reading.
     */
    public int getValue() {
        return input.getValue();
    }

    /**
     * Normalizes the current voltage as a percentage between the configured minimum and maximum bounds.
     * 
     * @return A clamped value between 0.0 and 1.0 representing the sensor's position within its range.
     */
    public double getNormalizedPosition() {
        double currentVoltage = getVoltage();
        double min = config.getMinVoltage();
        double max = config.getMaxVoltage();
        
        double percentage = (currentVoltage - min) / (max - min);
        return Math.max(0.0, Math.min(1.0, percentage)); // Clamp between 0 and 1
    }

    /**
     * Retrieves the underlying WPILib AnalogInput object.
     * 
     * @return The raw AnalogInput instance.
     */
    public AnalogInput getRawInput() {
        return input;
    }

    /**
     * Closes the analog input and frees hardware resources.
     */
    public void close() {
        input.close();
    }
}