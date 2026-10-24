package first.lib.mechanisms.drivetrains.mecanum;

import org.littletonrobotics.junction.AutoLog;
import org.wpilib.math.kinematics.MecanumDriveWheelPositions;
import org.wpilib.math.kinematics.MecanumDriveWheelVelocities;

public abstract class MecanumDriveIO {

    @AutoLog
    public static class MecanumDriveIOInputs {
        public double flPositionMeters = 0.0, flVelocityMetersPerSecond = 0.0;
        public double frPositionMeters = 0.0, frVelocityMetersPerSecond = 0.0;
        public double rlPositionMeters = 0.0, rlVelocityMetersPerSecond = 0.0;
        public double rrPositionMeters = 0.0, rrVelocityMetersPerSecond = 0.0;
    }

    protected final MecanumDriveConfig config;

    protected MecanumDriveIO(MecanumDriveConfig config) {
        this.config = config;
    }

    public abstract void periodic();
    public abstract void setDesiredWheelVelocities(MecanumDriveWheelVelocities speeds);
    public abstract void stop();

    public abstract MecanumDriveWheelPositions getWheelPositions();
    public abstract MecanumDriveWheelVelocities getWheelVelocities();

    public void updateInputs(MecanumDriveIOInputs inputs) {
        var pos = getWheelPositions();
        var vel = getWheelVelocities();
        inputs.flPositionMeters = pos.frontLeft;
        inputs.frPositionMeters = pos.frontRight;
        inputs.rlPositionMeters = pos.rearLeft;
        inputs.rrPositionMeters = pos.rearRight;

        inputs.flVelocityMetersPerSecond = vel.frontLeft;
        inputs.frVelocityMetersPerSecond = vel.frontRight;
        inputs.rlVelocityMetersPerSecond = vel.rearLeft;
        inputs.rrVelocityMetersPerSecond = vel.rearRight;
    }
}