package first.lib.mechanisms.turret;

import org.wpilib.math.system.DCMotor;

import first.lib.hardware.motors.canMotors.CANMotor;
import first.lib.hardware.motors.canMotors.CANMotorConfig;
import first.lib.hardware.motors.canMotors.SparkMaxMotor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.With;

@Data
@Builder
@AllArgsConstructor
@With
public class TurretConfig {
    @Builder.Default public DCMotor motorType = DCMotor.getAndymark9015(1);
    @Builder.Default public double moiKgMetersSquared = 0.5;
    @Builder.Default public double gearing = 100;
    @Builder.Default public double minAngleRads = -Math.PI;
    @Builder.Default public double maxAngleRads = Math.PI;
    @Builder.Default public double startingAngleRads = 0;
    @Builder.Default public double[] measurementStdDevs = new double[]{0, 0.1};
    @Builder.Default public CANMotor motor = new SparkMaxMotor(new CANMotorConfig());
}