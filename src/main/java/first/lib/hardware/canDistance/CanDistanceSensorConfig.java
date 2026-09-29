package first.lib.hardware.canDistance;

import org.wpilib.hardware.bus.CANPort;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

/**
 * Configuration data class for CAN-based distance sensors.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@With
public class CanDistanceSensorConfig {
    
    @Builder.Default public int id = 0;
    @Builder.Default public String busName = "rio";
    @Builder.Default public CANPort canPort = CANPort.CAN_S0;

}