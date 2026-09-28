package first.lib.hardware.analog;

public class AnalogPotentiometerDevice extends AnalogInputController {

    private final double fullRangeDegrees;
    private final double offsetDegrees;

    public AnalogPotentiometerDevice(int channel, double fullRangeDegrees, double offsetDegrees) {
        super(channel);
        this.fullRangeDegrees = fullRangeDegrees;
        this.offsetDegrees = offsetDegrees;
    }

    public AnalogPotentiometerDevice(AnalogInputConfig config, double fullRangeDegrees, double offsetDegrees) {
        super(config);
        this.fullRangeDegrees = fullRangeDegrees;
        this.offsetDegrees = offsetDegrees;
    }

    public double getAngleDegrees() {
        return (getNormalizedPosition() * fullRangeDegrees) + offsetDegrees;
    }

    public double getAngleRadians() {
        return Math.toRadians(getAngleDegrees());
    }
}