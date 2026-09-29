package first.lib.mechanisms.flywheel;

import org.wpilib.math.system.DCMotor;
import first.lib.hardware.motors.canMotors.CANMotor;
import first.lib.hardware.motors.canMotors.CANMotorConfig;
import first.lib.hardware.motors.canMotors.SparkMaxMotor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.With;

/**
 * Configuration class for the Flywheel mechanism.
 * Utilizes Lombok for boilerplate code generation.
 */
@Data
@Builder
@AllArgsConstructor
@With
public class FlywheelConfig {
    /** The DC motor type used for the flywheel. */
    @Builder.Default public DCMotor motorType = DCMotor.getNEO(1);
    
    /** The moment of inertia in kg*m^2 for the flywheel mass. */
    @Builder.Default public double moiKgMetersSquared = 0.05;
    
    /** The gear ratio applied between the motor and the physical wheel. */
    @Builder.Default public double gearing = 1.0;
    
    /** Sensor noise model configurations for simulation. */
    @Builder.Default public double[] measurementStdDevs = new double[]{0, 0.1};
    
    /** The CAN motor controller mapped to this flywheel. */
    @Builder.Default public CANMotor motor = new SparkMaxMotor(new CANMotorConfig());
}