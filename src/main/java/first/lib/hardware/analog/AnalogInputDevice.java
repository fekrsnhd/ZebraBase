package first.lib.hardware.analog;

public class AnalogInputDevice extends AnalogInputController {

    private double fullScaleRange = 1.0; // Default to 0-1 scale unless specified

    public AnalogInputDevice(AnalogInputConfig cfg) {
        super(cfg);
    }

    public AnalogInputDevice(int channel) {
        super(new AnalogInputConfig(channel, 0.0, 5.0));
    }

    public AnalogInputDevice(int channel, double minVoltage, double maxVoltage) {
        super(new AnalogInputConfig(channel, minVoltage, maxVoltage));
    }

    public AnalogInputDevice withFullScaleRange(double range) {
        this.fullScaleRange = range;
        return this;
    }

    public double ScaledValue() {
        return getNormalizedPosition() * fullScaleRange;
    }
}