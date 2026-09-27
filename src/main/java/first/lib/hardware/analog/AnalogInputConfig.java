package first.lib.hardware.analog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@With
public class AnalogInputConfig {
    /* 
    Configuration parameters for analog input objects
    Uses Lombok's annotation processor to make automatic setters, getters, and default values
    also has .with___() methods for easier creation of complex objects 
    */
    @Builder.Default public int channel = 0;
    @Builder.Default public double minVoltage = 0.0;
    @Builder.Default public double maxVoltage = 5.0;

    public AnalogInputConfig withVoltageRange(double min, double max) {
        return this.withMinVoltage(min).withMaxVoltage(max);
    }
}