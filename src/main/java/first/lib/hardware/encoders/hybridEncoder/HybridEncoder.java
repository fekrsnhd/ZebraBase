package first.lib.hardware.encoders.hybridEncoder;

public abstract class HybridEncoder {
    
    protected final HybridEncoderConfig config;

    protected HybridEncoder(HybridEncoderConfig cfg) {
        this.config = cfg;
    }

    public HybridEncoderConfig getConfig() {
        return config;
    }

    public abstract void setRelativePositon(double position);

    public abstract double getRelativePositon();

    public abstract void resetRelativePosition();

    public abstract double getAbsolutePositon();

    public abstract double getVelocity();

}