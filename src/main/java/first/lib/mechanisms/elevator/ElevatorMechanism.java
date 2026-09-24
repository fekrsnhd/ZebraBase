package first.lib.mechanisms.elevator;

import org.littletonrobotics.junction.Logger;
import org.wpilib.command3.Command;
import org.wpilib.command3.Mechanism;
import org.wpilib.command3.Trigger;

import first.robot.globalConstants;

/*
Elevator Mechanism class wrapping ElevatorIO using native WPILib v3 mechanism standards,
AdvantageKit logging, and declarative command bindings.
https://github.wpilib.org/allwpilib/docs/beta/java/org/wpilib/command3/Mechanism.html
*/
public class ElevatorMechanism implements Mechanism {

    private final ElevatorIO io;
    private final String name;
    private final ElevatorIOInputsAutoLogged inputs = new ElevatorIOInputsAutoLogged();

    public ElevatorMechanism(String mechName, ElevatorConfig config) {
        this.name = mechName;
        boolean sim = globalConstants.currentMode.equals(globalConstants.Mode.SIM);
        this.io = sim ? new ElevatorSimulation(config) : new ElevatorReal(config);
    }


    public Command stop() {
        return runRepeatedly(() -> io.stop()).named(name + ".Stop");
    }

    public Command goToHeight(double heightMeters) {
        return runRepeatedly(() -> io.goToHeight(heightMeters)).named(name + ".goToHeight");
    }

    public Command goToPoint(double rotations) {
        return runRepeatedly(() -> io.goToPoint(rotations)).named(name + ".goToPoint");
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


    public double getHeight() {
        return io.getHeight();
    }

    public double getRotations() {
        return io.getRotations();
    }

    public double getVelocityMetersPerSecond() {
        return io.getVelocityMetersPerSecond();
    }

    public Trigger atHeight(double heightMeters, double toleranceMeters) {
        return io.atHeight(heightMeters, toleranceMeters);
    }

    public Trigger atHeight(double heightMeters) {
        return atHeight(heightMeters, 0.02);
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