package first.lib.hardware.canDistance;

public abstract class DistanceSensor {

    protected final CanDistanceSensorConfig config;

    protected DistanceSensor(CanDistanceSensorConfig cfg) {
        this.config = cfg;
    }

    public CanDistanceSensorConfig getConfig() {
        return config;
    }

    public abstract double getProximity();

    public abstract boolean hasTarget();
}