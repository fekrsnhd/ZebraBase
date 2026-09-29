package first.lib.mechanisms.turret;

import org.littletonrobotics.junction.Logger;
import org.wpilib.command3.Command;
import org.wpilib.command3.Mechanism;
import org.wpilib.command3.Trigger;

import first.robot.globalConstants;

/**
 * WPILib Mechanism representing a Turret.
 * Provides command factories and structural organization.
 */
public class TurretMechanism implements Mechanism {

    private final TurretIO io;
    private final String name;
    private final TurretIOInputsAutoLogged inputs = new TurretIOInputsAutoLogged();

    /**
     * Constructs a new TurretMechanism.
     * Automatically selects the real or simulated IO implementation based on current global mode.
     *
     * @param mechName The name of the mechanism.
     * @param config   The configuration representing physical properties.
     */
    public TurretMechanism(String mechName, TurretConfig config) {
        this.name = mechName;
        boolean sim = globalConstants.currentMode.equals(globalConstants.Mode.SIM);
        this.io = sim ? new TurretSimulation(config) : new TurretReal(config);
    }

    /** @return A command that continuously stops the turret. */
    public Command stop() {
        return runRepeatedly(() -> io.stop()).named(name + ".Stop");
    }

    /** * Creates a command to drive the turret to a target angle.
     * * @param angleRads The target angle in radians.
     * @return The command.
     */
    public Command goToAngle(double angleRads) {
        return runRepeatedly(() -> io.goToAngle(angleRads)).named(name + ".goToAngle");
    }

    /** * Creates a command to drive the turret to a target motor rotation.
     * * @param rotations The target point in motor rotations.
     * @return The command.
     */
    public Command goToPoint(double rotations) {
        return runRepeatedly(() -> io.goToPoint(rotations)).named(name + ".goToPoint");
    }

    /** * Creates a command to apply a constant voltage to the turret.
     * * @param volts The voltage to apply.
     * @return The command.
     */
    public Command applyVoltage(double volts) {
        return runRepeatedly(() -> io.applyVoltage(volts)).named(name + ".applyVoltage");
    }

    /** * Creates a command to apply a constant duty cycle to the turret.
     * * @param duty The duty cycle percentage.
     * @return The command.
     */
    public Command applyDutyCycle(double duty) {
        return runRepeatedly(() -> io.applyDutyCycle(duty)).named(name + ".applyDutyCycle");
    }

    @Override
    public Command idle() {
        return stop();
    }

    /** @return The current angle of the turret in radians. */
    public double getAngleRads() {
        return io.getAngleRads();
    }

    /** @return The current motor position in rotations. */
    public double getRotations() {
        return io.getRotations();
    }

    /** @return The current velocity in radians per second. */
    public double getVelocityRadsPerSec() {
        return io.getVelocityRadsPerSec();
    }

    /**
     * Checks if the turret is currently at a specified angle within a tolerance.
     *
     * @param angleRads The target angle.
     * @param toleranceRads The acceptable tolerance.
     * @return A trigger evaluating the position.
     */
    public Trigger atAngle(double angleRads, double toleranceRads) {
        return io.atAngle(angleRads, toleranceRads);
    }

    /**
     * Checks if the turret is currently at a specified angle within a default tolerance of 0.05 radians.
     *
     * @param angleRads The target angle.
     * @return A trigger evaluating the position.
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