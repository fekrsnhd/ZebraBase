package first.lib.hardware.analog;

import org.wpilib.hardware.discrete.AnalogInput;

public class AnalogInputController {

    private final AnalogInput input;
    private final AnalogInputConfig config;

    protected AnalogInputController(AnalogInputConfig config) {
        this.config = config;
        this.input = new AnalogInput(config.getChannel());
    }

    protected AnalogInputController(int channel) {
        this(AnalogInputConfig.builder().channel(channel).build());
    }

    public double getVoltage() {
        return input.getVoltage();
    }

    public int getValue() {
        return input.getValue();
    }

    public double getNormalizedPosition() {
        double currentVoltage = getVoltage();
        double min = config.getMinVoltage();
        double max = config.getMaxVoltage();
        
        double percentage = (currentVoltage - min) / (max - min);
        return Math.max(0.0, Math.min(1.0, percentage)); // Clamp between 0 and 1
    }

    public AnalogInput getRawInput() {
        return input;
    }

    public void close() {
        input.close();
    }
}