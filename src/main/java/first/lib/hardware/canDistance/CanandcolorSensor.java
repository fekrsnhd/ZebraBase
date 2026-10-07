package first.lib.hardware.canDistance;

import org.wpilib.hardware.bus.CANPort;

import com.reduxrobotics.sensors.canandcolor.Canandcolor;

/**
 * Implementation of a DistanceSensor using the Redux Canandcolor.
 */
public class CanandcolorSensor implements DistanceSensor {

    private final Canandcolor sensor;

    /**
     * Constructs a CanandcolorSensor using a predefined configuration.
     * @param cfg The CAN configuration properties.
     */
    public CanandcolorSensor(int id, CANPort port) {
        this.sensor = new Canandcolor(id, port);
    }

    @Override
    public double getProximity() {
        return sensor.getProximity();
    }

    @Override
    public boolean hasTarget() {
        return sensor.getProximity() < 0.95;
    }

    /**
     * Retrieves the underlying Redux hardware object.
     * @return The Canandcolor instance.
     */
    public Canandcolor getDevice() {
        return sensor;
    }
}