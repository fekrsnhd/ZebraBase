package first.lib.hardware.encoders.absolute;

import org.wpilib.hardware.rotation.DutyCycleEncoder;

import first.lib.hardware.encoders.EncoderConfig;

public class RevThroughBoreEncoder extends AbsoluteEncoder {

    private final DutyCycleEncoder encoder;
    private double relativeOffset = 0.0;

    public RevThroughBoreEncoder(EncoderConfig cfg) {
        super(cfg);
        this.encoder = new DutyCycleEncoder(config.channel);
    }

    @Override
    public void setPositon(double position) {
        this.relativeOffset = position - getPositon();
    }

    @Override
    public double getPositon() {
        return encoder.get() + relativeOffset;
    }

    @Override
    public void resetPosition() {
        setPositon(0);
    }


    @Override
    public double getVelocity() {
        double vel = encoder.getFrequency();
        return config.inverted ? -vel : vel;
    }
}