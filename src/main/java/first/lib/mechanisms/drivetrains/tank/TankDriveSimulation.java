package first.lib.mechanisms.drivetrains.tank;

import org.wpilib.framework.RobotBase;
import org.wpilib.math.system.Models;
import org.wpilib.simulation.DifferentialDrivetrainSim;

import first.lib.hardware.motors.canMotors.CANMotor;

public class TankDriveSimulation extends TankDriveIO {
    private final CANMotor left;
    private final CANMotor right;
    private final DifferentialDrivetrainSim sim;
    private double simLeftPos = 0.0;
    private double simRightPos = 0.0;

    public TankDriveSimulation(TankDriveConfig config) {
        super(config);
        this.left = config.leftLeader;
        this.right = config.rightLeader;
        this.sim = new DifferentialDrivetrainSim(
            Models.differentialDriveFromPhysicalConstants(
                config.motorType, 
                config.robotMassKg,
                config.wheelRadiusMeters, 
                config.trackWidthMeters / 2.0, 
                (1.0 / 6.0) * config.robotMassKg * config.trackWidthMeters,
                config.gearRatio
            ),
            config.motorType, 
            config.gearRatio, 
            config.trackWidthMeters, 
            config.wheelRadiusMeters, 
            null
        );
    }

    @Override
    public void periodic() {
        if (RobotBase.isSimulation()) {
            sim.setInputs(left.getAppliedVoltage(), right.getAppliedVoltage());
            sim.update(0.02);

            simLeftPos = sim.getLeftPosition();
            simRightPos = sim.getRightPosition();

            left.setSimEncoderPosition(simLeftPos / (2.0 * Math.PI * config.wheelRadiusMeters) * config.gearRatio);
            left.setSimEncoderVelocity(sim.getLeftVelocity() / (2.0 * Math.PI * config.wheelRadiusMeters) * config.gearRatio);
            
            right.setSimEncoderPosition(simRightPos / (2.0 * Math.PI * config.wheelRadiusMeters) * config.gearRatio);
            right.setSimEncoderVelocity(sim.getRightVelocity() / (2.0 * Math.PI * config.wheelRadiusMeters) * config.gearRatio);
        }
    }

    @Override
    public void setVoltage(double leftVolts, double rightVolts) {
        left.runVoltage(leftVolts);
        right.runVoltage(rightVolts);
    }

    @Override
    public void stop() {
        left.stop();
        right.stop();
    }

    @Override public double getLeftPositionMeters() { return simLeftPos; }
    @Override public double getLeftVelocityMetersPerSecond() { return sim.getLeftVelocity(); }
    @Override public double getRightPositionMeters() { return simRightPos; }
    @Override public double getRightVelocityMetersPerSecond() { return sim.getRightVelocity(); }
}