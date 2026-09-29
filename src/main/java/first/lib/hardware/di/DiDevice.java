package first.lib.hardware.di;

import org.wpilib.hardware.discrete.DigitalInput;
import org.wpilib.command3.Trigger;

/**
 * Base wrapper for standard digital input devices.
 */
public class DiDevice {

    private final DigitalInput di;

    /**
     * Constructs a standard digital input device.
     * @param channel The DIO channel on the RoboRIO.
     */
    public DiDevice(int channel) {
        di = new DigitalInput(channel);
    }

    /**
     * Retrieves the current state of the digital input.
     * @return True if the circuit is closed/high, false otherwise.
     */
    public boolean get() {
        return di.get();
    }

    /**
     * Creates a command-based Trigger bound to this device's state.
     * @return A Trigger that evaluates to the current device state.
     */
    public Trigger isTrue() {
        return new Trigger(this::get);
    }

    /**
     * Semantic wrapper for a beam break sensor.
     */
    public static class BeamBreakSensor extends DiDevice {
        public BeamBreakSensor(int channel) {
            super(channel);
        } 
    }

    /**
     * Semantic wrapper for a physical limit switch.
     */
    public static class LimitSwitch extends DiDevice {
        public LimitSwitch(int channel) {
            super(channel);
        } 
    }
}