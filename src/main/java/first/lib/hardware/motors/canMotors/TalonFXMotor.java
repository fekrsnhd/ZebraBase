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

/**
 * Implementation of a CANMotor using a CTRE Talon FX.
 */
public class TalonFXMotor extends CANMotor {

    private final TalonFX motor;
    private final TalonFXConfiguration config;
    private final TalonFXSimState motorSimState;
    private double tolerance;

    /**
     * Constructs and configures a TalonFXMotor.
     * @param cfg The configuration parameters for this motor.
     */
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

    /**
     * Initializes the motor configuration.
     */
    public void init() {
        
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
        motor.setControl(new DutyCycleOut(percent));
    }

    /**
     * Commands the motor to a specific position (rotations) if it is outside the set tolerance.
     * @param rotations Target position in rotations.
     */
    public void goToPoint(double rotations) {
        new PositionVoltage(rotations).withSlot(0);
    }

    /**
     * Commands the motor to a specific velocity in RPM if it is outside the set tolerance.
     * @param rpm Target velocity in RPM.
     */
    public void runRPM(double rpm) {
        new VelocityVoltage(rpm / 60.0).withSlot(0);
    }

    /**
     * Retrieves the current velocity in RPM.
     * @return Velocity in RPM.
     */
    public double getRPM() {
        return motor.getVelocity().getValueAsDouble() * 60.0;
    }

    /**
     * Retrieves the current velocity in RPS.
     * @return Velocity in RPS.
     */
    public double getVelocity() {
        return motor.getVelocity().getValueAsDouble();
    }

    /**
     * Retrieves the current position in rotations.
     * @return Position in rotations.
     */
    public double getRotations() {
        return motor.getPosition().getValueAsDouble();
    }

    /**
     * Retrieves the current voltage applied to the motor, supporting both live and simulated modes.
     * @return Applied voltage.
     */
    public double getAppliedVoltage() {
        if (globalConstants.currentMode == globalConstants.Mode.SIM) {
            return motorSimState.getMotorVoltage();
        }
        return motor.getMotorVoltage().getValueAsDouble();
    }

    /**
     * Retrieves the current output current of the motor.
     * @return Current in Amps.
     */
    public double getCurrent() {
        return motor.getSupplyCurrent().getValueAsDouble();
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
        motor.setPosition(pos);
    }

    /**
     * Sets the position for the simulated encoder.
     * @param rotations Target simulated position in rotations.
     */
    public void setSimEncoderPosition(double rotations) {
        motorSimState.setRawRotorPosition(rotations);
    }

    /**
     * Sets the velocity for the simulated encoder.
     * @param rps Target simulated velocity in RPS.
     */
    public void setSimEncoderVelocity(double rps) {
        motorSimState.setRotorVelocity(rps);
    }
}