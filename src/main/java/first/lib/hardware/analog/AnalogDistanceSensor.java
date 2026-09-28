package first.lib.hardware.analog;

import java.util.function.DoubleUnaryOperator;

import first.lib.hardware.canDistance.DistanceSensor;

public class AnalogDistanceSensor extends AnalogInputController implements DistanceSensor {

    private DoubleUnaryOperator voltageToDistanceFunction;

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

    public AnalogDistanceSensor(int channel, DoubleUnaryOperator voltageToDistanceFunction) {
        super(channel);
        this.voltageToDistanceFunction = voltageToDistanceFunction;
    }


    public double getDistance() {
        return voltageToDistanceFunction.applyAsDouble(getVoltage());
    }

    @Override
    public boolean hasTarget() {
        return true;
    } 

    @Override
    public double getProximity() {
        return getDistance();
    } 
}