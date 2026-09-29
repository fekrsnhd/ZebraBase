package first.lib.hardware.encoders.hybrid;

import org.wpilib.hardware.bus.CANPort;
import com.reduxrobotics.sensors.canandmag.Canandmag;
import first.lib.hardware.encoders.AbsoluteEncoderInterface;
import first.lib.hardware.encoders.RelativeEncoderInterface;

/**
 * Implements a hybrid encoder utilizing the Redux Canandmag.
 */
public class CanandmagEncoder implements AbsoluteEncoderInterface, RelativeEncoderInterface {

    private final Canandmag encoder;

    /**
     * Constructs a Canandmag encoder.
     * @param id The CAN ID.
     * @param canPort The CAN port assignment.
     * @param inverted Whether to invert the direction of the encoder.
     */
    public CanandmagEncoder(int id, CANPort canPort, boolean inverted) {
        this.encoder = new Canandmag(id, canPort);
        
        if (inverted) {
            encoder.setSettings(encoder.getSettings().setInvertDirection(true));
        } else {
            encoder.setSettings(encoder.getSettings().setInvertDirection(false));
        }
    }

    @Override
    public void setRelativePosition(double position) {
        encoder.setPosition(position);
    }

    @Override
    public double getRelativePosition() {
        return encoder.getPosition();
    }

    @Override
    public void resetRelativePosition() {
        encoder.setPosition(0);
    }

    /**
     * Sets the absolute position offset.
     * @param position The position value to set.
     */
    public void setAbsolutePosition(double position) {
        encoder.setAbsPosition(position);
    }

    @Override
    public double getAbsolutePosition() {
        return encoder.getAbsPosition();
    }

    /**
     * Resets the absolute position tracking.
     */
    public void resetAbsolutePosition() {
        encoder.setAbsPosition(0);
    }

    @Override
    public double getVelocity() {
        return encoder.getVelocity();
    }
}