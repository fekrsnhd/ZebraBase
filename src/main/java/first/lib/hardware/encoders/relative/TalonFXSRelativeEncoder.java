package first.lib.hardware.encoders.relative;

import first.lib.hardware.encoders.RelativeEncoderInterface;
import first.lib.hardware.motors.canMotors.TalonFXSMotor;

/**
 * Maps a CTRE Talon FXS motor controller's internal encoder to the RelativeEncoderInterface.
 */
public class TalonFXSRelativeEncoder implements RelativeEncoderInterface {

    private final TalonFXSMotor motor;

    /**
     * Constructs a relative encoder wrapper for a Talon FXS.
     * @param motorObject The TalonFXSMotor instance.
     */
    public TalonFXSRelativeEncoder(TalonFXSMotor motorObject) {
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