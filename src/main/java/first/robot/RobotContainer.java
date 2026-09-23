package first.robot;

import org.wpilib.command3.button.CommandXboxController;

import first.lib.hardware.motors.canMotors.CANMotorConfig;
import first.lib.hardware.motors.canMotors.TalonFXMotor;
import first.lib.mechanisms.simpleCANMotor.SimpleCANMotorMechanism;

public class RobotContainer {

    private final SimpleCANMotorMechanism simpleMotorMechanism;

    private final CommandXboxController controller = new CommandXboxController(0);

    public RobotContainer() {

        simpleMotorMechanism = new SimpleCANMotorMechanism("simpleMotor", new TalonFXMotor(new CANMotorConfig()));

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
