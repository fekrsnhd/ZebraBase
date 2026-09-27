package first.lib.hardware.imus;

import org.wpilib.math.geometry.Rotation2d;

public abstract class imu6 {
    public abstract double getHeading();
    public abstract Rotation2d geRotation2d();
    public abstract void resetYaw();
    public abstract double getRotationRate();
    public abstract void setYaw();

    public abstract double getYaw();
    public abstract double getPitch();
    public abstract double getRoll();

    public abstract double getYawRate();
    public abstract double getPitchRate();
    public abstract double getRollRate();

    public abstract double getAccelX();
    public abstract double getAccelY();
    public abstract double getAccelZ();

    public abstract void resetAll();
}