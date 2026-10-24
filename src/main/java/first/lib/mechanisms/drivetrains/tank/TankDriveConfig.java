package first.lib.mechanisms.drivetrains.tank;

import org.wpilib.math.system.DCMotor;
import org.wpilib.math.util.Units;

import first.lib.hardware.motors.canMotors.CANMotor;
import first.lib.hardware.motors.canMotors.CANMotorConfig;
import first.lib.hardware.motors.canMotors.TalonFXMotor;
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
public class TankDriveConfig {
    @Builder.Default public CANMotor leftLeader = new TalonFXMotor(new CANMotorConfig().withId(1));
    @Builder.Default public CANMotor rightLeader = new TalonFXMotor(new CANMotorConfig().withId(2));
    
    @Builder.Default public DCMotor motorType = DCMotor.getFalcon500(2);
    @Builder.Default public double gearRatio = 10.71; // e.g., Kitbot single speed
    @Builder.Default public double wheelRadiusMeters = Units.inchesToMeters(3.0);
    @Builder.Default public double trackWidthMeters = Units.inchesToMeters(24.0);
    @Builder.Default public double robotMassKg = 50.0;
}