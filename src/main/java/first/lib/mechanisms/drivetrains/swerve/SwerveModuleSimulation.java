package first.lib.mechanisms.drivetrains.swerve;

import org.wpilib.framework.RobotBase;
import org.wpilib.math.geometry.Rotation2d;
import org.wpilib.math.kinematics.SwerveModuleVelocity;
import org.wpilib.math.system.Models;
import org.wpilib.simulation.FlywheelSim;

import first.lib.hardware.motors.canMotors.CANMotor;

public class SwerveModuleSimulation extends SwerveModuleIO {

    private final CANMotor driveMotor;
    private final CANMotor steerMotor;
    private final FlywheelSim driveSim;
    private double simulatedDrivePositionMeters = 0.0;
    private double simulatedSteerRotations = 0.0;

    public SwerveModuleSimulation(SwerveModuleConfig config) {
        super(config);
        this.driveMotor = config.driveMotor;
        this.steerMotor = config.steerMotor;
        this.driveSim = new FlywheelSim(
            Models.flywheelFromPhysicalConstants(
                driveMotor.getConfig().getMotorType(), 
                0.5 * config.wheelWeightKg * Math.pow(config.wheelRadiusMeters, 2), 
                config.driveGearRatio),
            driveMotor.getConfig().getMotorType(),
            0, 0.1
        );
    }

    @Override
    public void periodic() {
        if (RobotBase.isSimulation()) {
            driveSim.setInput(driveMotor.getAppliedVoltage());
            driveSim.update(0.02);

            double driveVelocityRPS = driveSim.getAngularVelocity() / (2.0 * Math.PI);
            simulatedDrivePositionMeters += driveVelocityRPS * 0.02 * (2.0 * Math.PI * config.wheelRadiusMeters) / config.driveGearRatio;

            driveMotor.setSimEncoderPosition(simulatedDrivePositionMeters / (2.0 * Math.PI * config.wheelRadiusMeters) * config.driveGearRatio);
            driveMotor.setSimEncoderVelocity(driveVelocityRPS);

            simulatedSteerRotations += (steerMotor.getAppliedVoltage() * 0.1); 
            steerMotor.setSimEncoderPosition(simulatedSteerRotations);
        }
    }

    @Override
    public void setDesiredVelocity(SwerveModuleVelocity velocity) {
        double driveRPM = (velocity.velocity / (2.0 * Math.PI * config.wheelRadiusMeters)) * config.driveGearRatio * 60.0;
        driveMotor.runRPM(driveRPM);
        steerMotor.goToPoint(velocity.angle.getRotations() * config.steerGearRatio);
    }

    @Override
    public void stop() {
        driveMotor.stop();
        steerMotor.stop();
    }

    @Override
    public double getDrivePositionMeters() {
        return simulatedDrivePositionMeters;
    }

    @Override
    public double getDriveVelocityMetersPerSecond() {
        return driveSim.getAngularVelocity() * config.wheelRadiusMeters / config.driveGearRatio;
    }

    @Override
    public Rotation2d getSteerAngle() {
        return Rotation2d.fromRotations(simulatedSteerRotations / config.steerGearRatio);
    }
}