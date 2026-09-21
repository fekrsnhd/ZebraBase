package first.lib.hardware.motors.canMotors;

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

public class CANMotorConfig {
    /* 
    Configuration parameters for the CAN motor 
    Uses Lombok's annotation processor to make automatic setters, getters, and default values
    also has .with___() methods for easier creation of complex objects 
    */
    @Builder.Default public int id = 0;
    @Builder.Default public String busName = "rio";
    @Builder.Default public CANPort canPort = CANPort.CAN_S0;
    @Builder.Default public int currentLimit = 40;
    @Builder.Default public double kP = 1;
    @Builder.Default public double kI = 0;
    @Builder.Default public double kD = 0;
    @Builder.Default public double kS = 0.3;
    @Builder.Default public double kV = 0.01;
    @Builder.Default public double kA = 0.3;
    @Builder.Default public double kG = 0.05;
    @Builder.Default public double tolerance = 0.1;
    @Builder.Default public boolean gravityTypeElevator = false;
    @Builder.Default public double armCosRatio = 1;
    @Builder.Default public double armOffset = 0;
    @Builder.Default public int leaderID = -1;
    @Builder.Default public boolean followerAlign = true;
    @Builder.Default public boolean motorInvert = false;
    @Builder.Default public boolean brakeOn = true;

    public CANMotorConfig withPID(double p, double i, double d) {
        return this.withKP(p).withKI(i).withKD(d);
    }

    public CANMotorConfig withPIDG(double p, double i, double d, double g) {
        return this.withKP(p).withKI(i).withKD(d).withKG(g);
    }

    public CANMotorConfig withSVA(double s, double v, double a) {
        return this.withKS(s).withKV(v).withKA(a);
    }
}
