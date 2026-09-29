package first.lib.hardware.encoders;

/**
 * Standard interface for relative encoders that measure position relative to a zeroed state.
 */
public interface RelativeEncoderInterface {

    /**
     * Overrides the current relative position with a specified value.
     * @param position The new position to set.
     */
    public void setRelativePosition(double position);

    /**
     * Retrieves the current accumulated relative position.
     * @return The relative position.
     */
    public double getRelativePosition();

    /**
     * Resets the accumulated relative position to zero.
     */
    public void resetRelativePosition();

    /**
     * Retrieves the current rotational velocity.
     * @return The velocity.
     */
    public double getVelocity();

}