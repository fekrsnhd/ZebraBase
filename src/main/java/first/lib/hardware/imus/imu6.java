package first.lib.hardware.imus;

import org.wpilib.math.geometry.Rotation2d;

/**
 * Base abstract class defining the contract for a 6-axis IMU.
 */
public abstract class imu6 {
    /** @return The continuous heading of the robot in degrees. */
    public abstract double getHeading();
    
    /** @return The Rotation2d representation of the robot's heading. */
    public abstract Rotation2d getRotation2d(); // Typo: should likely be getRotation2d()
    
    /** Zeroes the yaw axis. */
    public abstract void resetYaw();
    
    /** @return The rotational velocity in degrees per second. */
    public abstract double getRotationRate();
    
    /** Overloaded method to reset the yaw axis. */
    public abstract void setYaw();

    /** @return The yaw angle in degrees. */
    public abstract double getYaw();
    /** @return The pitch angle in degrees. */
    public abstract double getPitch();
    /** @return The roll angle in degrees. */
    public abstract double getRoll();

    /** @return The yaw rotational velocity in degrees per second. */
    public abstract double getYawRate();
    /** @return The pitch rotational velocity in degrees per second. */
    public abstract double getPitchRate();
    /** @return The roll rotational velocity in degrees per second. */
    public abstract double getRollRate();

    /** @return Acceleration along the X axis in Gs. */
    public abstract double getAccelX();
    /** @return Acceleration along the Y axis in Gs. */
    public abstract double getAccelY();
    /** @return Acceleration along the Z axis in Gs. */
    public abstract double getAccelZ();

    /** Resets all relevant cumulative state inside the IMU. */
    public abstract void resetAll();
}