package first.lib.mechanisms.elevator;

import org.wpilib.math.system.DCMotor;
import first.lib.hardware.motors.canMotors.CANMotor;
import first.lib.hardware.motors.canMotors.CANMotorConfig;
import first.lib.hardware.motors.canMotors.SparkMaxMotor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.With;

/**
 * Configuration class for the Elevator mechanism.
 * Utilizes Lombok for boilerplate generation (Builder, Getters, Setters, etc.).
 */
@Data
@Builder
@AllArgsConstructor
@With
public class ElevatorConfig {
    /** The DC motor type used in the elevator. */
    @Builder.Default public DCMotor motorType = DCMotor.getAndymark9015(1);
    
    /** The mass of the elevator carriage in kilograms. */
    @Builder.Default public double massKg = 5;
    
    /** The radius of the elevator pulley/spool in meters. */
    @Builder.Default public double radiusMeters = 0.2;
    
    /** The gear ratio from the motor to the output. */
    @Builder.Default public double gearing = 1;
    
    /** The minimum allowable height for the elevator in meters. */
    @Builder.Default public double minHeightMeters = 0;
    
    /** The maximum allowable height for the elevator in meters. */
    @Builder.Default public double maxHeightMeters = 1;
    
    /** Whether to simulate gravity acting downward on the elevator carriage. */
    @Builder.Default public boolean simulateGravity = false;
    
    /** The initial height of the elevator in meters. */
    @Builder.Default public double startingHeightMeters = 0;
    
    /** Standard deviations for simulated sensor noise measurements. */
    @Builder.Default public double[] measurementStdDevs = new double[]{0, 0.1};
    
    /** The CAN motor controller mapped to this mechanism. */
    @Builder.Default public CANMotor motor = new SparkMaxMotor(new CANMotorConfig());
}