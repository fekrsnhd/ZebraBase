package first.lib.hardware.pwm.servos;

public class LinearActuator extends ServoController {
    
    private double fullLengthMM = 180;

    public LinearActuator(PwmServoControllerConfig cfg) { super(cfg); }

    public LinearActuator(int channel) {
        PwmServoControllerConfig config = new PwmServoControllerConfig(channel, 600, 2400, -1);
        this(config);
    }

    public LinearActuator(int channel, int minRate, int maxRate) {
        PwmServoControllerConfig config = new PwmServoControllerConfig(channel, minRate, maxRate, -1);
        this(config);
    }

    public LinearActuator(int channel, int minRate, int maxRate, int outputPeriod) {
        PwmServoControllerConfig config = new PwmServoControllerConfig(channel, minRate, maxRate, outputPeriod);
        this(config);
    }

    public LinearActuator WithFullLength(double lengthMM) {
        fullLengthMM = lengthMM;
        return this;
    }

    public void setPostition(double posMM) {
        setPercent(posMM / fullLengthMM);
    }
    
}
