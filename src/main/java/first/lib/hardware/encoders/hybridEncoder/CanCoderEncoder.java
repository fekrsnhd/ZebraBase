package first.lib.hardware.encoders.hybridEncoder;

import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.hardware.CANcoder;

public class CanCoderEncoder extends HybridEncoder {

    private CANcoder encoder;

    public CanCoderEncoder(HybridEncoderConfig cfg) {
        super(cfg);
        encoder = new CANcoder(config.id, new CANBus(config.canPort));
    }

    @Override
    public void setRelativePositon(double position) {
        encoder.setPosition(position);
    }
    
    @Override
    public double getRelativePositon() {
        return encoder.getPosition().getValueAsDouble();
    }
    
    @Override
    public void resetRelativePosition() {
        encoder.setPosition(0);
    }
    
    @Override
    public double getAbsolutePositon() {
        return encoder.getAbsolutePosition().getValueAsDouble();
    }
    
    @Override
    public double getVelocity() {
        return encoder.getVelocity().getValueAsDouble();
    }
}
