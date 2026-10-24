package first.lib.mechanisms.drivetrains.swerve;

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
public class SwerveModuleConfig {
    @Builder.Default public CANMotor driveMotor = new TalonFXMotor(new CANMotorConfig().withId(1));
    @Builder.Default public CANMotor steerMotor = new TalonFXMotor(new CANMotorConfig().withId(2));
    @Builder.Default public double driveGearRatio = 6.12;
    @Builder.Default public double steerGearRatio = 12.8; 
    @Builder.Default public double wheelRadiusMeters = 0.0508;
    @Builder.Default public double wheelWeightKg = 0.226;
    @Builder.Default public double absoluteEncoderOffsetRotations = 0.0;

    public SwerveModuleConfig withWheelRadiusInches(double inches) {
        return this.withWheelRadiusMeters(Units.inchesToMeters(inches));
    }
}

