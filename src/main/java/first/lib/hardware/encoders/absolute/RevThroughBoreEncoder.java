package first.lib.hardware.encoders.absolute;

import org.wpilib.hardware.rotation.DutyCycleEncoder;
import first.lib.hardware.encoders.AbsoluteEncoderInterface;

/**
 * Wrapper for the REV Through Bore Encoder using a duty cycle signal.
 */
public class RevThroughBoreEncoder implements AbsoluteEncoderInterface {

    private final DutyCycleEncoder encoder;
    private double relativeOffset = 0.0;
    private boolean inverted = false;

    /**
     * Constructs a REV Through Bore Encoder via DIO.
     * @param channel The DIO channel.
     * @param inv Whether to invert the encoder reading.
     */
    public RevThroughBoreEncoder(int channel, boolean inv) {
        this.inverted = inv;
        this.encoder = new DutyCycleEncoder(channel);
    }

    @Override
    public double getAbsolutePosition() {
        double pos = encoder.get() + relativeOffset;
        return inverted ? -pos : pos; 
    }

}