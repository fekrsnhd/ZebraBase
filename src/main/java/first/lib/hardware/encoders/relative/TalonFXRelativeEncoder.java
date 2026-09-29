package first.lib.hardware.encoders.relative;

import first.lib.hardware.encoders.RelativeEncoderInterface;
import first.lib.hardware.motors.canMotors.TalonFXMotor;

/**
 * Maps a CTRE Talon FX motor controller's internal encoder to the RelativeEncoderInterface.
 */
public class TalonFXRelativeEncoder implements RelativeEncoderInterface {

    private final TalonFXMotor motor;

    /**
     * Constructs a relative encoder wrapper for a Talon FX.
     * @param motorObject The TalonFXMotor instance.
     */
    public TalonFXRelativeEncoder(TalonFXMotor motorObject) {
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