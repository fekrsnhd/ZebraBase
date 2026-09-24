package first.lib.mechanisms.elevator;

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

public class ElevatorConfig{
    @Builder.Default public DCMotor motorType = DCMotor.getAndymark9015(1);
    @Builder.Default public double massKg = 5;
    @Builder.Default public double radiusMeters = 0.2;
    @Builder.Default public double gearing = 1;
    @Builder.Default public double minHeightMeters = 0;
    @Builder.Default public double maxHeightMeters = 1;
    @Builder.Default public boolean simulateGravity = false;
    @Builder.Default public double startingHeightMeters = 0;
    @Builder.Default public double[] measurementStdDevs = new double[]{0, 0.1};
    @Builder.Default public CANMotor motor = new SparkMaxMotor(new CANMotorConfig());
}