package first.lib.mechanisms.singleJointedArm;

import org.littletonrobotics.junction.Logger;
import org.wpilib.command3.Command;
import org.wpilib.command3.Mechanism;
import org.wpilib.command3.Trigger;
import first.robot.globalConstants;

public class SingleJointedArmMechanism implements Mechanism {

    private final SingleJointedArmIO io;
    private final String name;
    private final SingleJointedArmIOInputsAutoLogged inputs = new SingleJointedArmIOInputsAutoLogged();

    public SingleJointedArmMechanism(String mechName, SingleJointedArmConfig config) {
        this.name = mechName;
        boolean sim = globalConstants.currentMode.equals(globalConstants.Mode.SIM);
        this.io = sim ? new SingleJointedArmSimulation(config) : new SingleJointedArmReal(config);
    }



    public Command stop() {
        return runRepeatedly(() -> io.stop()).named(name + ".Stop");
    }

    public Command goToAngle(double angleRads) {
        return runRepeatedly(() -> io.goToAngle(angleRads)).named(name + ".goToAngle");
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

    @Override public Command idle() { return stop(); }



    public double getAngleRads() { return io.getAngleRads(); }

    public double getRotations() { return io.getRotations(); }

    public double getVelocityRadsPerSec() { return io.getVelocityRadsPerSec(); }

    

    public Trigger atAngle(double angleRads, double toleranceRads) {
        return io.atAngle(angleRads, toleranceRads);
    }

    public Trigger atAngle(double angleRads) {
        return atAngle(angleRads, 0.05); // Default 0.05 rad tolerance
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