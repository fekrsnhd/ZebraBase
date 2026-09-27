// package first.lib.hardware.imus;

// import org.wpilib.math.geometry.Rotation2d;
// import com.studica.frc.AHRS;

// public class NavXMicroimu extends imu6Accum {

//     private final AHRS imu;

//     public NavXMicroimu() {
//         imu = new AHRS(AHRS.NavXComType.kUSB1);
//     }

//     public NavXMicroimu(AHRS.NavXComType comType) {
//         imu = new AHRS(comType);
//     }

//     public NavXMicroimu(AHRS imu) {
//         this.imu = imu;
//     }

//     @Override
//     public double getHeading() {
//         return getYaw();
//     }

//     @Override
//     public Rotation2d geRotation2d() {
//         return imu.getRotation2d();
//     }

//     @Override
//     public void resetYaw() {
//         imu.zeroYaw();
//     }

//     @Override
//     public double getRotationRate() {
//         return getYawRate();
//     }

//     @Override
//     public void setYaw() {
//         resetYaw();
//     }

//     @Override
//     public double getYaw() {
//         return imu.getYaw();
//     }

//     @Override
//     public double getPitch() {
//         return imu.getPitch();
//     }

//     @Override
//     public double getRoll() {
//         return imu.getRoll();
//     }

//     @Override
//     public double getYawRate() {
//         return imu.getRate();
//     }

//     @Override
//     public double getPitchRate() {
//         return imu.getRawGyroY();
//     }

//     @Override
//     public double getRollRate() {
//         return imu.getRawGyroX();
//     }

//     @Override
//     public double getAccelX() {
//         return imu.getWorldLinearAccelX();
//     }

//     @Override
//     public double getAccelY() {
//         return imu.getWorldLinearAccelY();
//     }

//     @Override
//     public double getAccelZ() {
//         return imu.getWorldLinearAccelZ();
//     }

//     @Override
//     public void resetAll() {
//         resetYaw();
//         imu.reset();
//     }

//     @Override
//     public double getAccumX() {
//         return imu.getRawGyroX();
//     }

//     @Override
//     public double getAccumY() {
//         return imu.getRawGyroY();
//     }

//     @Override
//     public double getAccumZ() {
//         return imu.getAngle();
//     }
// }