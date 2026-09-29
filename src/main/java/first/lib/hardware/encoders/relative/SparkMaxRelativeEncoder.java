package first.lib.hardware.encoders.relative;

import first.lib.hardware.encoders.RelativeEncoderInterface;
import first.lib.hardware.motors.canMotors.SparkMaxMotor;

/**
 * Maps a REV Spark Max motor controller's internal encoder to the RelativeEncoderInterface.
 */
public class SparkMaxRelativeEncoder implements RelativeEncoderInterface {

    private final SparkMaxMotor motor;

    /**
     * Constructs a relative encoder wrapper for a Spark Max.
     * @param motorObject The SparkMaxMotor instance.
     */
    public SparkMaxRelativeEncoder(SparkMaxMotor motorObject) {
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