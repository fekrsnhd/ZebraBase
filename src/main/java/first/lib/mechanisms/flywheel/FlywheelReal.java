package first.lib.mechanisms.flywheel;

import org.wpilib.command3.Trigger;
import first.lib.hardware.motors.canMotors.CANMotor;

public class FlywheelReal extends FlywheelIO {

    private final CANMotor motor;

    public FlywheelReal(FlywheelConfig cfg) {
        super(cfg);
        this.motor = config.motor;
    }

    public void periodic() {}

    public void stop() {
        motor.stop();
    }

    public void runRPM(double rpm) {
        motor.runRPM(rpm); // Assuming native runRPM on the CANMotor interface
    }

    public void applyVoltage(double volts) {
        motor.runVoltage(volts);
    }
    
    public void applyDutyCycle(double duty) {
        motor.runDuty(duty);
    }

    public double getRPM() {
        return motor.getRPM();
    }

    public Trigger atRPM(double rpm, double tolerance) {
        return new Trigger(() -> (Math.abs(getRPM() - rpm) <= tolerance));
    }
}