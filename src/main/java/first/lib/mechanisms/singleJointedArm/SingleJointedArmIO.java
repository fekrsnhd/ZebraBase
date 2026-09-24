package first.lib.mechanisms.singleJointedArm;

import org.littletonrobotics.junction.AutoLog;
import org.wpilib.command3.Trigger;

public abstract class SingleJointedArmIO {

    @AutoLog
    public static class SingleJointedArmIOInputs {
        public double positionRads = 0;
        public double velocityRadsPerSec = 0;
        public double rotations = 0;
    }

    protected final SingleJointedArmConfig config;

    protected SingleJointedArmIO(SingleJointedArmConfig cfg) {
        this.config = cfg;
    }

    public abstract void periodic();

    public double rotationsToRadians(double rotations) {
        return rotations * (2.0 * Math.PI) / config.gearing;
    }

    public double radiansToRotations(double radians) {
        return radians * config.gearing / (2.0 * Math.PI);
    }

    public abstract void stop();

    public abstract void goToAngle(double angleRads);

    public abstract void goToPoint(double pointRotations);

    public abstract void applyVoltage(double volts);

    public abstract void applyDutyCycle(double duty);

    public abstract double getAngleRads();

    public abstract double getRotations();

    public abstract double getVelocityRadsPerSec();
    
    public abstract Trigger atAngle(double angleRads, double toleranceRads);

    public void updateInputs(SingleJointedArmIOInputs inputs) {
        inputs.positionRads = getAngleRads();
        inputs.velocityRadsPerSec = getVelocityRadsPerSec();
        inputs.rotations = getRotations();
    }
}