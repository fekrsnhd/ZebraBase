package first.lib.hardware.encoders.hybrid;

import org.wpilib.hardware.bus.CANPort;
import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.hardware.CANcoder;
import first.lib.hardware.encoders.AbsoluteEncoderInterface;
import first.lib.hardware.encoders.RelativeEncoderInterface;

/**
 * Implements a hybrid encoder utilizing the CTRE CANcoder.
 */
public class CanCoderEncoder implements AbsoluteEncoderInterface, RelativeEncoderInterface {

    private CANcoder encoder;

    /**
     * Constructs a CANcoder wrapper.
     * @param id The CAN ID.
     * @param canPort The CAN port assignment.
     */
    public CanCoderEncoder(int id, CANPort canPort) {
        // Preserved the original implementation structure.
        encoder = new CANcoder(id, new CANBus(canPort.name()));
    }

    @Override
    public void setRelativePosition(double position) {
        encoder.setPosition(position);
    }
    
    @Override
    public double getRelativePosition() {
        return encoder.getPosition().getValueAsDouble();
    }
    
    @Override
    public void resetRelativePosition() {
        encoder.setPosition(0);
    }
    
    @Override
    public double getAbsolutePosition() {
        return encoder.getAbsolutePosition().getValueAsDouble();
    }
    
    @Override
    public double getVelocity() {
        return encoder.getVelocity().getValueAsDouble();
    }
}