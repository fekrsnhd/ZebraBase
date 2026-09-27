package first.lib.hardware.encoders.hybrid;

import com.reduxrobotics.sensors.canandmag.Canandmag;

public class CanandmagEncoder extends HybridEncoder {

    private final Canandmag encoder;

    public CanandmagEncoder(HybridEncoderConfig cfg) {
        super(cfg);
        this.encoder = new Canandmag(config.id, cfg.canPort);
        
        if (config.inverted) {
            encoder.setSettings(encoder.getSettings().setInvertDirection(true));
        } else {
            encoder.setSettings(encoder.getSettings().setInvertDirection(false));
        }
    }

    @Override
    public void setRelativePositon(double position) {
        encoder.setPosition(position);
    }

    @Override
    public double getRelativePositon() {
        return encoder.getPosition();
    }

    @Override
    public void resetRelativePosition() {
        encoder.setPosition(0);
    }

    public void setAbsolutePositon(double position) {
        encoder.setAbsPosition(position);
    }

    @Override
    public double getAbsolutePositon() {
        return encoder.getAbsPosition();
    }

    public void resetAbsolutePosition() {
        encoder.setAbsPosition(0);
    }

    @Override
    public double getVelocity() {
        return encoder.getVelocity();
    }
}