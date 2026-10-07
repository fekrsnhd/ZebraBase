package first.lib.hardware.canDistance;

import org.wpilib.hardware.bus.CANPort;

import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.hardware.CANrange;

/**
 * Implementation of a DistanceSensor using the CTRE CANrange.
 */
public class CanRangeSensor implements DistanceSensor {

    private final CANrange sensor;

    public CanRangeSensor(int id, CANPort port) {
        this.sensor = new CANrange(id, new CANBus(port));
    }

    public CanRangeSensor(int id, String name) {
        this.sensor = new CANrange(id, new CANBus(name));
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