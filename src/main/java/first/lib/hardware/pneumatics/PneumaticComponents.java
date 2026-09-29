package first.lib.hardware.pneumatics;

import org.wpilib.hardware.pneumatic.Compressor;
import org.wpilib.hardware.pneumatic.DoubleSolenoid;
import org.wpilib.hardware.pneumatic.Solenoid;

/**
 * Contains wrapper classes for various pneumatic components.
 */
public class PneumaticComponents {

    /**
     * A wrapper for controlling a pneumatic compressor.
     */
    public static class CompressorWrapper {
        private final Compressor compressor;

        /**
         * Constructs a new CompressorWrapper for a specific board.
         * @param board The PneumaticBoards instance controlling this compressor.
         */
        public CompressorWrapper(PneumaticBoards board) {
            compressor = new Compressor(board.getPort(), board.getType());
        }

        /**
         * Enables the compressor in digital mode (uses standard pressure switch).
         */
        public void enableDigital() {
            compressor.enableDigital();
        }

        /**
         * Enables the compressor in analog mode with custom pressure thresholds.
         * @param minPressure The minimum pressure to activate the compressor.
         * @param maxPressure The maximum pressure to shut off the compressor.
         */
        public void enableAnalog(double minPressure, double maxPressure) {
            compressor.enableAnalog(minPressure, maxPressure);
        }

        /**
         * Disables the compressor entirely.
         */
        public void disable() {
            compressor.disable();
        }
        
        /**
         * Retrieves the current pressure read by the analog sensor.
         * @return The pressure in PSI.
         */
        public double getPressure() {
            return compressor.getPressure();
        }
    }

    /**
     * A wrapper for a single-acting pneumatic solenoid.
     */
    public static class SingleSolenoidDevice {
        private final Solenoid solenoid;

        /**
         * Constructs a new single solenoid device.
         * @param board The PneumaticBoards instance controlling this solenoid.
         * @param channel The channel on the board the solenoid is wired to.
         */
        public SingleSolenoidDevice(PneumaticBoards board, int channel) {
            solenoid = new Solenoid(board.getPort(), board.getType(), channel);
        }

        /**
         * Sets the state of the solenoid.
         * @param on True to activate, false to deactivate.
         */
        public void set(boolean on) {
            solenoid.set(on);
        }

        /**
         * Retrieves the current state of the solenoid.
         * @return True if active, false otherwise.
         */
        public boolean get() {
            return solenoid.get();
        }
    }

    /**
     * A wrapper for a double-acting pneumatic solenoid.
     */
    public static class DoubleSolenoidDevice {
        private final DoubleSolenoid doubleSolenoid;

        /**
         * Constructs a new double solenoid device.
         * @param board The PneumaticBoards instance controlling this solenoid.
         * @param forwardChannel The channel corresponding to forward movement.
         * @param reverseChannel The channel corresponding to reverse movement.
         */
        public DoubleSolenoidDevice(PneumaticBoards board, int forwardChannel, int reverseChannel) {
            doubleSolenoid = new DoubleSolenoid(board.getPort(), board.getType(), forwardChannel, reverseChannel);
        }

        /**
         * Sets the state of the double solenoid.
         * @param value The target state (kForward, kReverse, or kOff).
         */
        public void set(DoubleSolenoid.Value value) {
            doubleSolenoid.set(value);
        }

        /**
         * Retrieves the current set state of the double solenoid.
         * @return The current state value.
         */
        public DoubleSolenoid.Value get() {
            return doubleSolenoid.get();
        }
    }
}