package first.lib.mechanisms.singleJointedArm;

import org.wpilib.math.system.DCMotor;
import first.lib.hardware.motors.canMotors.CANMotor;
import first.lib.hardware.motors.canMotors.CANMotorConfig;
import first.lib.hardware.motors.canMotors.SparkMaxMotor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.With;

/**
 * Configuration data class for a single-jointed arm mechanism.
 * Utilizes Lombok to automatically generate getters, setters, builders, and constructors.
 */
@Data
@Builder
@AllArgsConstructor
@With
public class SingleJointedArmConfig {
    /** The type and quantity of motors driving the arm. */
    @Builder.Default public DCMotor motorType = DCMotor.getNEO(1);
    
    /** The mass of the arm in kilograms. */
    @Builder.Default public double massKg = 4.0;
    
    /** The length of the arm from the pivot point to the center of mass in meters. */
    @Builder.Default public double lengthMeters = 0.5;
    
    /** The gear ratio between the motor and the arm joint. */
    @Builder.Default public double gearing = 150;
    
    /** The minimum allowable angle of the arm in radians. */
    @Builder.Default public double minAngleRads = 0.0;
    
    /** The maximum allowable angle of the arm in radians. */
    @Builder.Default public double maxAngleRads = Math.PI;
    
    /** Whether gravity should be factored into the physics simulation. */
    @Builder.Default public boolean simulateGravity = true;
    
    /** The initial starting angle of the arm in radians. */
    @Builder.Default public double startingAngleRads = 0;
    
    /** Standard deviations for measurement noise in the simulation (position, velocity). */
    @Builder.Default public double[] measurementStdDevs = new double[]{0, 0.1};
    
    /** The underlying CAN motor instance. Defaults to a SparkMax. */
    @Builder.Default public CANMotor motor = new SparkMaxMotor(new CANMotorConfig());
}