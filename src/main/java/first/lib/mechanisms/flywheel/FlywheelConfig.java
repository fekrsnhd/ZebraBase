package first.lib.mechanisms.flywheel;

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
public class FlywheelConfig {
    @Builder.Default public DCMotor motorType = DCMotor.getNEO(1);
    @Builder.Default public double moiKgMetersSquared = 0.05;
    @Builder.Default public double gearing = 1.0;
    @Builder.Default public double[] measurementStdDevs = new double[]{0, 0.1};
    @Builder.Default public CANMotor motor = new SparkMaxMotor(new CANMotorConfig());
}