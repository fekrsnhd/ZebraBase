package first.lib.hardware.pwm.servos;

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

public class ServoControllerConfig {
    /* 
    Configuration parameters for pwm objects
    Uses Lombok's annotation processor to make automatic setters, getters, and default values
    also has .with___() methods for easier creation of complex objects 
    */
    @Builder.Default public int channel = 0;
    @Builder.Default public int minRate = 0;
    @Builder.Default public int maxRate = 4096;
    @Builder.Default public int outputPeriod = -1;

    public ServoControllerConfig withRange(int min, int max) {
        return this.withMaxRate(max).withMinRate(min);
    }

}
