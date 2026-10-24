package first.lib.mechanisms.drivetrains.mecanum;

import org.wpilib.framework.RobotBase;
import org.wpilib.simulation.FlywheelSim;
import org.wpilib.math.system.Models;
import org.wpilib.math.kinematics.MecanumDriveWheelPositions;
import org.wpilib.math.kinematics.MecanumDriveWheelVelocities;
import first.lib.hardware.motors.canMotors.CANMotor;

public class MecanumDriveSimulation extends MecanumDriveIO {

    private final CANMotor frontLeft, frontRight, rearLeft, rearRight;
    private final FlywheelSim flSim, frSim, rlSim, rrSim;

    private double flPosMeters = 0.0, frPosMeters = 0.0, rlPosMeters = 0.0, rrPosMeters = 0.0;

    public MecanumDriveSimulation(MecanumDriveConfig config) {
        super(config);
        this.frontLeft = config.frontLeft;
        this.frontRight = config.frontRight;
        this.rearLeft = config.rearLeft;
        this.rearRight = config.rearRight;

        var motorType = frontLeft.getConfig().getMotorType();
        double inertia = 0.05; // Estimated moment of inertia

        this.flSim = new FlywheelSim(Models.flywheelFromPhysicalConstants(motorType, inertia, config.gearRatio), motorType, 0, 0.1);
        this.frSim = new FlywheelSim(Models.flywheelFromPhysicalConstants(motorType, inertia, config.gearRatio), motorType, 0, 0.1);
        this.rlSim = new FlywheelSim(Models.flywheelFromPhysicalConstants(motorType, inertia, config.gearRatio), motorType, 0, 0.1);
        this.rrSim = new FlywheelSim(Models.flywheelFromPhysicalConstants(motorType, inertia, config.gearRatio), motorType, 0, 0.1);
    }

    @Override
    public void periodic() {
        if (RobotBase.isSimulation()) {
            // Update individual wheel simulations with applied voltages
            flSim.setInput(frontLeft.getAppliedVoltage());
            frSim.setInput(frontRight.getAppliedVoltage());
            rlSim.setInput(rearLeft.getAppliedVoltage());
            rrSim.setInput(rearRight.getAppliedVoltage());

            flSim.update(0.02);
            frSim.update(0.02);
            rlSim.update(0.02);
            rrSim.update(0.02);

            // Compute velocities (RPS) and positions (meters)
            double flRPS = flSim.getAngularVelocity() / (2.0 * Math.PI);
            double frRPS = frSim.getAngularVelocity() / (2.0 * Math.PI);
            double rlRPS = rlSim.getAngularVelocity() / (2.0 * Math.PI);
            double rrRPS = rrSim.getAngularVelocity() / (2.0 * Math.PI);

            flPosMeters += flRPS * 0.02 * (2.0 * Math.PI * config.wheelRadiusMeters) / config.gearRatio;
            frPosMeters += frRPS * 0.02 * (2.0 * Math.PI * config.wheelRadiusMeters) / config.gearRatio;
            rlPosMeters += rlRPS * 0.02 * (2.0 * Math.PI * config.wheelRadiusMeters) / config.gearRatio;
            rrPosMeters += rrRPS * 0.02 * (2.0 * Math.PI * config.wheelRadiusMeters) / config.gearRatio;

            // Push simulated encoder states back to motors
            frontLeft.setSimEncoderPosition(metersToRotations(flPosMeters));
            frontLeft.setSimEncoderVelocity(flRPS);
            frontRight.setSimEncoderPosition(metersToRotations(frPosMeters));
            frontRight.setSimEncoderVelocity(frRPS);
            rearLeft.setSimEncoderPosition(metersToRotations(rlPosMeters));
            rearLeft.setSimEncoderVelocity(rlRPS);
            rearRight.setSimEncoderPosition(metersToRotations(rrPosMeters));
            rearRight.setSimEncoderVelocity(rrRPS);
        }
    }

    @Override
    public void setDesiredWheelVelocities(MecanumDriveWheelVelocities speeds) {
        frontLeft.runRPM(metersPerSecondToRPM(speeds.frontLeft));
        frontRight.runRPM(metersPerSecondToRPM(speeds.frontRight));
        rearLeft.runRPM(metersPerSecondToRPM(speeds.rearLeft));
        rearRight.runRPM(metersPerSecondToRPM(speeds.rearRight));
    }

    @Override
    public void stop() {
        frontLeft.stop();
        frontRight.stop();
        rearLeft.stop();
        rearRight.stop();
    }

    @Override
    public MecanumDriveWheelPositions getWheelPositions() {
        return new MecanumDriveWheelPositions(flPosMeters, frPosMeters, rlPosMeters, rrPosMeters);
    }

    @Override
    public MecanumDriveWheelVelocities getWheelVelocities() {
        return new MecanumDriveWheelVelocities(
            flSim.getAngularVelocity() * config.wheelRadiusMeters / config.gearRatio,
            frSim.getAngularVelocity() * config.wheelRadiusMeters / config.gearRatio,
            rlSim.getAngularVelocity() * config.wheelRadiusMeters / config.gearRatio,
            rrSim.getAngularVelocity() * config.wheelRadiusMeters / config.gearRatio
        );
    }

    private double metersPerSecondToRPM(double mps) {
        return (mps / (2.0 * Math.PI * config.wheelRadiusMeters)) * config.gearRatio * 60.0;
    }

    private double metersToRotations(double meters) {
        return meters * config.gearRatio / (2.0 * Math.PI * config.wheelRadiusMeters);
    }
}