package first.lib.mechanisms.turret;

import org.wpilib.math.system.DCMotor;

import first.lib.hardware.motors.canMotors.CANMotor;
import first.lib.hardware.motors.canMotors.CANMotorConfig;
import first.lib.hardware.motors.canMotors.SparkMaxMotor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.With;

/**
 * Configuration data class for a Turret mechanism.
 * Utilizes Lombok to automatically generate getters, setters, builders, and constructors.
 */
@Data
@Builder
@AllArgsConstructor
@With
public class TurretConfig {
    /** The type and quantity of motors driving the turret. */
    @Builder.Default public DCMotor motorType = DCMotor.getAndymark9015(1);
    
    /** The moment of inertia of the turret in kg*m^2. */
    @Builder.Default public double moiKgMetersSquared = 0.5;
    
    /** The gear ratio between the motor and the turret rotation. */
    @Builder.Default public double gearing = 100;
    
    /** The minimum allowable angle of the turret in radians. */
    @Builder.Default public double minAngleRads = -Math.PI;
    
    /** The maximum allowable angle of the turret in radians. */
    @Builder.Default public double maxAngleRads = Math.PI;
    
    /** The initial starting angle of the turret in radians. */
    @Builder.Default public double startingAngleRads = 0;
    
    /** Standard deviations for measurement noise in the simulation. */
    @Builder.Default public double[] measurementStdDevs = new double[]{0, 0.1};
    
    /** The underlying CAN motor instance. Defaults to a SparkMax. */
    @Builder.Default public CANMotor motor = new SparkMaxMotor(new CANMotorConfig());
}