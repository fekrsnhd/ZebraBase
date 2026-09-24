package first.lib.mechanisms.flywheel;

import org.littletonrobotics.junction.AutoLog;
import org.wpilib.command3.Trigger;

public abstract class FlywheelIO {

    @AutoLog
    public static class FlywheelIOInputs {
        public double velocityRPM = 0;
        public double appliedVolts = 0;
    }

    protected final FlywheelConfig config;

    protected FlywheelIO(FlywheelConfig cfg) {
        this.config = cfg;
    }

    public abstract void periodic();

    public abstract void stop();

    public abstract void runRPM(double rpm);
    
    public abstract void applyVoltage(double volts);

    public abstract void applyDutyCycle(double duty);

    public abstract double getRPM();

    public abstract Trigger atRPM(double rpm, double tolerance);

    public void updateInputs(FlywheelIOInputs inputs) {
        inputs.velocityRPM = getRPM();
        inputs.appliedVolts = config.motor.getAppliedVoltage();
    }
}