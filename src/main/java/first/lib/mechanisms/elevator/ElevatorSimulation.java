package first.lib.mechanisms.elevator;

import org.wpilib.command3.Trigger;
import org.wpilib.framework.RobotBase;
import org.wpilib.math.system.Models;
import org.wpilib.simulation.ElevatorSim;

import first.lib.hardware.motors.canMotors.CANMotor;

public class ElevatorSimulation extends ElevatorIO {

    private final CANMotor motor;

    private final ElevatorSim elevatorSim;

    private double appliedVoltage = 0;


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
        return elevatorSim.getPosition();
    }

    public double getRotations() {
        return metersToRotations(elevatorSim.getPosition());
    }
    
    public double getVelocityMetersPerSecond() {
        return elevatorSim.getVelocity(); 
    }

    //Triggers
    public Trigger atHeight(double height, double tolerance) {
        return new Trigger(() -> (Math.abs(getHeight() - height) <= tolerance));
    }

}
