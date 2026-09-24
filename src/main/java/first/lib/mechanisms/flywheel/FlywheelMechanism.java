package first.lib.mechanisms.flywheel;

import org.littletonrobotics.junction.Logger;
import org.wpilib.command3.Command;
import org.wpilib.command3.Mechanism;
import org.wpilib.command3.Trigger;
import first.robot.globalConstants;

public class FlywheelMechanism implements Mechanism {

    private final FlywheelIO io;
    private final String name;
    private final FlywheelIOInputsAutoLogged inputs = new FlywheelIOInputsAutoLogged();

    public FlywheelMechanism(String mechName, FlywheelConfig config) {
        this.name = mechName;
        boolean sim = globalConstants.currentMode.equals(globalConstants.Mode.SIM);
        this.io = sim ? new FlywheelSimulation(config) : new FlywheelReal(config);
    }

    public Command stop() {
        return runRepeatedly(() -> io.stop()).named(name + ".Stop");
    }

    public Command runRPM(double rpm) {
        return runRepeatedly(() -> io.runRPM(rpm)).named(name + ".runRPM");
    }

    public Command applyVoltage(double volts) {
        return runRepeatedly(() -> io.applyVoltage(volts)).named(name + ".applyVoltage");
    }

    public Command applyDutyCycle(double duty) {
        return runRepeatedly(() -> io.applyDutyCycle(duty)).named(name + ".applyDutyCycle");
    }

    @Override
    public Command idle() {
        return stop();
    }

    public double getRPM() {
        return io.getRPM();
    }

    public Trigger atRPM(double rpm, double tolerance) {
        return io.atRPM(rpm, tolerance);
    }

    public Trigger atRPM(double rpm) {
        return atRPM(rpm, 50.0); // Default 50 RPM tolerance
    }

    @Override
    public String getName() {
        return name;
    }

    public void periodic() {
        io.periodic();
        io.updateInputs(inputs);
        Logger.processInputs(name, inputs);
    }
}