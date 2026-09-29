package first.lib.hardware.analog;

/**
 * Represents a string potentiometer (draw-wire sensor) measuring linear displacement.
 */
public class StringPotentiometer extends AnalogInputController {

    private final double maxExtensionInches;
    private final double zeroOffsetInches;

    /**
     * Constructs a string potentiometer with no physical offset.
     * 
     * @param channel            The analog input channel.
     * @param maxExtensionInches The physical length of the string at maximum extension.
     */
    public StringPotentiometer(int channel, double maxExtensionInches) {
        this(channel, maxExtensionInches, 0.0);
    }

    /**
     * Constructs a string potentiometer with a physical offset.
     * 
     * @param channel            The analog input channel.
     * @param maxExtensionInches The physical length of the string at maximum extension.
     * @param zeroOffsetInches   A physical length offset to add to the reading.
     */
    public StringPotentiometer(int channel, double maxExtensionInches, double zeroOffsetInches) {
        super(channel);
        this.maxExtensionInches = maxExtensionInches;
        this.zeroOffsetInches = zeroOffsetInches;
    }

    /**
     * Constructs a string potentiometer using a configuration and offset.
     * 
     * @param config             The hardware configuration bounds.
     * @param maxExtensionInches The physical length of the string at maximum extension.
     * @param zeroOffsetInches   A physical length offset to add to the reading.
     */
    public StringPotentiometer(AnalogInputConfig config, double maxExtensionInches, double zeroOffsetInches) {
        super(config);
        this.maxExtensionInches = maxExtensionInches;
        this.zeroOffsetInches = zeroOffsetInches;
    }

    /**
     * Gets the total extended distance in inches.
     * 
     * @return The linear extension in inches.
     */
    public double getExtensionInches() {
        return (getNormalizedPosition() * maxExtensionInches) + zeroOffsetInches;
    }

    /**
     * Gets the total extended distance in meters.
     * 
     * @return The linear extension in meters.
     */
    public double getExtensionMeters() {
        return getExtensionInches() * 0.0254;
    }
}