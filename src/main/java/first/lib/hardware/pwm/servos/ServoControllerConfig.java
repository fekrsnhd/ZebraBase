package first.lib.hardware.pwm.servos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

/**
 * Configuration parameters for PWM objects.
 * Uses Lombok's annotation processor to provide automatic setters, getters, 
 * default values, and `.with___()` syntax.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@With
public class ServoControllerConfig {
    
    @Builder.Default public int channel = 0;
    @Builder.Default public int minRate = 0;
    @Builder.Default public int maxRate = 4096;
    @Builder.Default public int outputPeriod = -1;

    /**
     * Configures the minimum and maximum pulse rates.
     * @param min The minimum rate in microseconds.
     * @param max The maximum rate in microseconds.
     * @return A new ServoControllerConfig instance with updated bounds.
     */
    public ServoControllerConfig withRange(int min, int max) {
        return this.withMaxRate(max).withMinRate(min);
    }

}