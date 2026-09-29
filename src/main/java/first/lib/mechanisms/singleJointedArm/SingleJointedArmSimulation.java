package first.lib.mechanisms.singleJointedArm;

import org.wpilib.command3.Trigger;
import org.wpilib.framework.RobotBase;
import org.wpilib.simulation.SingleJointedArmSim;
import first.lib.hardware.motors.canMotors.CANMotor;

/**
 * Simulated implementation of the SingleJointedArmIO.
 * Utilizes WPILib's SingleJointedArmSim to compute physics steps and provide simulated feedback.
 */
public class SingleJointedArmSimulation extends SingleJointedArmIO {

    private final CANMotor motor;
    private final SingleJointedArmSim armSim;
    private double appliedVoltage = 0;

    /**
     * Constructs a SingleJointedArmSimulation.
     * Initializes the underlying WPILib physics simulator.
     *
     * @param cfg The configuration representing the physical arm properties.
     */
    public SingleJointedArmSimulation(SingleJointedArmConfig cfg) {
        super(cfg);
        this.motor = config.motor;
                
        this.armSim = new SingleJointedArmSim(
            cfg.motorType,
            cfg.gearing,
            SingleJointedArmSim.estimateMOI(cfg.lengthMeters, cfg.massKg),
            cfg.lengthMeters,
            cfg.minAngleRads,
            cfg.maxAngleRads,
            cfg.simulateGravity,
            cfg.startingAngleRads,
            cfg.measurementStdDevs
        );
    }

    /**
     * Periodically updates the simulation physics step and feeds updated position/velocity
     * to the simulated motor encoder.
     */
    @Override
    public void periodic() {
        if (RobotBase.isSimulation()) {
            appliedVoltage = motor.getAppliedVoltage();
            armSim.setInput(appliedVoltage);
            armSim.update(0.02);

            double simRotations = radiansToRotations(armSim.getAngle());
            double simVelocityRps = radiansToRotations(armSim.getVelocity());
            
            motor.setSimEncoderPosition(simRotations);
            motor.setSimEncoderVelocity(simVelocityRps);
        }    
    }

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
        return armSim.getAngle();
    }

    @Override
    public double getRotations() {
        return radiansToRotations(armSim.getAngle());
    }
    
    @Override
    public double getVelocityRadsPerSec() {
        return armSim.getVelocity(); 
    }

    @Override
    public Trigger atAngle(double angleRads, double tolerance) {
        return new Trigger(() -> (Math.abs(getAngleRads() - angleRads) <= tolerance));
    }
}