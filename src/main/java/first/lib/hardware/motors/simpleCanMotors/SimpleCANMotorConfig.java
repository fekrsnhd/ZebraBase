package first.lib.hardware.motors.simpleCanMotors;

import org.wpilib.hardware.bus.CANPort;

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

public class SimpleCANMotorConfig {
    /* 
    Configuration parameters for the CAN motor 
    Uses Lombok's annotation processor to make automatic setters, getters, and default values
    also has .with___() methods for easier creation of complex objects 
    */
    @Builder.Default public int id = 0;
    @Builder.Default public String busName = "rio";
    @Builder.Default public CANPort canPort = CANPort.CAN_S0;
    @Builder.Default public int currentLimit = 40;
    @Builder.Default public int leaderID = -1;
    @Builder.Default public boolean followerAlign = true;
    @Builder.Default public boolean motorInvert = false;
    @Builder.Default public boolean brakeOn = true;

}
