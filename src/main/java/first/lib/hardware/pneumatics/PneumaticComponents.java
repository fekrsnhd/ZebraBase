package first.lib.hardware.pneumatics;

import org.wpilib.hardware.pneumatic.Compressor;
import org.wpilib.hardware.pneumatic.DoubleSolenoid;
import org.wpilib.hardware.pneumatic.Solenoid;

public class PneumaticComponents {

    public static class CompressorWrapper {
        private final Compressor compressor;

        public CompressorWrapper(PneumaticBoards board) {
            compressor = new Compressor(board.getPort(), board.getType());
        }

        public void enableDigital() {
            compressor.enableDigital();
        }

        public void enableAnalog(double minPressure, double maxPressure) {
            compressor.enableAnalog(minPressure, maxPressure);
        }

        public void disable() {
            compressor.disable();
        }
        
        public double getPressure() {
            return compressor.getPressure();
        }
    }

    public static class SingleSolenoidDevice {
        private final Solenoid solenoid;

        public SingleSolenoidDevice(PneumaticBoards board, int channel) {
            solenoid = new Solenoid(board.getPort(), board.getType(), channel);
        }

        public void set(boolean on) {
            solenoid.set(on);
        }

        public boolean get() {
            return solenoid.get();
        }
    }

    public static class DoubleSolenoidDevice {
        private final DoubleSolenoid doubleSolenoid;

        public DoubleSolenoidDevice(PneumaticBoards board, int forwardChannel, int reverseChannel) {
            doubleSolenoid = new DoubleSolenoid(board.getPort(), board.getType(), forwardChannel, reverseChannel);
        }

        public void set(DoubleSolenoid.Value value) {
            doubleSolenoid.set(value);
        }

        public DoubleSolenoid.Value get() {
            return doubleSolenoid.get();
        }
    }
}