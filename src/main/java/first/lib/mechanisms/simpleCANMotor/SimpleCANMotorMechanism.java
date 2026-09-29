package first.lib.mechanisms.simpleCANMotor;

import org.littletonrobotics.junction.Logger;
import org.wpilib.command3.Command;
import org.wpilib.command3.Mechanism;

import first.lib.hardware.motors.canMotors.CANMotor;
import first.robot.globalConstants;

/**
 * Simple motor mechanism utilizing the CANMotor class to create a WPILib v3 mechanism.
 * This utilizes the native command structure, allowing other subsystems and files
 * to use the motor commands more seamlessly.
 * 
 * @see <a href="https://github.wpilib.org/allwpilib/docs/beta/java/org/wpilib/command3/Mechanism.html">WPILib Mechanism Documentation</a>
 */
public class SimpleCANMotorMechanism implements Mechanism {

    private final SimpleCANMotorIO io;
    private final String name;
    private final SimpleCANMotorIOInputsAutoLogged inputs = new SimpleCANMotorIOInputsAutoLogged();

    /**
     * Constructs a new SimpleCANMotorMechanism.
     * Initializes the appropriate IO implementation based on the current robot mode (Sim or Real).
     *
     * @param mechName The name of the mechanism, used for logging and command naming.
     * @param motor    The underlying CANMotor to control.
     */
    public SimpleCANMotorMechanism(String mechName, CANMotor motor) {
        this.name = mechName;
        boolean sim = globalConstants.currentMode.equals(globalConstants.Mode.SIM);
        this.io = sim ? new SimpleCANMotorSim(motor) : new SimpleCANMotorReal(motor);
    }

    /**
     * Creates a command to stop the motor. The command runs repeatedly until interrupted.
     *
     * @return A command that stops the motor.
     */
    public Command stop() {
        return runRepeatedly(() -> io.stop()).named(name + ".Stop");
    }

    /**
     * Overrides the native idle command to use this mechanism's specific stop command.
     *
     * @return A command that idles/stops the mechanism.
     */
    @Override
    public Command idle() {
        return stop();
    }

    /**
     * Retrieves the configured name of the mechanism.
     * Overrides the native method to return the assigned string instead of the object reference name.
     *
     * @return The name of the mechanism.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Creates a command to run the motor at a specific duty cycle.
     *
     * @param duty The duty cycle to run at.
     * @return A command that applies the duty cycle continuously.
     */
    public Command runDuty(double duty) {
        return runRepeatedly(() -> io.runDuty(duty)).named(name + ".runDuty");
    }

    /**
     * Creates a command to run the motor at a specific voltage.
     *
     * @param voltage The voltage to apply.
     * @return A command that applies the voltage continuously.
     */
    public Command runVoltage(double voltage) {
        return runRepeatedly(() -> io.runVoltage(voltage)).named(name + ".runVoltage");
    }

    /**
     * Creates a command to move the motor to a specific position.
     *
     * @param rotations The target position in rotations.
     * @return A command that directs the motor to the target point.
     */
    public Command goToPoint(double rotations) {
        return runRepeatedly(() -> io.goToPoint(rotations)).named(name + ".goToPoint");
    }

    /**
     * Creates a command to run the motor at a specific RPM.
     *
     * @param rpm The target speed in revolutions per minute.
     * @return A command that maintains the target RPM.
     */
    public Command runRPM(double rpm) {
        return runRepeatedly(() -> io.runRPM(rpm)).named(name + ".runRPM");
    }

    /**
     * Gets the current speed of the motor in RPM.
     *
     * @return The current RPM.
     */
    public double getRPM() {
        return io.getRPM();
    }

    /**
     * Gets the current velocity of the motor in rotations per second (RPS).
     *
     * @return The current velocity in RPS.
     */
    public double getVelocity() {
        return io.getVelocity();
    }

    /**
     * Gets the current position of the motor in rotations.
     *
     * @return The position in rotations.
     */
    public double getRotations() {
        return io.getRotations();
    }

    /**
     * Gets the voltage currently applied to the motor.
     *
     * @return The applied voltage.
     */
    public double getAppliedVoltage() {
        return io.getAppliedVoltage();
    }

    /**
     * Gets the current drawn by the motor.
     *
     * @return The current in amps.
     */
    public double getCurrent() {
        return io.getCurrent();
    }

    /**
     * Sets the simulated encoder position.
     *
     * @param rotations The position to set for the simulated encoder.
     */
    public void setSimEncoderPosition(double rotations) {
        io.setSimEncoderPosition(rotations);
    }

    /**
     * Sets the simulated encoder velocity.
     *
     * @param rps The velocity in RPS to set for the simulated encoder.
     */
    public void setSimEncoderVelocity(double rps) {
        io.setSimEncoderVelocity(rps);
    }
    
    /**
     * Updates the IO inputs and logs them periodically.
     * This should be called every loop cycle.
     */
    public void periodic() {
        io.updateInputs(inputs);
        Logger.processInputs(name, inputs);
    }
}