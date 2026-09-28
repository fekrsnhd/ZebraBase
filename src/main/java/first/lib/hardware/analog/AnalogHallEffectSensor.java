package first.lib.hardware.analog;

public class AnalogHallEffectSensor extends AnalogInputController {

    private double thresholdVoltage = 2.5;

    public AnalogHallEffectSensor(int channel) {
        super(channel);
    }

    public AnalogHallEffectSensor(AnalogInputConfig config) {
        super(config);
    }

    public AnalogHallEffectSensor withThresholdVoltage(double thresholdVoltage) {
        this.thresholdVoltage = thresholdVoltage;
        return this;
    }

    public double getMagneticFieldStrength() {
        return getNormalizedPosition();
    }

    public boolean isMagnetDetected() {
        return getVoltage() >= thresholdVoltage;
    }
}