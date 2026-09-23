package first.lib.hardware.encoders.absolute;

import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.hardware.CANcoder;

import first.lib.hardware.encoders.EncoderConfig;



public class CanCoder extends AbsoluteEncoder {

    private CANcoder encoder;

    public CanCoder(EncoderConfig cfg) {
        super(cfg);
        encoder = new CANcoder(config.id, new CANBus(config.canPort));
    }

    public void setPositon(double position) {
        encoder.setPosition(position);
    }

    public double getPositon() {
        return encoder.getAbsolutePosition().getValueAsDouble();
    }

    public void resetPosition() {
        encoder.setPosition(0);
    }

    public double getVelocity() {
        return encoder.getVelocity().getValueAsDouble();
    }
}
