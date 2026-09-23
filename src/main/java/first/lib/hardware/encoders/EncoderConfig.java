package first.lib.hardware.encoders;

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

public class EncoderConfig {
    @Builder.Default public int id = 0;
    @Builder.Default public String busName = "rio";
    @Builder.Default public CANPort canPort = CANPort.CAN_S0;
    @Builder.Default public boolean inverted = false;
}
