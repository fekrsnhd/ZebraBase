package first.lib.mechanisms.singleJointedArm;

import org.wpilib.command3.Trigger;
import org.wpilib.framework.RobotBase;
import org.wpilib.simulation.SingleJointedArmSim;
import first.lib.hardware.motors.canMotors.CANMotor;

public class SingleJointedArmSimulation extends SingleJointedArmIO {

    private final CANMotor motor;
    private final SingleJointedArmSim armSim;
    private double appliedVoltage = 0;

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
        return armSim.getAngle();
    }

    public double getRotations() {
        return radiansToRotations(armSim.getAngle());
    }
    
    public double getVelocityRadsPerSec() {
        return armSim.getVelocity(); 
    }

    public Trigger atAngle(double angleRads, double tolerance) {
        return new Trigger(() -> (Math.abs(getAngleRads() - angleRads) <= tolerance));
    }
}