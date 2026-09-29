package first.lib.hardware.analog;

import java.util.function.DoubleUnaryOperator;
import first.lib.hardware.canDistance.DistanceSensor;

/**
 * Represents an analog distance sensor (e.g., an ultrasonic or infrared sensor) 
 * that converts a voltage reading into a physical distance measurement.
 */
public class AnalogDistanceSensor extends AnalogInputController implements DistanceSensor {

    private DoubleUnaryOperator voltageToDistanceFunction;

    /**
     * Constructs an AnalogDistanceSensor by interpolating between minimum and maximum bounds.
     * 
     * @param channel           The analog input channel
     * @param minVoltage        The voltage corresponding to the minimum distance
     * @param maxVoltage        The voltage corresponding to the maximum distance
     * @param minDistanceInches The minimum physical distance in inches
     * @param maxDistanceInches The maximum physical distance in inches
     */
    public AnalogDistanceSensor(int channel, double minVoltage, double maxVoltage, double minDistanceInches, double maxDistanceInches) {
        super(AnalogInputConfig.builder()
                .channel(channel)
                .minVoltage(minVoltage)
                .maxVoltage(maxVoltage)
                .build());
        
        this.voltageToDistanceFunction = voltage -> {
            double normalized = getNormalizedPosition();
            return minDistanceInches + normalized * (maxDistanceInches - minDistanceInches);
        };
    }

    /**
     * Constructs an AnalogDistanceSensor using a custom mathematical function to convert voltage to distance.
     * 
     * @param channel                   The analog input channel on the RoboRIO.
     * @param voltageToDistanceFunction A function mapping raw voltage (double) to distance (double).
     */
    public AnalogDistanceSensor(int channel, DoubleUnaryOperator voltageToDistanceFunction) {
        super(channel);
        this.voltageToDistanceFunction = voltageToDistanceFunction;
    }

    /**
     * Calculates the current measured distance based on the voltage mapping.
     * 
     * @return The distance, typically in inches.
     */
    public double getDistance() {
        return voltageToDistanceFunction.applyAsDouble(getVoltage());
    }

    /**
     * Determines if a target is currently detected.
     * 
     * @return True inherently, as analog distance sensors continuously return a valid reading.
     */
    @Override
    public boolean hasTarget() {
        return true;
    } 

    /**
     * Retrieves the proximity value to satisfy the DistanceSensor interface.
     * 
     * @return The calculated distance.
     */
    @Override
    public double getProximity() {
        return getDistance();
    } 
}