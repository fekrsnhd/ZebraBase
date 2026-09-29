package first.lib.hardware.analog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

/**
 * Configuration data class for analog input objects.
 * Uses Lombok's annotation processor to provide automatic setters, getters, 
 * default values, and `.with___()` syntax.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@With
public class AnalogInputConfig {
    
    @Builder.Default public int channel = 0;
    @Builder.Default public double minVoltage = 0.0;
    @Builder.Default public double maxVoltage = 5.0;

    /**
     * Configures the minimum and maximum voltage bounds for the sensor.
     * 
     * @param min The lowest expected voltage from the sensor.
     * @param max The highest expected voltage from the sensor.
     * @return A new AnalogInputConfig instance with updated bounds.
     */
    public AnalogInputConfig withVoltageRange(double min, double max) {
        return this.withMinVoltage(min).withMaxVoltage(max);
    }
}