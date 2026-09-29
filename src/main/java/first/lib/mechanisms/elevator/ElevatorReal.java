package first.lib.mechanisms.elevator;

import org.wpilib.command3.Trigger;
import first.lib.hardware.motors.canMotors.CANMotor;

/**
 * The physical hardware implementation for the Elevator IO layer.
 * Manages communication with real motor controllers on the robot.
 */
public class ElevatorReal extends ElevatorIO {

    private final CANMotor motor;

    /**
     * Constructs the hardware implementation of the elevator.
     *
     * @param cfg The elevator configuration containing the actual motor instance.
     */
    public ElevatorReal(ElevatorConfig cfg) {
        super(cfg);
        this.motor = config.motor;
    }

    /**
     * Hardware periodic update loop.
     */
    public void periodic() {
        //comment
    }

    /**
     * Halts the motor operation.
     */
    public void stop() {
        motor.stop();
    }

    /**
     * Drives the physical elevator to the specified height.
     * Clamps the target to prevent exceeding hardware limits.
     *
     * @param heightMeters The requested height in meters.
     */
    public void goToHeight(double heightMeters) {
        double clampedHeight = Math.max(config.minHeightMeters, Math.min(heightMeters, config.maxHeightMeters));
        motor.goToPoint(metersToRotations(clampedHeight));
    }

    /**
     * Drives the physical elevator to a direct rotational value.
     *
     * @param pointRotations The requested point in rotations.
     */
    public void goToPoint(double pointRotations) {
        motor.goToPoint(pointRotations);
    }

    /**
     * Feeds physical voltage to the motor.
     *
     * @param volts The requested voltage.
     */
    public void applyVoltage(double volts) {
        motor.runVoltage(volts);
    }
    
    /**
     * Feeds physical duty cycle percentage to the motor.
     *
     * @param duty The requested duty cycle.
     */
    public void applyDutyCycle(double duty) {
        motor.runDuty(duty);
    }

    /**
     * @return The physical height read from hardware sensors in meters.
     */
    public double getHeight() {
        return rotationsToMeters(motor.getRotations());
    }

    /**
     * @return The physical rotation count from the motor controller.
     */
    public double getRotations() {
        return motor.getRotations();
    }
    
    /**
     * @return The physical velocity in meters per second.
     */
    public double getVelocityMetersPerSecond() {
        return rotationsToMeters(motor.getVelocity()); 
    }

    /**
     * Builds a hardware tolerance check for target height.
     *
     * @param height    Target height in meters.
     * @param tolerance Admissible error in meters.
     * @return Evaluates true when hardware reaches the threshold.
     */
    public Trigger atHeight(double height, double tolerance) {
        return new Trigger(() -> (Math.abs(getHeight() - height) <= tolerance));
    }
}