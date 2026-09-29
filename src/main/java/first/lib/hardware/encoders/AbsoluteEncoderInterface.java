package first.lib.hardware.encoders;

/**
 * Standard interface for absolute encoders that measure physical position independently of a zero point.
 */
public interface AbsoluteEncoderInterface {

    /**
     * Retrieves the current absolute position.
     * @return The absolute position, typically in rotations or degrees.
     */
    public double getAbsolutePosition();
    
}