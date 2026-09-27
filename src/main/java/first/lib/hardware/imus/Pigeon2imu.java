package first.lib.hardware.imus;

import org.wpilib.math.geometry.Rotation2d;

import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.hardware.Pigeon2;

public class Pigeon2imu extends imu6Accum {

    private final Pigeon2 imu;

    public Pigeon2imu(int id, CANBus bus) {
        imu = new Pigeon2(id, bus);
    }

    public double getHeading() {
        return getYaw();
    }

    public Rotation2d geRotation2d() {
        return imu.getRotation2d();
    }

    public void resetYaw() {
        setYaw(0.0);
    }

    public double getRotationRate() {
        return imu.getAngularVelocityXDevice().getValueAsDouble();
    }

    public void setYaw(double yawDegrees) {
        imu.setYaw(yawDegrees);
    }

    public void setYaw() {
        resetYaw();
    }

    public double getYaw() {
        return imu.getYaw().getValueAsDouble();
    }

    public double getPitch() {
        return imu.getPitch().getValueAsDouble();
    }

    public double getRoll() {
        return imu.getRoll().getValueAsDouble();
    }

    public double getYawRate() {
        return imu.getAngularVelocityZWorld().getValueAsDouble();
    }

    public double getPitchRate() {
        return imu.getAngularVelocityYWorld().getValueAsDouble();
    }

    public double getRollRate() {
        return imu.getAngularVelocityXWorld().getValueAsDouble();
    }

    public double getAccelX() {
        return imu.getAccelerationX().getValueAsDouble();
    }

    public double getAccelY() {
        return imu.getAccelerationY().getValueAsDouble();
    }

    public double getAccelZ() {
        return imu.getAccelerationZ().getValueAsDouble();
    }

    public void resetAll() {
        resetYaw();
    }

    public double getAccumX() {
        return imu.getAccumGyroX().getValueAsDouble();
    }

    public double getAccumY() {
        return imu.getAccumGyroY().getValueAsDouble();
    }

    public double getAccumZ() {
        return imu.getAccumGyroZ().getValueAsDouble();
    }
}