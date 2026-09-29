package first.lib.mechanisms.singleJointedArm;

import org.wpilib.command3.Trigger;
import first.lib.hardware.motors.canMotors.CANMotor;

/**
 * Real hardware implementation of the SingleJointedArmIO.
 * Passes commands through to a physical CAN motor and handles unit conversions.
 */
public class SingleJointedArmReal extends SingleJointedArmIO {

    private final CANMotor motor;

    /**
     * Constructs a SingleJointedArmReal.
     *
     * @param cfg The arm configuration.
     */
    public SingleJointedArmReal(SingleJointedArmConfig cfg) {
        super(cfg);
        this.motor = config.motor;
    }

    @Override
    public void periodic() {}

    @Override
    public void stop() {
        motor.stop();
    }

    @Override
    public void goToAngle(double angleRads) {
        // Enforce physical software stops before sending to the motor
        double clampedAngle = Math.max(config.minAngleRads, Math.min(angleRads, config.maxAngleRads));
        motor.goToPoint(radiansToRotations(clampedAngle));
    }

    @Override
    public void goToPoint(double pointRotations) {
        motor.goToPoint(pointRotations);
    }

    @Override
    public void applyVoltage(double volts) {
        motor.runVoltage(volts);
    }
    
    @Override
    public void applyDutyCycle(double duty) {
        motor.runDuty(duty);
    }

    @Override
    public double getAngleRads() {
        return rotationsToRadians(motor.getRotations());
    }

    @Override
    public double getRotations() {
        return motor.getRotations();
    }
    
    @Override
    public double getVelocityRadsPerSec() {
        return rotationsToRadians(motor.getVelocity()); 
    }

    @Override
    public Trigger atAngle(double angleRads, double tolerance) {
        return new Trigger(() -> (Math.abs(getAngleRads() - angleRads) <= tolerance));
    }
}