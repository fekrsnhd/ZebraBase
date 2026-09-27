package first.lib.hardware.imus;

import org.wpilib.math.geometry.Rotation2d;
import com.reduxrobotics.sensors.canandgyro.Canandgyro;

public class Canandgyroimu extends imu6 {

    private final Canandgyro imu;

    public Canandgyroimu(int canId) {
        imu = new Canandgyro(canId);
    }

    public Canandgyroimu(Canandgyro imu) {
        this.imu = imu;
    }

    @Override
    public double getHeading() {
        return getYaw();
    }

    @Override
    public Rotation2d geRotation2d() {
        return imu.getRotation2d();
    }

    @Override
    public void resetYaw() {
        setYaw(0.0);
    }

    @Override
    public double getRotationRate() {
        return getYawRate();
    }

    @Override
    public void setYaw() {
        resetYaw();
    }

    public void setYaw(double yawDegrees) {
        imu.setYaw(yawDegrees);
    }

    @Override
    public double getYaw() {
        return imu.getYaw();
    }

    @Override
    public double getPitch() {
        return imu.getPitch();
    }

    @Override
    public double getRoll() {
        return imu.getRoll();
    }

    @Override
    public double getYawRate() {
        return imu.getAngularVelocityYaw();
    }

    @Override
    public double getPitchRate() {
        return imu.getAngularVelocityPitch();
    }

    @Override
    public double getRollRate() {
        return imu.getAngularVelocityRoll();
    }

    @Override
    public double getAccelX() {
        return imu.getAccelerationX();
    }

    @Override
    public double getAccelY() {
        return imu.getAccelerationY();
    }

    @Override
    public double getAccelZ() {
        return imu.getAccelerationZ();
    }

    @Override
    public void resetAll() {
        resetYaw();
    }

}