package first.lib.hardware.canDistance;

import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.hardware.CANrange;

public class CanRangeSensor extends DistanceSensor {

    private final CANrange sensor;

    public CanRangeSensor(CanDistanceSensorConfig cfg) {
        super(cfg);
        this.sensor = new CANrange(config.id, new CANBus(config.busName));
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