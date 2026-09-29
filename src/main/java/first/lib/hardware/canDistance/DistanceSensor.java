package first.lib.hardware.canDistance;

/**
 * Base interface for generic distance or proximity sensing devices.
 */
public interface DistanceSensor {

    /**
     * Retrieves the proximity value.
     * @return The proximity or distance reading.
     */
    public abstract double getProximity();

    /**
     * Evaluates whether a target is currently detected.
     * @return True if a target satisfies the hardware detection threshold.
     */
    public abstract boolean hasTarget();
}