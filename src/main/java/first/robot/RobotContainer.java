package first.robot;

import first.lib.hardware.imus.Pigeon2imu;
import first.lib.mechanisms.drivetrains.swerve.SwerveDriveMechanism;
import first.robot.mechanisms.swerve.SwerveBuilder;
import first.robot.mechanisms.swerve.SwerveConstants;

import org.wpilib.command3.button.CommandDualShock4Controller;

public class RobotContainer {

    private SwerveDriveMechanism drivetrain;

    private CommandDualShock4Controller controller = new CommandDualShock4Controller(0);

    public RobotContainer() {
        drivetrain = SwerveBuilder.build(new Pigeon2imu(0, "swerve"));
        configureBindings();
    }

    private void configureBindings() {
        drivetrain.setDefaultCommand(
            drivetrain.drive(
                () -> controller.getLeftX() * SwerveConstants.kSpeedAt12Volts.magnitude(), 
                () -> controller.getLeftY() * SwerveConstants.kSpeedAt12Volts.magnitude(), 
                () -> controller.getRightX() * SwerveConstants.kMaxAngularRate
            )
        );
    }

    public void periodic() {
        drivetrain.periodic();
    }
}
