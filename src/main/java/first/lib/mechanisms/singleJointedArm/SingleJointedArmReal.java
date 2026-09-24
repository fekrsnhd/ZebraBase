package first.lib.mechanisms.singleJointedArm;

import org.wpilib.command3.Trigger;
import first.lib.hardware.motors.canMotors.CANMotor;

public class SingleJointedArmReal extends SingleJointedArmIO {

    private final CANMotor motor;

    public SingleJointedArmReal(SingleJointedArmConfig cfg) {
        super(cfg);
        this.motor = config.motor;
    }

    public void periodic() {}

    public void stop() {
        motor.stop();
    }

    public void goToAngle(double angleRads) {
        double clampedAngle = Math.max(config.minAngleRads, Math.min(angleRads, config.maxAngleRads));
        motor.goToPoint(radiansToRotations(clampedAngle));
    }

    public void goToPoint(double pointRotations) {
        motor.goToPoint(pointRotations);
    }

    public void applyVoltage(double volts) {
        motor.runVoltage(volts);
    }
    
    public void applyDutyCycle(double duty) {
        motor.runDuty(duty);
    }

    public double getAngleRads() {
        return rotationsToRadians(motor.getRotations());
    }

    public double getRotations() {
        return motor.getRotations();
    }
    
    public double getVelocityRadsPerSec() {
        return rotationsToRadians(motor.getVelocity()); 
    }

    public Trigger atAngle(double angleRads, double tolerance) {
        return new Trigger(() -> (Math.abs(getAngleRads() - angleRads) <= tolerance));
    }
}