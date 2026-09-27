package first.lib.hardware.motors.canMotors;

import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.ControlType;
import com.revrobotics.spark.config.FeedForwardConfig;
import com.revrobotics.spark.config.SparkFlexConfig;

import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.sim.SparkRelativeEncoderSim;

public class SparkFlexMotor extends CANMotor {

    private final SparkFlex motor;
    private final SparkFlexConfig config;
    private final RelativeEncoder encoder;
    private final SparkRelativeEncoderSim sparkRelativeEncoderSim;
    private final CANMotorConfig cfg;

    public SparkFlexMotor(CANMotorConfig mConfig) {
        super(mConfig);

        this.cfg = mConfig;

        this.motor = new SparkFlex(cfg.canPort, cfg.id, MotorType.kBrushless);

        this.config = new SparkFlexConfig();

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

    public void runVoltage(double volts) {
        motor.setVoltage(volts);
    }

    public void runDuty(double percent) {
        motor.setThrottle(percent);
    }

    public void goToPoint(double rotations) {
        motor.getClosedLoopController().setSetpoint(rotations, ControlType.kPosition);
    }

    public void runRPM(double rpm) {
        motor.getClosedLoopController().setSetpoint(rpm, ControlType.kVelocity);
    }

    public double getRPM() {
        return encoder.getVelocity().get() * 60;
    }

    public double getVelocity() {
        return encoder.getVelocity().get();
    }

    public double getRotations() {
        return encoder.getPosition().get();
    }

    public double getAppliedVoltage() {
        return motor.getAppliedOutput().get() * motor.getBusVoltage().get();
    }

    public double getCurrent() {
        return motor.getOutputCurrent().get();
    }

    public void stop() {
        motor.stopMotor();
    }

    public void setRotorPosition(double pos) {
        encoder.setPosition(pos);
    }

    public void setSimEncoderPosition(double rotations) {
        sparkRelativeEncoderSim.setPosition(rotations);
    }

    public void setSimEncoderVelocity(double rps) {
        sparkRelativeEncoderSim.setVelocity(rps);
    }

}