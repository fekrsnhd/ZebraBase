package first.lib.hardware.encoders.relative;

import first.lib.hardware.encoders.RelativeEncoderInterface;
import first.lib.hardware.motors.canMotors.SparkFlexMotor;

/**
 * Maps a REV Spark Flex motor controller's internal encoder to the RelativeEncoder interface.
 */
public class SparkFlexRelativeEncoder implements RelativeEncoderInterface {

    private final SparkFlexMotor motor;

    /**
     * Constructs a relative encoder wrapper for a Spark Flex.
     * @param motorObject The SparkFlexMotor instance.
     */
    public SparkFlexRelativeEncoder(SparkFlexMotor motorObject) {
        this.motor = motorObject;
    }

    @Override
    public void setRelativePosition(double position) {
        motor.setRotorPosition(position);
    }

    @Override
    public double getRelativePosition() {
        return motor.getRotations();
    }

    @Override
    public void resetRelativePosition() {
        motor.setRotorPosition(0);
    }

    @Override
    public double getVelocity() {
        return motor.getVelocity();
    }
}