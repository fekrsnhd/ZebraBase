package first.robot.mechanisms.swerve;

import first.lib.hardware.imus.IMU6;
import first.lib.mechanisms.drivetrains.swerve.SwerveDriveMechanism;

public class SwerveBuilder {

    public static SwerveDriveMechanism build(IMU6 imu) {
        return SwerveConstants.createDrivetrain(imu);
    }
}