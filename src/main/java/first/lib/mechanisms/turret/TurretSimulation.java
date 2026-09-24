package first.lib.mechanisms.turret;

import org.wpilib.command3.Trigger;
import org.wpilib.framework.RobotBase;
import org.wpilib.simulation.SingleJointedArmSim;
import first.lib.hardware.motors.canMotors.CANMotor;

public class TurretSimulation extends TurretIO {

    private final CANMotor motor;
    private final SingleJointedArmSim turretSim;
    private double appliedVoltage = 0;

    public TurretSimulation(TurretConfig cfg) {
        super(cfg);
        this.motor = config.motor;
                
        // SingleJointedArmSim works well for turrets if gravity is false and length is arbitrary.
        this.turretSim = new SingleJointedArmSim(
            cfg.motorType,
            cfg.gearing,
            cfg.moiKgMetersSquared,
            1.0, // Arbitrary length for MOI simulation
            cfg.minAngleRads,
            cfg.maxAngleRads,
            false, // Turrets do not simulate gravity
            cfg.startingAngleRads,
            cfg.measurementStdDevs
        );
    }

    public void periodic() {
        if (RobotBase.isSimulation()) {
            appliedVoltage = motor.getAppliedVoltage();
            turretSim.setInput(appliedVoltage);
            turretSim.update(0.02);

            double simRotations = radiansToRotations(turretSim.getAngle());
            double simVelocityRps = radiansToRotations(turretSim.getVelocity());
            
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
        return turretSim.getAngle();
    }

    public double getRotations() {
        return radiansToRotations(turretSim.getAngle());
    }
    
    public double getVelocityRadsPerSec() {
        return turretSim.getVelocity(); 
    }

    public Trigger atAngle(double angleRads, double tolerance) {
        return new Trigger(() -> (Math.abs(getAngleRads() - angleRads) <= tolerance));
    }
}