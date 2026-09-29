package first.lib.hardware.canDistance;

import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.hardware.CANrange;

/**
 * Implementation of a DistanceSensor using the CTRE CANrange.
 */
public class CanRangeSensor implements DistanceSensor {

    private final CANrange sensor;

    /**
     * Constructs a CanRangeSensor using a predefined configuration.
     * @param cfg The CAN configuration properties.
     */
    public CanRangeSensor(CanDistanceSensorConfig cfg) {
        this.sensor = new CANrange(cfg.id, new CANBus(cfg.busName));
    }

    @Override
    public double getProximity() {
        return sensor.getDistance().getValueAsDouble();
    }

    @Override
    public boolean hasTarget() {
        return sensor.getIsDetected().getValue();
    }

}