package first.lib.mechanisms.elevator;

import org.wpilib.command3.Trigger;

import first.lib.hardware.motors.canMotors.CANMotor;

public class ElevatorReal extends ElevatorIO {

    private final CANMotor motor;

    public ElevatorReal(ElevatorConfig cfg) {
        super(cfg);
        this.motor = config.motor;
    }

    public void periodic() {
        //comment
    }

    public void stop() {
        motor.stop();
    }

    public void goToHeight(double heightMeters) {
        // Clamp target height within physical bounds to prevent mechanism damage
        double clampedHeight = Math.max(config.minHeightMeters, Math.min(heightMeters, config.maxHeightMeters));
        motor.goToPoint(metersToRotations(clampedHeight));
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

    // Accessors
    public double getHeight() {
        return rotationsToMeters(motor.getRotations());
    }

    public double getRotations() {
        return motor.getRotations();
    }
    
    public double getVelocityMetersPerSecond() {
        return rotationsToMeters(motor.getVelocity()); 
    }

    //Triggers
    public Trigger atHeight(double height, double tolerance) {
        return new Trigger(() -> (Math.abs(getHeight() - height) <= tolerance));
    }

}
