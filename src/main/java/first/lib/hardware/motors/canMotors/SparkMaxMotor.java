package first.lib.hardware.motors.canMotors;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.ControlType;
import com.revrobotics.spark.config.FeedForwardConfig;
import com.revrobotics.spark.config.SparkMaxConfig;

import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.sim.SparkRelativeEncoderSim;

/**
 * Implementation of a CANMotor using a REV Robotics Spark Max.
 */
public class SparkMaxMotor extends CANMotor {

    private final SparkMax motor;
    private final SparkMaxConfig config;
    private final RelativeEncoder encoder;
    private final SparkRelativeEncoderSim sparkRelativeEncoderSim;
    private final CANMotorConfig cfg;

    /**
     * Constructs and configures a SparkMaxMotor.
     * @param mConfig The configuration parameters for this motor.
     */
    public SparkMaxMotor(CANMotorConfig mConfig) {
        super(mConfig);

        this.cfg = mConfig;

        this.motor = new SparkMax(cfg.canPort, cfg.id, MotorType.kBrushless);

        this.config = new SparkMaxConfig();

        this.config.closedLoop.pid(cfg.kP, cfg.kI, cfg.kD);
        
        if (cfg.gravityTypeElevator) {
            config.closedLoop.feedForward.apply(new FeedForwardConfig().svag(cfg.kS, cfg.kV, cfg.kA, cfg.kG));
            config.closedLoop.allowedClosedLoopError(cfg.tolerance, ClosedLoopSlot.kSlot0);
        } else {
            config.closedLoop.feedForward.apply(new FeedForwardConfig().svacr(cfg.kS, cfg.kV, cfg.kA, cfg.kG, cfg.armCosRatio));
            config.closedLoop.allowedClosedLoopError(cfg.tolerance, ClosedLoopSlot.kSlot0);
        }

        encoder = motor.getEncoder();

        config.smartCurrentLimit(cfg.currentLimit);
        config.inverted(cfg.motorInvert);

        config.idleMode(cfg.brakeOn ? IdleMode.kBrake : IdleMode.kCoast);

        if (cfg.leaderID != -1) {
            config.follow(cfg.leaderID, !cfg.followerAlign);
        }

        motor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        sparkRelativeEncoderSim = new SparkRelativeEncoderSim(motor);
    }

    /**
     * Sets the voltage applied to the motor.
     * @param volts Target voltage.
     */
    public void runVoltage(double volts) {
        motor.setVoltage(volts);
    }

    /**
     * Sets the motor output duty cycle.
     * @param percent Duty cycle percentage [-1.0, 1.0].
     */
    public void runDuty(double percent) {
        motor.setThrottle(percent);
    }

    /**
     * Commands the motor to a specific rotation point using position control.
     * @param rotations Target position in rotations.
     */
    public void goToPoint(double rotations) {
        motor.getClosedLoopController().setSetpoint(rotations, ControlType.kPosition);
    }

    /**
     * Commands the motor to run at a target RPM.
     * @param rpm Target velocity in RPM.
     */
    public void runRPM(double rpm) {
        motor.getClosedLoopController().setSetpoint(rpm, ControlType.kVelocity);
    }

    /**
     * Retrieves the current velocity in RPM.
     * @return Velocity in RPM.
     */
    public double getRPM() {
        return encoder.getVelocity().get() * 60;
    }

    /**
     * Retrieves the current velocity in RPS.
     * @return Velocity in RPS.
     */
    public double getVelocity() {
        return encoder.getVelocity().get();
    }

    /**
     * Retrieves the current position in rotations.
     * @return Position in rotations.
     */
    public double getRotations() {
        return encoder.getPosition().get();
    }

    /**
     * Retrieves the current voltage applied to the motor.
     * @return Applied voltage.
     */
    public double getAppliedVoltage() {
        return motor.getAppliedOutput().get() * motor.getBusVoltage().get();
    }

    /**
     * Retrieves the current output current of the motor.
     * @return Current in Amps.
     */
    public double getCurrent() {
        return motor.getOutputCurrent().get();
    }

    /**
     * Stops all motor movement immediately.
     */
    public void stop() {
        motor.stopMotor();
    }

    /**
     * Overrides the current encoder position.
     * @param pos New position in rotations.
     */
    public void setRotorPosition(double pos) {
        encoder.setPosition(pos);
    }

    /**
     * Sets the position for the simulated encoder.
     * @param rotations Target simulated position in rotations.
     */
    public void setSimEncoderPosition(double rotations) {
        sparkRelativeEncoderSim.setPosition(rotations);
    }

    /**
     * Sets the velocity for the simulated encoder.
     * @param rps Target simulated velocity in RPS.
     */
    public void setSimEncoderVelocity(double rps) {
        sparkRelativeEncoderSim.setVelocity(rps);
    }
}