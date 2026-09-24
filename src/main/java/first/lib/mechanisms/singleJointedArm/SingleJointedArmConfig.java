package first.lib.mechanisms.singleJointedArm;

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
public class SingleJointedArmConfig {
    @Builder.Default public DCMotor motorType = DCMotor.getNEO(1);
    @Builder.Default public double massKg = 4.0;
    @Builder.Default public double lengthMeters = 0.5;
    @Builder.Default public double gearing = 150;
    @Builder.Default public double minAngleRads = 0.0;
    @Builder.Default public double maxAngleRads = Math.PI;
    @Builder.Default public boolean simulateGravity = true;
    @Builder.Default public double startingAngleRads = 0;
    @Builder.Default public double[] measurementStdDevs = new double[]{0, 0.1};
    @Builder.Default public CANMotor motor = new SparkMaxMotor(new CANMotorConfig());
}