package first.lib.mechanisms.singleJointedArm;

import org.littletonrobotics.junction.Logger;
import org.wpilib.command3.Command;
import org.wpilib.command3.Mechanism;
import org.wpilib.command3.Trigger;
import first.robot.globalConstants;

/**
 * WPILib Mechanism representing a single-jointed arm.
 * Provides command factories and structural organization for the arm subsystem.
 */
public class SingleJointedArmMechanism implements Mechanism {

    private final SingleJointedArmIO io;
    private final String name;
    private final SingleJointedArmIOInputsAutoLogged inputs = new SingleJointedArmIOInputsAutoLogged();

    /**
     * Constructs a new SingleJointedArmMechanism.
     * Automatically selects the real or simulated IO implementation based on current global mode.
     *
     * @param mechName The name of the mechanism (used for logging and commands).
     * @param config   The configuration representing the physical arm properties.
     */
    public SingleJointedArmMechanism(String mechName, SingleJointedArmConfig config) {
        this.name = mechName;
        boolean sim = globalConstants.currentMode.equals(globalConstants.Mode.SIM);
        this.io = sim ? new SingleJointedArmSimulation(config) : new SingleJointedArmReal(config);
    }

    /** @return A command that continuously stops the arm. */
    public Command stop() {
        return runRepeatedly(() -> io.stop()).named(name + ".Stop");
    }

    /** * Creates a command to drive the arm to a target angle.
     * * @param angleRads The target angle in radians.
     * @return The command.
     */
    public Command goToAngle(double angleRads) {
        return runRepeatedly(() -> io.goToAngle(angleRads)).named(name + ".goToAngle");
    }

    /** * Creates a command to drive the arm to a target motor rotation.
     * * @param rotations The target point in motor rotations.
     * @return The command.
     */
    public Command goToPoint(double rotations) {
        return runRepeatedly(() -> io.goToPoint(rotations)).named(name + ".goToPoint");
    }

    /** * Creates a command to apply a constant voltage to the arm.
     * * @param volts The voltage to apply.
     * @return The command.
     */
    public Command applyVoltage(double volts) {
        return runRepeatedly(() -> io.applyVoltage(volts)).named(name + ".applyVoltage");
    }

    /** * Creates a command to apply a constant duty cycle to the arm.
     * * @param duty The duty cycle percentage to apply.
     * @return The command.
     */
    public Command applyDutyCycle(double duty) {
        return runRepeatedly(() -> io.applyDutyCycle(duty)).named(name + ".applyDutyCycle");
    }

    @Override 
    public Command idle() { return stop(); }

    /** @return The current angle of the arm in radians. */
    public double getAngleRads() { return io.getAngleRads(); }

    /** @return The current motor position in rotations. */
    public double getRotations() { return io.getRotations(); }

    /** @return The current velocity in radians per second. */
    public double getVelocityRadsPerSec() { return io.getVelocityRadsPerSec(); }

    /**
     * Checks if the arm is currently at a specified angle within a tolerance.
     *
     * @param angleRads The target angle.
     * @param toleranceRads The acceptable tolerance.
     * @return A trigger evaluating true if within tolerance.
     */
    public Trigger atAngle(double angleRads, double toleranceRads) {
        return io.atAngle(angleRads, toleranceRads);
    }

    /**
     * Checks if the arm is currently at a specified angle within a default tolerance of 0.05 radians.
     *
     * @param angleRads The target angle.
     * @return A trigger evaluating true if within tolerance.
     */
    public Trigger atAngle(double angleRads) {
        return atAngle(angleRads, 0.05); // Default 0.05 rad tolerance
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * Periodic method that should be called every loop cycle.
     * Updates inputs, logs data, and handles underlying IO periodic updates.
     */
    public void periodic() {
        io.periodic();
        io.updateInputs(inputs);
        Logger.processInputs(name, inputs);
    }
}