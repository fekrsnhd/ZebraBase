package first.lib.mechanisms.elevator;

import org.littletonrobotics.junction.Logger;
import org.wpilib.command3.Command;
import org.wpilib.command3.Mechanism;
import org.wpilib.command3.Trigger;

import first.robot.globalConstants;

/**
 * Elevator Mechanism class wrapping ElevatorIO using native WPILib v3 mechanism standards,
 * AdvantageKit logging, and declarative command bindings.
 */
public class ElevatorMechanism implements Mechanism {

    private final ElevatorIO io;
    private final String name;
    private final ElevatorIOInputsAutoLogged inputs = new ElevatorIOInputsAutoLogged();

    /**
     * Constructs a new ElevatorMechanism.
     * Automatically instantiates the real or simulated IO layer based on the global current mode.
     *
     * @param mechName The name of the mechanism.
     * @param config   The configuration settings for the elevator.
     */
    public ElevatorMechanism(String mechName, ElevatorConfig config) {
        this.name = mechName;
        boolean sim = globalConstants.currentMode.equals(globalConstants.Mode.SIM);
        this.io = sim ? new ElevatorSimulation(config) : new ElevatorReal(config);
    }

    /**
     * @return A Command that repeatedly stops the elevator motor.
     */
    public Command stop() {
        return runRepeatedly(() -> io.stop()).named(name + ".Stop");
    }

    /**
     * @param heightMeters The target height in meters.
     * @return A Command that continuously drives the elevator to the target height.
     */
    public Command goToHeight(double heightMeters) {
        return runRepeatedly(() -> io.goToHeight(heightMeters)).named(name + ".goToHeight");
    }

    /**
     * @param rotations The target point in rotations.
     * @return A Command that continuously drives the elevator to the target rotation point.
     */
    public Command goToPoint(double rotations) {
        return runRepeatedly(() -> io.goToPoint(rotations)).named(name + ".goToPoint");
    }

    /**
     * @param volts The voltage to apply.
     * @return A Command that continuously applies a specific voltage.
     */
    public Command applyVoltage(double volts) {
        return runRepeatedly(() -> io.applyVoltage(volts)).named(name + ".applyVoltage");
    }

    /**
     * @param duty The duty cycle percentage.
     * @return A Command that continuously applies a specific duty cycle.
     */
    public Command applyDutyCycle(double duty) {
        return runRepeatedly(() -> io.applyDutyCycle(duty)).named(name + ".applyDutyCycle");
    }

    /**
     * Default command execution behavior for this mechanism.
     *
     * @return The idle command (stop).
     */
    @Override
    public Command idle() {
        return stop();
    }

    /**
     * @return The current height of the elevator in meters.
     */
    public double getHeight() {
        return io.getHeight();
    }

    /**
     * @return The current position of the elevator in rotations.
     */
    public double getRotations() {
        return io.getRotations();
    }

    /**
     * @return The current linear velocity in meters per second.
     */
    public double getVelocityMetersPerSecond() {
        return io.getVelocityMetersPerSecond();
    }

    /**
     * @param heightMeters    The target height in meters.
     * @param toleranceMeters The acceptable distance from the target in meters.
     * @return A Trigger determining if the elevator is at the specified height.
     */
    public Trigger atHeight(double heightMeters, double toleranceMeters) {
        return io.atHeight(heightMeters, toleranceMeters);
    }

    /**
     * Creates an atHeight trigger using a default tolerance of 0.02 meters.
     *
     * @param heightMeters The target height in meters.
     * @return A Trigger determining if the elevator is at the specified height.
     */
    public Trigger atHeight(double heightMeters) {
        return atHeight(heightMeters, 0.02);
    }

    /**
     * @return The mechanism's string identifier.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Periodically updates the IO layer and logs telemetry via AdvantageKit.
     */
    public void periodic() {
        io.periodic();
        io.updateInputs(inputs);
        Logger.processInputs(name, inputs);
    }
}