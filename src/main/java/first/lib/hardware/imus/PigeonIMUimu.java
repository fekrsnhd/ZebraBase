// package first.lib.hardware.imus;

// import org.wpilib.math.geometry.Rotation2d;
// import com.ctre.phoenix.sensors.PigeonIMU;

// public class PigeonIMUimu extends imu6Accum {

//     private final Pigeon imu;

//     public PigeonIMUimu(int deviceNumber) {
//         imu = new PigeonIMU(deviceNumber);
//     }

//     @Override
//     public double getHeading() {
//         return getYaw();
//     }

//     @Override
//     public Rotation2d geRotation2d() {
//         return Rotation2d.fromDegrees(getYaw());
//     }

//     @Override
//     public void resetYaw() {
//         setYaw(0.0);
//     }

//     @Override
//     public double getRotationRate() {
//         return getYawRate();
//     }

//     @Override
//     public void setYaw() {
//         resetYaw();
//     }

//     public void setYaw(double yawDegrees) {
//         imu.setYaw(yawDegrees);
//     }

//     @Override
//     public double getYaw() {
//         double[] ypr = new double[3];
//         imu.getYawPitchRoll(ypr);
//         return ypr[0];
//     }

//     @Override
//     public double getPitch() {
//         double[] ypr = new double[3];
//         imu.getYawPitchRoll(ypr);
//         return ypr[1];
//     }

//     @Override
//     public double getRoll() {
//         double[] ypr = new double[3];
//         imu.getYawPitchRoll(ypr);
//         return ypr[2];
//     }

//     @Override
//     public double getYawRate() {
//         double[] xyz = new double[3];
//         imu.getRawGyro(xyz);
//         return xyz[2];
//     }

//     @Override
//     public double getPitchRate() {
//         double[] xyz = new double[3];
//         imu.getRawGyro(xyz);
//         return xyz[1];
//     }

//     @Override
//     public double getRollRate() {
//         double[] xyz = new double[3];
//         imu.getRawGyro(xyz);
//         return xyz[0];
//     }

//     @Override
//     public double getAccelX() {
//         short[] accel = new short[3];
//         imu.getBiasedAccelerometer(accel);
//         return accel[0] / 16384.0;
//     }

//     @Override
//     public double getAccelY() {
//         short[] accel = new short[3];
//         imu.getBiasedAccelerometer(accel);
//         return accel[1] / 16384.0;
//     }

//     @Override
//     public double getAccelZ() {
//         short[] accel = new short[3];
//         imu.getBiasedAccelerometer(accel);
//         return accel[2] / 16384.0;
//     }

//     @Override
//     public void resetAll() {
//         resetYaw();
//         imu.setAccumZAngle(0, 0);
//     }

//     @Override
//     public double getAccumX() {
//         return 0.0;
//     }

//     @Override
//     public double getAccumY() {
//         return 0.0;
//     }

//     @Override
//     public double getAccumZ() {
//         return imu.getAbsoluteCompassHeading();
//     }
// }