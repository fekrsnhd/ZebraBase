package first.lib.hardware.canDistance;

public interface DistanceSensor {

    public abstract double getProximity();

    public abstract boolean hasTarget();
}