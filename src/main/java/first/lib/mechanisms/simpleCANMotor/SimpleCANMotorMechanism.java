package first.lib.mechanisms.simpleCANMotor;

import org.littletonrobotics.junction.Logger;
import org.wpilib.command3.Command;
import org.wpilib.command3.Mechanism;

import first.lib.hardware.motors.canMotors.CANMotor;
import first.robot.globalConstants;
/*
Simple motor mechanism uses the CANMotor class to create a wpilib v3 mechanism with the native command structure
this allows other files to use the motors commands more easily
https://github.wpilib.org/allwpilib/docs/beta/java/org/wpilib/command3/Mechanism.html
 */
public class SimpleCANMotorMechanism implements Mechanism {

    private final SimpleCANMotorIO io;

    private final String name;
    
    private final SimpleCANMotorIOInputsAutoLogged inputs = new SimpleCANMotorIOInputsAutoLogged();

    public SimpleCANMotorMechanism(String mechName, CANMotor motor) {
        this.name = mechName;
        boolean sim = globalConstants.currentMode.equals(globalConstants.Mode.SIM);
        this.io = sim ? new SimpleCANMotorSim(motor) : new SimpleCANMotorReal(motor);
        
    }

    public Command stop() { //Stop command runs until interupted 
        return runRepeatedly(() -> io.stop()).named(name + ".Stop");
    }

    @Override
    public Command idle() { //Overrides native idle command with the mechanism's stop command
        return stop();
    }

    @Override
    public String getName() { //Overrides the native getName method which would normally return the object name
        return name;
    }

    public Command runDuty(double duty) {
        return runRepeatedly(() -> io.runDuty(duty)).named(name + ".runDuty");
    }

    public Command runVoltage(double voltage) {
        return runRepeatedly(() -> io.runVoltage(voltage)).named(name + ".runVoltage");
    }

    public Command goToPoint(double rotations) {
        return runRepeatedly(() -> io.goToPoint(rotations)).named(name + ".goToPoint");
    }

    public Command runRPM(double rpm) {
        return runRepeatedly(() -> io.runRPM(rpm)).named(name + ".runRPM");
    }

    public double getRPM() {
        return io.getRPM();
    }

    public double getVelocity() {
        return io.getVelocity();
    }

    public double getRotations() {
        return io.getRotations();
    }

    public double getAppliedVoltage() {
        return io.getAppliedVoltage();
    }

    public double getCurrent() {
        return io.getCurrent();
    }

    public void setSimEncoderPosition(double rotations) {
        io.setSimEncoderPosition(rotations);
    }

    public void setSimEncoderVelocity(double rps) {
        io.setSimEncoderVelocity(rps);
    }
    
    public void periodic() {
        io.updateInputs(inputs);
        Logger.processInputs(name, inputs);
    }
}