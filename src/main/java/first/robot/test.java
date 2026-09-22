package first.robot;

import org.wpilib.hardware.bus.CANPort;

import first.lib.hardware.motors.canMotors.CANMotor;
import first.lib.hardware.motors.canMotors.CANMotorConfig;
import first.lib.hardware.motors.canMotors.SparkMaxMotor;
import first.lib.hardware.motors.canMotors.TalonFXMotor;
import first.lib.mechanisms.simpleMotor.SimpleMotorMechanism;

public class test {

    CANMotorConfig cmc = new CANMotorConfig();

    CANMotor motor = new TalonFXMotor(new CANMotorConfig().withId(9).withCanPort(CANPort.CAN_D0));

    CANMotor motor2 = new SparkMaxMotor(new CANMotorConfig().withId(9).withCanPort(CANPort.CAN_D0));

    SimpleMotorMechanism smm = new SimpleMotorMechanism("motor", motor);

    public void testMethod() {
        smm.runDuty(0.5);
    }

}
