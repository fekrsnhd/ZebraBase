package first.lib.mechanisms.elevator;

import org.littletonrobotics.junction.AutoLog;
import org.wpilib.command3.Trigger;

public abstract class ElevatorIO {

    @AutoLog
    public static class ElevatorIOInputs {
        public double position = 0;
        public double velocity = 0;
        public double rotations = 0;
    }

    protected final ElevatorConfig config;

    protected ElevatorIO(ElevatorConfig cfg) {
        this.config = cfg;
    }

    public abstract void periodic();

    public double rotationsToMeters(double rotations) {
        return rotations * (2.0 * Math.PI * config.radiusMeters) / config.gearing;
    }
    public double metersToRotations(double meters) {
        return meters * config.gearing / (2.0 * Math.PI * config.radiusMeters);
    }

    public abstract void stop();

    public abstract void goToHeight(double heightMeters);

    public abstract void goToPoint(double pointRotations);

    public abstract void applyVoltage(double volts);

    public abstract void applyDutyCycle(double duty);

    public abstract double getHeight();

    public abstract double getRotations();

    public abstract double getVelocityMetersPerSecond();

    public abstract Trigger atHeight(double height, double tolerance);

    public void updateInputs(ElevatorIOInputs inputs) {
        inputs.position = getHeight();
        inputs.velocity = getVelocityMetersPerSecond();
        inputs.rotations = getRotations();
    }

}
