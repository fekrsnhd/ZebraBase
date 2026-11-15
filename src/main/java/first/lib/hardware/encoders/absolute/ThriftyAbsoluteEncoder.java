package first.lib.hardware.encoders.absolute;

import org.wpilib.hardware.rotation.AnalogEncoder;

import first.lib.hardware.encoders.AbsoluteEncoderInterface;

/**
 * Wrapper for a Thrifty Absolute Encoder using an analog or duty cycle connection.
 */
public class ThriftyAbsoluteEncoder implements AbsoluteEncoderInterface {

    private final AnalogEncoder encoder;
    private boolean inverted = false;
    private double offset = 0.0;

    /**
     * Constructs a Thrifty Absolute Encoder on an analog channel.
     * @param channel The analog input channel.
     * @param inv Whether to invert the encoder reading.
     * @param offset The absolute offset in rotations.
     */
    public ThriftyAbsoluteEncoder(int channel, boolean inv, double offset) {
        this.encoder = new AnalogEncoder(channel);
        this.inverted = inv;
        this.offset = offset;
    }

    @Override
    public double getAbsolutePosition() {
        double pos = encoder.get() - offset;
        pos = pos - Math.floor(pos); // Keep within [0.0, 1.0)
        return inverted ? (1.0 - pos) : pos;
    }
}