package first.lib.hardware.imus;

/**
 * Extension of the imu6 class that supports cumulative rotational data.
 */
public abstract class imu6Accum extends imu6 {

    /** @return The accumulated rotation around the X axis. */
    public abstract double getAccumX();
    /** @return The accumulated rotation around the Y axis. */
    public abstract double getAccumY();
    /** @return The accumulated rotation around the Z axis. */
    public abstract double getAccumZ();
    
}