package first.robot;

// import first.lib.hardware.imus.Pigeon2imu;
// import first.lib.mechanisms.drivetrains.swerve.SwerveDriveMechanism;
// import first.robot.mechanisms.swerve.SwerveBuilder;
// import first.robot.mechanisms.swerve.SwerveConstants;

// import org.wpilib.command3.button.CommandDualShock4Controller;
// import org.wpilib.hardware.bus.SerialPort;

public class RobotContainer {

    // private SerialPort megaPiPort;

    // private SwerveDriveMechanism drivetrain;

    // private CommandDualShock4Controller controller = new CommandDualShock4Controller(0);

    public RobotContainer() {
        try {
        // kUSB opens the first available USB serial device (/dev/ttyUSB0 or /dev/ttyACM0)
            // megaPiPort = new SerialPort(115200, SerialPort.Port.USB);
            System.out.println("Connected to MegaPi via USB!");
        } catch (Exception e) {
            System.out.println("Failed to open USB serial port: " + e.getMessage());
        }
        // drivetrain = SwerveBuilder.build(new Pigeon2imu(0, "swerve"));
        configureBindings();
    }

    private void configureBindings() {
        // drivetrain.setDefaultCommand(
        //     drivetrain.drive(
        //         () -> controller.getLeftX() * SwerveConstants.kSpeedAt12Volts.magnitude(), 
        //         () -> controller.getLeftY() * SwerveConstants.kSpeedAt12Volts.magnitude(), 
        //         () -> controller.getRightX() * SwerveConstants.kMaxAngularRate
        //     )
        // );
    }

    public void periodic() {
        // drivetrain.periodic();
        int frontLeft = 150;
        int frontRight = 150;
        int rearLeft = 150;
        int rearRight = 150;

        String command = String.format("M:%d,%d,%d,%d\n", frontLeft, frontRight, rearLeft, rearRight);
        
        // if (megaPiPort != null) {
        //     megaPiPort.writeString(command);
        // }
    }
}
