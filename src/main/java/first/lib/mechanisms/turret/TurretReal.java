package first.lib.mechanisms.turret;

import org.wpilib.command3.Trigger;
import first.lib.hardware.motors.canMotors.CANMotor;

/**
 * Real hardware implementation of the TurretIO.
 * Passes commands through to a physical CAN motor.
 */
public class TurretReal extends TurretIO {

    private final CANMotor motor;

    /**
     * Constructs a TurretReal.
     *
     * @param cfg The turret configuration.
     */
    public TurretReal(TurretConfig cfg) {
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