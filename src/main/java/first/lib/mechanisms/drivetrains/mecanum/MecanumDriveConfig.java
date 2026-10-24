package first.lib.mechanisms.drivetrains.mecanum;

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
public class MecanumDriveConfig {
    @Builder.Default public CANMotor frontLeft = new TalonFXMotor(new CANMotorConfig().withId(1));
    @Builder.Default public CANMotor frontRight = new TalonFXMotor(new CANMotorConfig().withId(2));
    @Builder.Default public CANMotor rearLeft = new TalonFXMotor(new CANMotorConfig().withId(3));
    @Builder.Default public CANMotor rearRight = new TalonFXMotor(new CANMotorConfig().withId(4));

    @Builder.Default public double gearRatio = 8.45;
    @Builder.Default public double wheelRadiusMeters = Units.inchesToMeters(3.0);
}