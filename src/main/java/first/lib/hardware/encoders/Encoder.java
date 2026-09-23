package first.lib.hardware.encoders;

public abstract class Encoder {
    
    protected final EncoderConfig config;

    protected Encoder(EncoderConfig cfg) {
        this.config = cfg;
    }

    public EncoderConfig getConfig() {
        return config;
    }

    public abstract void setPositon(double position);

    public abstract double getPositon();

    public abstract void resetPosition();

    public abstract double getVelocity();

}