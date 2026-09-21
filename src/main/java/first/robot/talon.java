package first.robot;

import org.wpilib.hardware.bus.CANPort;

import first.lib.hardware.motors.canMotors.CANMotor;
import first.lib.hardware.motors.canMotors.CANMotorConfig;
import first.lib.hardware.motors.canMotors.SparkMaxMotor;
import first.lib.hardware.motors.canMotors.TalonFXMotor;

public class talon {

    CANMotorConfig cmc = new CANMotorConfig();

    CANMotor motor = new TalonFXMotor(new CANMotorConfig().withId(9).withCanPort(CANPort.CAN_D0));

    CANMotor motor2 = new SparkMaxMotor(new CANMotorConfig().withId(9).withCanPort(CANPort.CAN_D0));

}
