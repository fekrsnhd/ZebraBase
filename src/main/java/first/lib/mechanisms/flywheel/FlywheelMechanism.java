package first.lib.mechanisms.flywheel;

import org.littletonrobotics.junction.Logger;
import org.wpilib.command3.Command;
import org.wpilib.command3.Mechanism;
import org.wpilib.command3.Trigger;
import first.robot.globalConstants;

/**
 * Flywheel Mechanism managing logic and declarative Command interfaces.
 */
public class FlywheelMechanism implements Mechanism {

    private final FlywheelIO io;
    private final String name;
    private final FlywheelIOInputsAutoLogged inputs = new FlywheelIOInputsAutoLogged();

    /**
     * Connects to hardware or sim based on the current mode and initializes a Flywheel.
     *
     * @param mechName Mechanism identifier string.
     * @param config   Configuration requirements for behavior.
     */
    public FlywheelMechanism(String mechName, FlywheelConfig config) {
        this.name = mechName;
        boolean sim = globalConstants.currentMode.equals(globalConstants.Mode.SIM);
        this.io = sim ? new FlywheelSimulation(config) : new FlywheelReal(config);
    }

    /**
     * @return A Command repeatedly issuing stop signals.
     */
    public Command stop() {
        return runRepeatedly(() -> io.stop()).named(name + ".Stop");
    }

    /**
     * @param rpm The target RPM limit.
     * @return A Command continuously enforcing target RPM on the flywheel.
     */
    public Command runRPM(double rpm) {
        return runRepeatedly(() -> io.runRPM(rpm)).named(name + ".runRPM");
    }

    /**
     * @param volts Target output voltage.
     * @return A Command applying continuous output voltage.
     */
    public Command applyVoltage(double volts) {
        return runRepeatedly(() -> io.applyVoltage(volts)).named(name + ".applyVoltage");
    }

    /**
     * @param duty Duty cycle requirement.
     * @return A Command running standard open-loop output mapping.
     */
    public Command applyDutyCycle(double duty) {
        return runRepeatedly(() -> io.applyDutyCycle(duty)).named(name + ".applyDutyCycle");
    }

    /**
     * Yields default interaction context.
     *
     * @return Stop command configuration.
     */
    @Override
    public Command idle() {
        return stop();
    }

    /**
     * @return RPM extracted dynamically from IO structure.
     */
    public double getRPM() {
        return io.getRPM();
    }

    /**
     * @param rpm       Target RPM value.
     * @param tolerance Discrepancy boundary for positive response.
     * @return Conditional status indicator.
     */
    public Trigger atRPM(double rpm, double tolerance) {
        return io.atRPM(rpm, tolerance);
    }

    /**
     * Creates a trigger utilizing a preset tolerance of 50 RPM.
     *
     * @param rpm The target RPM limit.
     * @return Conditional status indicator.
     */
    public Trigger atRPM(double rpm) {
        return atRPM(rpm, 50.0);
    }

    /**
     * @return The text label of the subsystem.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Executes standard telemetry synchronization and execution logic iteratively.
     */
    public void periodic() {
        io.periodic();
        io.updateInputs(inputs);
        Logger.processInputs(name, inputs);
    }
}