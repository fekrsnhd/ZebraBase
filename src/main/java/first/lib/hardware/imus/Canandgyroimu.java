package first.lib.hardware.imus;

import org.wpilib.hardware.bus.CANPort;
import org.wpilib.math.geometry.Rotation2d;
import com.reduxrobotics.sensors.canandgyro.Canandgyro;

/**
 * IMU implementation using the Redux Robotics Canandgyro.
 */
public class Canandgyroimu extends imu6 {

    private final Canandgyro imu;

    /**
     * Constructs a Canandgyroimu using a specified CAN ID.
     * @param canId The CAN ID of the Canandgyro.
     */
    public Canandgyroimu(int canId, CANPort port) {
        imu = new Canandgyro(canId, port);
    }

    /**
     * Constructs a Canandgyroimu using an existing Canandgyro instance.
     * @param imu The Canandgyro object.
     */
    public Canandgyroimu(Canandgyro imu) {
        this.imu = imu;
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
        return getYawRate();
    }

    @Override
    public void setYaw() {
        resetYaw();
    }

    /**
     * Sets the yaw to a specific angle.
     * @param yawDegrees The desired yaw angle in degrees.
     */
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