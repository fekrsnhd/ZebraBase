package first.lib.hardware.analog;

/**
 * An analog sensor that measures the strength of a magnetic field.
 */
public class AnalogHallEffectSensor extends AnalogInputController {

    private double thresholdVoltage = 2.5;

    /**
     * Constructs a Hall Effect sensor using a default channel.
     * 
     * @param channel The analog input channel.
     */
    public AnalogHallEffectSensor(int channel) {
        super(channel);
    }

    /**
     * Constructs a Hall Effect sensor using a specific configuration.
     * 
     * @param config The AnalogInputConfig defining hardware properties.
     */
    public AnalogHallEffectSensor(AnalogInputConfig config) {
        super(config);
    }

    /**
     * Modifies the voltage threshold used to detect the presence of a magnet.
     * 
     * @param thresholdVoltage The voltage at which a magnet is considered "detected".
     * @return The current AnalogHallEffectSensor instance for chaining.
     */
    public AnalogHallEffectSensor withThresholdVoltage(double thresholdVoltage) {
        this.thresholdVoltage = thresholdVoltage;
        return this;
    }

    /**
     * Retrieves the strength of the magnetic field normalized from 0.0 to 1.0.
     * 
     * @return The normalized field strength.
     */
    public double getMagneticFieldStrength() {
        return getNormalizedPosition();
    }

    /**
     * Checks if the measured field strength exceeds the defined threshold.
     * 
     * @return True if the current voltage is greater than or equal to the threshold voltage.
     */
    public boolean isMagnetDetected() {
        return getVoltage() >= thresholdVoltage;
    }
}