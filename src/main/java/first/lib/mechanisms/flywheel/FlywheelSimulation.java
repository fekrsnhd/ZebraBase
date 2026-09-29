package first.lib.mechanisms.flywheel;

import org.wpilib.command3.Trigger;
import org.wpilib.framework.RobotBase;
import org.wpilib.simulation.FlywheelSim;
import org.wpilib.math.system.Models;
import first.lib.hardware.motors.canMotors.CANMotor;

/**
 * Synthetic environment physics modeling for flywheels.
 */
public class FlywheelSimulation extends FlywheelIO {

    private final CANMotor motor;
    private final FlywheelSim flywheelSim;
    private double appliedVoltage = 0;

    /**
     * Projects configuration specifications against WPILib mathematical matrices.
     *
     * @param cfg Input variables used inside matrix simulation modeling.
     */
    public FlywheelSimulation(FlywheelConfig cfg) {
        super(cfg);
        this.motor = config.motor;
                
        this.flywheelSim = new FlywheelSim(
            Models.flywheelFromPhysicalConstants(
                cfg.motorType, 
                cfg.moiKgMetersSquared, 
                cfg.gearing),
            cfg.motorType,
            cfg.measurementStdDevs
        );
    }

    /**
     * Computes the step interval state change.
     * Replaces real sensor inputs with values derived from theoretical models.
     */
    public void periodic() {
        if (RobotBase.isSimulation()) {
            appliedVoltage = motor.getAppliedVoltage();
            flywheelSim.setInput(appliedVoltage);
            flywheelSim.update(0.02);
            
            // Flywheel simulations rely purely on velocity metrics
            double simVelocityRps = flywheelSim.getAngularVelocity() / (2.0 * Math.PI);
            motor.setSimEncoderVelocity(simVelocityRps);
        }    
    }

    /** Terminates simulated feed logic. */
    public void stop() {
        motor.stop();
    }

    /**
     * Commands simulated object via velocity logic requirements.
     *
     * @param rpm Modeled expectation.
     */
    public void runRPM(double rpm) {
        motor.runRPM(rpm);
    }

    /**
     * Pushes modeled electricity calculations.
     *
     * @param volts Force logic argument limit.
     */
    public void applyVoltage(double volts) {
        motor.runVoltage(volts);
    }
    
    /**
     * Adjusts modeled motor output constraints.
     *
     * @param duty Limit margin calculation payload.
     */
    public void applyDutyCycle(double duty) {
        motor.runDuty(duty);
    }

    /**
     * Returns computed velocity matrix data.
     *
     * @return Simulated state data reading format.
     */
    public double getRPM() {
        return (flywheelSim.getAngularVelocity() / (2.0 * Math.PI)) / 60.0;
    }

    /**
     * Tests modeled value logic.
     *
     * @param rpm       Expected configuration threshold.
     * @param tolerance Window calculation payload constraint.
     * @return Response check payload.
     */
    public Trigger atRPM(double rpm, double tolerance) {
        return new Trigger(() -> (Math.abs(getRPM() - rpm) <= tolerance));
    }
}