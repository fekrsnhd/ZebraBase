package first.lib.mechanisms.simpleCANMotor;

import first.lib.hardware.motors.canMotors.CANMotor;

/**
 * Real hardware implementation of the SimpleCANMotorIO.
 * Passes commands directly through to a physical CANMotor instance.
 */
public class SimpleCANMotorReal extends SimpleCANMotorIO {

    /**
     * Constructs a SimpleCANMotorReal for interacting with physical hardware.
     *
     * @param motor The physical CANMotor instance.
     */
    public SimpleCANMotorReal(CANMotor motor) {
        super(motor);
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