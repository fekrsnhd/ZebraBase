package first.lib.hardware.encoders.hybrid;

import com.thrifty.canEncoder.CanEncoder;
import com.thrifty.canEncoder.CanEncoderConfig;
import com.thrifty.core.Motor.Direction;

import first.lib.hardware.encoders.AbsoluteEncoderInterface;
import first.lib.hardware.encoders.RelativeEncoderInterface;

/**
 * Implements a hybrid encoder utilizing the 2027 Thrifty CAN Through Bore Encoder.
 */
public class ThriftyCANEncoder implements AbsoluteEncoderInterface, RelativeEncoderInterface {

    private final CanEncoder encoder;

    /**
     * Constructs a Thrifty CAN Encoder wrapper.
     * @param canPort The CAN bus port index.
     * @param id The CAN ID of the encoder.
     * @param inverted Whether to invert the encoder direction.
     * @param offset Zero offset in rotations [0.0, 1.0).
     */
    public ThriftyCANEncoder(int canPort, int id, boolean inverted, double offset) {
        this.encoder = new CanEncoder(canPort, id);

        // Convert rotation offset [0.0, 1.0) to 12-bit raw ticks (0 - 4095)
        int offsetTicks = (int) Math.round(offset * 4096.0) % 4096;

        encoder.configure(
            CanEncoderConfig.direction(inverted ? Direction.CLOCKWISE : Direction.COUNTER_CLOCKWISE),
            CanEncoderConfig.zeroOffset(offsetTicks)
        );
    }

    @Override
    public double getAbsolutePosition() {
        return encoder.status().getPositionAbs();
    }

    @Override
    public void setRelativePosition(double position) {
        System.out.print("No Set Position");
    }

    @Override
    public double getRelativePosition() {
        return encoder.status().getPositionRel();
    }

    @Override
    public void resetRelativePosition() {
        System.out.print("No Reset");
    }

    @Override
    public double getVelocity() {
        return encoder.status().getVelocity();
    }
}