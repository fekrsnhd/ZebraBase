package first.lib.hardware.motors.simpleCanMotors;

import org.wpilib.hardware.bus.CANPort;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

/**
 * Configuration parameters for simplified CAN motors.
 * Uses Lombok's annotation processor to provide automatic setters, getters, 
 * default values, and `.with___()` syntax.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@With
public class SimpleCANMotorConfig {
    
    @Builder.Default public int id = 0;
    @Builder.Default public String busName = "rio";
    @Builder.Default public CANPort canPort = CANPort.CAN_S0;
    @Builder.Default public int currentLimit = 40;
    @Builder.Default public int leaderID = -1;
    @Builder.Default public boolean followerAlign = true;
    @Builder.Default public boolean motorInvert = false;
    @Builder.Default public boolean brakeOn = true;

}