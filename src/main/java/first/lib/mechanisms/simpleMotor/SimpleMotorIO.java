package first.lib.mechanisms.simpleMotor;

import org.littletonrobotics.junction.AutoLog;

import first.lib.hardware.motors.canMotors.CANMotor;

public abstract class SimpleMotorIO {
    
    @AutoLog
    public static class SimpleMotorIOInputs {
        public double position = 0;
        public double current = 0;
        public double volts = 0;
        public double velocity = 0;
    }

    protected final CANMotor canMotor;

    protected SimpleMotorIO(CANMotor motor) {
        this.canMotor = motor;
    }
    
    // public CANMotor getMotor() {
    //     return canMotor;
    // }

    public abstract void runVoltage(double volts);

    public abstract void runDuty(double percent);

    public abstract void goToPoint(double rotations);

    public abstract void runRPM(double rpm);

    public abstract double getRPM();

    public abstract double getVelocity(); //RPS

    public abstract double getRotations();

    public abstract double getAppliedVoltage();

    public abstract double getCurrent();

    public abstract void stop();

    public abstract void setSimEncoderPosition(double rotations);

    public abstract void setSimEncoderVelocity(double rps);

    public void updateInputs(SimpleMotorIOInputs inputs) {
        inputs.position = canMotor.getRotations();
        inputs.velocity = canMotor.getVelocity();
        inputs.current = canMotor.getCurrent();
        inputs.volts = canMotor.getAppliedVoltage();
    }
    

}