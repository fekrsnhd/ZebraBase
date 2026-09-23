package first.lib.hardware.motors.canMotors;

import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.MotorAlignmentValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.sim.TalonFXSimState;

import first.robot.globalConstants;

public class TalonFXMotor extends CANMotor {

    private final TalonFX motor;
    private final TalonFXConfiguration config;
    private final TalonFXSimState motorSimState;
    private double tolerance;


    public TalonFXMotor(CANMotorConfig cfg) {
        super(cfg);
        this.motor = new TalonFX(cfg.id, new CANBus(cfg.canPort));
        this.motorSimState = motor.getSimState();
        this.config = new TalonFXConfiguration();

        config.withSlot0(
            new Slot0Configs()
                .withKP(cfg.kP)
                .withKI(cfg.kI)
                .withKD(cfg.kD)
                .withKS(cfg.kS)
                .withKV(cfg.kV)
                .withKA(cfg.kA)
                .withKG(cfg.kG)
                .withGravityType(
                    cfg.gravityTypeElevator
                        ? GravityTypeValue.Elevator_Static
                        : GravityTypeValue.Arm_Cosine
                )
        );

        this.tolerance = cfg.tolerance;

        config.CurrentLimits.SupplyCurrentLimit = cfg.currentLimit;
        config.CurrentLimits.SupplyCurrentLimitEnable = cfg.currentLimit > 0;
        config.MotorOutput.NeutralMode = 
                cfg.brakeOn
                ? NeutralModeValue.Brake
                : NeutralModeValue.Coast;

        config.MotorOutput.Inverted =
                cfg.motorInvert
                ? InvertedValue.Clockwise_Positive
                : InvertedValue.CounterClockwise_Positive;

        motor.getConfigurator().apply(config);

        if (cfg.leaderID != -1) {
            MotorAlignmentValue alignmentValue = 
                    cfg.followerAlign 
                    ? MotorAlignmentValue.Opposed 
                    : MotorAlignmentValue.Aligned;
            motor.setControl(new Follower(cfg.leaderID, alignmentValue));
        }
    }

    public void init() {
        
    }

    public void runVoltage(double volts) {
        motor.setVoltage(volts);
    }

    public void runDuty(double percent) {
        motor.setControl(new DutyCycleOut(percent));
    }

    public void goToPoint(double rotations) {
        if (!(Math.abs(getRotations() - rotations) <= tolerance)) {
            motor.setControl(
                new PositionVoltage(rotations).withSlot(0)
            );
        }
    }

    public void runRPM(double rpm) {
        if (!(Math.abs(getRotations() - getRPM()) <= tolerance)) {
            motor.setControl(
                new VelocityVoltage(rpm / 60.0).withSlot(0)
            );
        }
    }

    public double getRPM() {
        return motor.getVelocity().getValueAsDouble() * 60.0;
    }

    public double getVelocity() {
        return motor.getVelocity().getValueAsDouble();
    }

    public double getRotations() {
        return motor.getPosition().getValueAsDouble();
    }

    public double getAppliedVoltage() {
        if (globalConstants.currentMode == globalConstants.Mode.SIM) {
            return motorSimState.getMotorVoltage();
        }
        return motor.getMotorVoltage().getValueAsDouble();
    }

    public double getCurrent() {
        return motor.getSupplyCurrent().getValueAsDouble();
    }

    public void stop() {
        motor.stopMotor();
    }

    public void setRotorPosition(double pos) {
        motor.setPosition(pos);
    }

    public void setSimEncoderPosition(double rotations) {
        motorSimState.setRawRotorPosition(rotations);
    }

    public void setSimEncoderVelocity(double rps) {
        motorSimState.setRotorVelocity(rps);
    }

}