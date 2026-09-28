package first.lib.hardware.di;

import org.wpilib.hardware.discrete.DigitalInput;
import org.wpilib.command3.Trigger;

public class DiDevice {

    private final DigitalInput di;

    public DiDevice(int channel) {
        di = new DigitalInput(channel);
    }

    public boolean get() {
        return di.get();
    }

    public Trigger isTrue() {
        return new Trigger(this::get);
    }

    public static class BeamBreakSensor extends DiDevice {
        public BeamBreakSensor(int channel) {
            super(channel);
        } 
    }

    public static class LimitSwitch extends DiDevice {
        public LimitSwitch(int channel) {
            super(channel);
        } 
    }
}