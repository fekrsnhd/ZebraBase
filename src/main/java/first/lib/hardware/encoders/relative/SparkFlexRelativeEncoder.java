package first.lib.hardware.encoders.relative;

import first.lib.hardware.encoders.EncoderConfig;
import first.lib.hardware.motors.canMotors.SparkFlexMotor;

public class SparkFlexRelativeEncoder extends RelativeEncoder {

    private final SparkFlexMotor motor;

    public SparkFlexRelativeEncoder(SparkFlexMotor motorObject, EncoderConfig cfg) {
        super(cfg);
        this.motor = motorObject;
    }

    public void setPositon(double position) {
        motor.setRotorPosition(position);
    }

    public double getPositon() {
        return motor.getRotations();
    }

    public void resetPosition() {
        motor.setRotorPosition(0);
    }

    public double getVelocity() {
        return motor.getVelocity();
    }
}
