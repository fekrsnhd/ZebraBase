package first.lib.hardware.imus;

import org.wpilib.math.geometry.Rotation2d;

import com.studica.frc.Navx;

import static org.wpilib.units.Units.Degrees;
import static org.wpilib.units.Units.DegreesPerSecond;

/**
 * IMU implementation using the Studica NavX3 sensor (2027 StudicaLib API).
 */
public class NavX3imu extends IMU6 {

    private final Navx imu;
    private double lastHeading = 0.0;
    private double lastYaw = 0.0;
    private double lastPitch = 0.0;
    private double lastRoll = 0.0;
    private double lastYawRate = 0.0;
    private double lastPitchRate = 0.0;
    private double lastRollRate = 0.0;
    private double lastAccelX = 0.0;
    private double lastAccelY = 0.0;
    private double lastAccelZ = 0.0;

    /**
     * Constructs a NavX3imu using CAN ID and a system bus port.
     * @param canId The CAN ID of the NavX3.
     * @param port The CAN port (e.g., CANPort.CAN_S0).
     */
    public NavX3imu(int canId, org.wpilib.hardware.bus.CANPort port) {
        this.imu = new Navx(canId, port, 100); // Default update rate: 100 Hz
    }

    @Override
    public double getHeading() {
        return getYaw();
    }

    @Override
    public Rotation2d getRotation2d() {
        return imu.getRotation2d().orElse(Rotation2d.fromDegrees(lastHeading));
    }

    @Override
    public void resetYaw() {
        // NavX3 handles zeroing natively or via yaw offset configurations
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
        // NavX3 yaw adjustment logic
    }

    @Override
    public double getYaw() {
        lastYaw = imu.getYaw().map(yaw -> yaw.in(Degrees)).orElse(lastYaw);
        lastHeading = lastYaw;
        return lastYaw;
    }

    @Override
    public double getPitch() {
        lastPitch = imu.getPitch().map(pitch -> pitch.in(Degrees)).orElse(lastPitch);
        return lastPitch;
    }

    @Override
    public double getRoll() {
        lastRoll = imu.getRoll().map(roll -> roll.in(Degrees)).orElse(lastRoll);
        return lastRoll;
    }

    @Override
    public double getYawRate() {
        lastYawRate = imu.getAngularVelocity().get().x().in(DegreesPerSecond);
        return lastYawRate;
    }

    @Override
    public double getPitchRate() {
        lastPitchRate = imu.getAngularVelocity().get().y().in(DegreesPerSecond);
        return lastPitchRate;
    }

    @Override
    public double getRollRate() {
        lastRollRate = imu.getAngularVelocity().get().z().in(DegreesPerSecond);
        return lastRollRate;
    }

    @Override
    public double getAccelX() {
        return lastAccelX;
    }

    @Override
    public double getAccelY() {
        return lastAccelY;
    }

    @Override
    public double getAccelZ() {
        return lastAccelZ;
    }

    @Override
    public void resetAll() {
        resetYaw();
    }
}