package first.lib.hardware.imus;

import org.wpilib.framework.RobotBase; // Or edu.wpi.first.wpilibj.RobotBase depending on your WPILib setup
import org.wpilib.hardware.bus.CANPort;
import org.wpilib.math.geometry.Rotation2d;

import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.hardware.Pigeon2;

public class Pigeon2imu extends IMU6Accum {

    private final Pigeon2 imu;

    public Pigeon2imu(int id, CANPort port) {
        imu = new Pigeon2(id, new CANBus(port));
    }

    public Pigeon2imu(int id, String name) {
        imu = new Pigeon2(id, new CANBus(name));
    }

    /**
     * Updates the simulated gyro yaw based on the robot's rotational velocity.
     * @param omegaRadiansPerSecond Robot rotational velocity in rad/s.
     * @param dt Loop time step in seconds (typically 0.02).
     */
    public void simPeriodic(double omegaRadiansPerSecond, double dt) {
        if (RobotBase.isSimulation()) {
            double deltaDegrees = Math.toDegrees(omegaRadiansPerSecond * dt);
            // CTRE Phoenix 6 SimState addYaw accumulates rotation
            imu.getSimState().addYaw(deltaDegrees);
        }
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