package first.lib.mechanisms.simpleMotor;

import first.lib.hardware.motors.canMotors.CANMotor;

public class SimpleMotorReal extends SimpleMotorIO {

    public SimpleMotorReal(CANMotor motor) {
        super(motor);
    }

    public void runVoltage(double volts) {
        canMotor.runVoltage(volts);
    }

    public void runDuty(double percent) {
        canMotor.runDuty(percent);
    }

    public void goToPoint(double rotations) {
        canMotor.goToPoint(rotations);
    }

    public void runRPM(double rpm) {
        canMotor.runRPM(rpm);
    }

    public double getRPM() {
        return canMotor.getRPM();
    }

    public double getVelocity() {
        return canMotor.getVelocity();
    }

    public double getRotations() {
        return canMotor.getRotations();
    }

    public double getAppliedVoltage() {
        return canMotor.getAppliedVoltage();
    }

    public double getCurrent() {
        return canMotor.getCurrent();
    }

    public void stop() {
        canMotor.stop();
    }

    public void setSimEncoderPosition(double rotations) {
        canMotor.setSimEncoderPosition(rotations);
    }

    public void setSimEncoderVelocity(double rps) {
        canMotor.setSimEncoderVelocity(rps);
    }

}