package first.lib.mechanisms.simpleCANMotor;

import first.lib.hardware.motors.canMotors.CANMotor;

/**
 * Simulated implementation of the SimpleCANMotorIO.
 * Interacts with a simulated CANMotor instance to replicate behavior without hardware.
 */
public class SimpleCANMotorSim extends SimpleCANMotorIO {

    private final CANMotor canMotor;

    /**
     * Constructs a SimpleCANMotorSim for interacting with simulated hardware.
     *
     * @param motor The simulated CANMotor instance.
     */
    public SimpleCANMotorSim(CANMotor motor) {
        super(motor);
        this.canMotor = motor;
    }

    @Override
    public void runVoltage(double volts) {
        canMotor.runVoltage(volts);
    }

    @Override
    public void runDuty(double percent) {
        canMotor.runDuty(percent);
    }

    @Override
    public void goToPoint(double rotations) {
        canMotor.goToPoint(rotations);
    }

    @Override
    public void runRPM(double rpm) {
        canMotor.runRPM(rpm);
    }

    @Override
    public double getRPM() {
        return canMotor.getRPM();
    }

    @Override
    public double getVelocity() {
        return canMotor.getVelocity();
    }

    @Override
    public double getRotations() {
        return canMotor.getRotations();
    }

    @Override
    public double getAppliedVoltage() {
        return canMotor.getAppliedVoltage();
    }

    @Override
    public double getCurrent() {
        return canMotor.getCurrent();
    }

    @Override
    public void stop() {
        canMotor.stop();
    }

    @Override
    public void setSimEncoderPosition(double rotations) {
        canMotor.setSimEncoderPosition(rotations);
    }

    @Override
    public void setSimEncoderVelocity(double rps) {
        canMotor.setSimEncoderVelocity(rps);
    }
}