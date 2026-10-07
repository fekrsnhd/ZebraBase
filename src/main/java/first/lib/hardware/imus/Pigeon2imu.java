package first.lib.hardware.imus;

import org.wpilib.hardware.bus.CANPort;
import org.wpilib.math.geometry.Rotation2d;

import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.hardware.Pigeon2;


public class Pigeon2imu extends imu6Accum {

    private final Pigeon2 imu;

    /**
     * @param id The CAN ID.
     * @param bus The CAN bus the device is on.
     */
    public Pigeon2imu(int id, CANPort port) {
        imu = new Pigeon2(id, new CANBus(port));
    }

    public Pigeon2imu(int id, String name) {
        imu = new Pigeon2(id, new CANBus(name));
    }

    @Override
    public double getHeading() {
        return getYaw();
    }

    @Override
    public Rotation2d getRotation2d() {
        return imu.getRotation2d();
    }

    @Override
    public void resetYaw() {
        setYaw(0.0);
    }

    @Override
    public double getRotationRate() {
        // FIXED: Previously returned AngularVelocityXDevice (Roll axis). 
        // Now correctly delegates to getYawRate() for the Z-axis rotation rate.
        return getYawRate();
    }

    public void setYaw(double yawDegrees) {
        imu.setYaw(yawDegrees);
    }

    @Override
    public void setYaw() {
        resetYaw();
    }

    @Override
    public double getYaw() {
        return imu.getYaw().getValueAsDouble();
    }

    @Override
    public double getPitch() {
        return imu.getPitch().getValueAsDouble();
    }

    @Override
    public double getRoll() {
        return imu.getRoll().getValueAsDouble();
    }

    @Override
    public double getYawRate() {
        return imu.getAngularVelocityZWorld().getValueAsDouble();
    }

    @Override
    public double getPitchRate() {
        return imu.getAngularVelocityYWorld().getValueAsDouble();
    }

    @Override
    public double getRollRate() {
        return imu.getAngularVelocityXWorld().getValueAsDouble();
    }

    @Override
    public double getAccelX() {
        return imu.getAccelerationX().getValueAsDouble();
    }

    @Override
    public double getAccelY() {
        return imu.getAccelerationY().getValueAsDouble();
    }

    @Override
    public double getAccelZ() {
        return imu.getAccelerationZ().getValueAsDouble();
    }

    @Override
    public void resetAll() {
        resetYaw();
    }

    @Override
    public double getAccumX() {
        return imu.getAccumGyroX().getValueAsDouble();
    }

    @Override
    public double getAccumY() {
        return imu.getAccumGyroY().getValueAsDouble();
    }

    @Override
    public double getAccumZ() {
        return imu.getAccumGyroZ().getValueAsDouble();
    }
}