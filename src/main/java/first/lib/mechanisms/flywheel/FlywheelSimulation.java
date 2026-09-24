package first.lib.mechanisms.flywheel;

import org.wpilib.command3.Trigger;
import org.wpilib.framework.RobotBase;
import org.wpilib.simulation.FlywheelSim;
import org.wpilib.math.system.Models;
import first.lib.hardware.motors.canMotors.CANMotor;

public class FlywheelSimulation extends FlywheelIO {

    private final CANMotor motor;
    private final FlywheelSim flywheelSim;
    private double appliedVoltage = 0;

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

    public void stop() {
        motor.stop();
    }

    public void runRPM(double rpm) {
        motor.runRPM(rpm);
    }

    public void applyVoltage(double volts) {
        motor.runVoltage(volts);
    }
    
    public void applyDutyCycle(double duty) {
        motor.runDuty(duty);
    }

    public double getRPM() {
        return (flywheelSim.getAngularVelocity() / (2.0 * Math.PI)) / 60.0;
    }

    public Trigger atRPM(double rpm, double tolerance) {
        return new Trigger(() -> (Math.abs(getRPM() - rpm) <= tolerance));
    }
}