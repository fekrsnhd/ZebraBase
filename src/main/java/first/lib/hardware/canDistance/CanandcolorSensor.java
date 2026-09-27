package first.lib.hardware.canDistance;

import com.reduxrobotics.sensors.canandcolor.Canandcolor;

public class CanandcolorSensor extends DistanceSensor {

    private final Canandcolor sensor;

    public CanandcolorSensor(CanDistanceSensorConfig cfg) {
        super(cfg);
        this.sensor = new Canandcolor(config.id);
    }

    @Override
    public double getProximity() {
        return sensor.getProximity();
    }

    @Override
    public boolean hasTarget() {
        return sensor.getProximity() < 0.95;
    }

    public Canandcolor getDevice() {
        return sensor;
    }
}