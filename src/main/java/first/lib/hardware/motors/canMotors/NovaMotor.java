package first.lib.hardware.motors.canMotors;

import com.thrifty.nova.Nova;
import com.thrifty.nova.NovaConfig;
import com.thrifty.core.MotorControl;
import com.thrifty.core.Motor.Direction;
import com.thrifty.core.Motor.MotorType;
import com.thrifty.core.Motor.Slot;

import org.wpilib.math.system.DCMotor;

import com.thrifty.core.Motor;


/**
 * Implementation of a CANMotor using the Thrifty Nova motor controller (2027 ThriftyLib).
 */
public class NovaMotor extends CANMotor {

    private final Nova motor;

    /**
     * Constructs and configures a NovaMotor using ThriftyLib 2027.
     * @param cfg The configuration parameters for this motor.
     */
    public NovaMotor(CANMotorConfig cfg) {
        super(cfg);
        MotorType motorType = MotorType.PULSAR;
        if (cfg.motorType.equals(DCMotor.getCIM(1))) {
            motorType = MotorType.BRUSHED;
        } else if (cfg.motorType.equals(DCMotor.getNEO(1))) {
            motorType = MotorType.NEO;
        } else if (cfg.motorType.equals(DCMotor.getMinion(1))) {
            motorType = MotorType.MINION;
        } else if (cfg.motorType.equals(DCMotor.getKrakenX44(1))) {
            motorType = MotorType.BRUSHED;
        }
        // Initialize Nova with CAN ID and bus port
        this.motor = new Nova(cfg.canPort.value, cfg.id, motorType);

        // Configure the Nova using the batched configuration pattern
        motor.configure(
            NovaConfig.brakeMode(cfg.brakeOn),
            NovaConfig.direction(cfg.motorInvert ? Direction.CLOCKWISE : Direction.COUNTER_CLOCKWISE),
            NovaConfig.statorCurrent(cfg.currentLimit > 0 ? cfg.currentLimit : 40.0),
            
            // PID and Feed-forward configuration for SLOT0
            NovaConfig.pid0(cfg.kP, cfg.kI, cfg.kD, 
                cfg.kV / 12.0, 1000.0, cfg.tolerance),
            NovaConfig.useSlot(Slot.SLOT0)
        );

        // Follower setup if leader ID is specified
        if (cfg.leaderID != -1) {
            motor.configure(NovaConfig.follow(cfg.leaderID));
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
        motor.control(MotorControl.voltage(volts));
    }

    /**
     * Sets the motor output duty cycle.
     * @param percent Duty cycle percentage [-1.0, 1.0].
     */
    public void runDuty(double percent) {
        motor.control(MotorControl.percent(percent));
    }

    /**
     * Commands the motor to a specific position (rotations).
     * @param rotations Target position in rotations.
     */
    public void goToPoint(double rotations) {
        motor.control(
            MotorControl.position(rotations)
                        .withSlot(Motor.Slot.SLOT0)
        );
    }

    /**
     * Commands the motor to a specific velocity in RPM.
     * @param rpm Target velocity in RPM.
     */
    public void runRPM(double rpm) {
        motor.control(
            MotorControl.velocity(rpm / 60.0)
                        .withSlot(Motor.Slot.SLOT0)
        );
    }

    /**
     * Retrieves the current velocity in RPM.
     * @return Velocity in RPM.
     */
    public double getRPM() {
        return getVelocity() * 60.0;
    }

    /**
     * Retrieves the current velocity in RPS.
     * @return Velocity in RPS.
     */
    public double getVelocity() {
        return motor.status().getVelocity();
    }

    /**
     * Retrieves the current position in rotations.
     * @return Position in rotations.
     */
    public double getRotations() {
        return motor.status().getPosition();
    }

    /**
     * Retrieves the current voltage applied to the motor.
     * @return Applied voltage.
     */
    public double getAppliedVoltage() {
        return motor.status().getVoltage();
    }

    /**
     * Retrieves the current output current of the motor.
     * @return Current in Amps.
     */
    public double getCurrent() {
        return motor.status().getCurrentStator();
    }

    /**
     * Stops all motor movement immediately.
     */
    public void stop() {
        motor.stop();
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
        // Thrifty Nova relies on WPILib simulation hooks / NetworkTables
    }

    /**
     * Sets the velocity for the simulated encoder.
     * @param rps Target simulated velocity in RPS.
     */
    public void setSimEncoderVelocity(double rps) {
        // Thrifty Nova relies on WPILib simulation hooks / NetworkTables
    }
}