package first.robot;

import org.wpilib.command3.button.CommandXboxController;
import org.wpilib.hardware.bus.CANPort;

import first.lib.hardware.motors.canMotors.CANMotorConfig;
import first.lib.hardware.motors.canMotors.SparkMaxMotor;
import first.lib.hardware.motors.pwmMotors.PwmMotor;
import first.lib.hardware.motors.pwmMotors.PwmMotor.PwmSpark;
import first.lib.hardware.powerDistribution.PowerDistributionBoard.PDH;
import first.lib.mechanisms.simpleCANMotor.SimpleCANMotorMechanism;

public class RobotContainer {

    private final SimpleCANMotorMechanism simpleMotorMechanism;

    private final PwmMotor pwmMotor;

    private final CommandXboxController controller = new CommandXboxController(0);

    private final PDH pdh;

    public RobotContainer() {

        pdh = new PDH(CANPort.CAN_D0, 1);

        pwmMotor = new PwmSpark(0, true);

        simpleMotorMechanism = new SimpleCANMotorMechanism("simpleMotor", new SparkMaxMotor(new CANMotorConfig()));

        configureBindings();
        
    }

    private void configureBindings() {
        controller.x().onTrue(simpleMotorMechanism.stop());
        controller.y().onTrue(simpleMotorMechanism.runDuty(0.5));
    }

    public void periodic() {
        //implementation
    }
}
