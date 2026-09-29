package first.lib.mechanisms.elevator;

import org.wpilib.command3.Trigger;
import org.wpilib.framework.RobotBase;
import org.wpilib.math.system.Models;
import org.wpilib.simulation.ElevatorSim;

import first.lib.hardware.motors.canMotors.CANMotor;

/**
 * The physics simulation implementation for the Elevator IO layer.
 * Handles modeling WPILib physics in a virtual environment.
 */
public class ElevatorSimulation extends ElevatorIO {

    private final CANMotor motor;
    private final ElevatorSim elevatorSim;
    private double appliedVoltage = 0;

    /**
     * Constructs the simulated elevator.
     *
     * @param cfg The elevator configuration parameters used to generate physical models.
     */
    public ElevatorSimulation(ElevatorConfig cfg) {
        super(cfg);
        this.motor = config.motor;
                
        this.elevatorSim = new ElevatorSim(    
            Models.elevatorFromPhysicalConstants(
                cfg.motorType,
                cfg.massKg,
                cfg.radiusMeters,
                cfg.gearing
            ),
            cfg.motorType,
            cfg.minHeightMeters,
            cfg.maxHeightMeters,
            cfg.simulateGravity,
            cfg.startingHeightMeters,
            cfg.measurementStdDevs
        );
    }

    /**
     * Simulation periodic update loop. Calculates physics steps and pushes virtual sensor data back to the IO interface.
     */
    public void periodic() {
        if (RobotBase.isSimulation()) {
            appliedVoltage = motor.getAppliedVoltage();
            elevatorSim.setInput(appliedVoltage);

            elevatorSim.update(0.02);

            double simRotations = metersToRotations(elevatorSim.getPosition());
            double simVelocityRps = metersToRotations(elevatorSim.getVelocity());
            
            motor.setSimEncoderPosition(simRotations);
            motor.setSimEncoderVelocity(simVelocityRps);
        }    
    }

    /**
     * Stops the simulated motor.
     */
    public void stop() {
        motor.stop();
    }

    /**
     * Commands the simulated mechanism to travel to the specified height.
     *
     * @param heightMeters The target height in meters, which is clamped to physical bounds.
     */
    public void goToHeight(double heightMeters) {
        double clampedHeight = Math.max(config.minHeightMeters, Math.min(heightMeters, config.maxHeightMeters));
        motor.goToPoint(metersToRotations(clampedHeight));
    }

    /**
     * Commands the simulated mechanism to a direct point in rotations.
     *
     * @param pointRotations The target point.
     */
    public void goToPoint(double pointRotations) {
        motor.goToPoint(pointRotations);
    }

    /**
     * Feeds virtual voltage to the motor model.
     *
     * @param volts The voltage value.
     */
    public void applyVoltage(double volts) {
        motor.runVoltage(volts);
    }
    
    /**
     * Feeds virtual duty cycle to the motor model.
     *
     * @param duty The duty cycle percentage.
     */
    public void applyDutyCycle(double duty) {
        motor.runDuty(duty);
    }

    /**
     * @return The modeled height in meters from WPILib physics.
     */
    public double getHeight() {
        return elevatorSim.getPosition();
    }

    /**
     * @return The modeled mechanism position in rotations.
     */
    public double getRotations() {
        return metersToRotations(elevatorSim.getPosition());
    }
    
    /**
     * @return The modeled velocity in meters per second.
     */
    public double getVelocityMetersPerSecond() {
        return elevatorSim.getVelocity(); 
    }

    /**
     * @param height    Target height in meters.
     * @param tolerance Tolerance in meters.
     * @return Evaluates to true when the physics model is within threshold.
     */
    public Trigger atHeight(double height, double tolerance) {
        return new Trigger(() -> (Math.abs(getHeight() - height) <= tolerance));
    }
}