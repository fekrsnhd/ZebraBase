package first.lib.hardware.analog;

public class StringPotentiometer extends AnalogInputController {

    private final double maxExtensionInches;
    private final double zeroOffsetInches;

    public StringPotentiometer(int channel, double maxExtensionInches) {
        this(channel, maxExtensionInches, 0.0);
    }

    public StringPotentiometer(int channel, double maxExtensionInches, double zeroOffsetInches) {
        super(channel);
        this.maxExtensionInches = maxExtensionInches;
        this.zeroOffsetInches = zeroOffsetInches;
    }

    public StringPotentiometer(AnalogInputConfig config, double maxExtensionInches, double zeroOffsetInches) {
        super(config);
        this.maxExtensionInches = maxExtensionInches;
        this.zeroOffsetInches = zeroOffsetInches;
    }

    /**
     * Gets total extended distance in inches.
     */
    public double getExtensionInches() {
        return (getNormalizedPosition() * maxExtensionInches) + zeroOffsetInches;
    }

    /**
     * Gets total extended distance in meters.
     */
    public double getExtensionMeters() {
        return getExtensionInches() * 0.0254;
    }
}