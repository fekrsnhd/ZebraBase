package first.lib.hardware.pwm.servos;

public class Servo extends ServoController {
    
    private double fullAngle = 180;

    public Servo(PwmServoControllerConfig cfg) { super(cfg); }

    public Servo(int channel) {
        PwmServoControllerConfig config = new PwmServoControllerConfig(channel, 600, 2400, -1);
        this(config);
    }

    public Servo(int channel, int minRate, int maxRate) {
        PwmServoControllerConfig config = new PwmServoControllerConfig(channel, minRate, maxRate, -1);
        this(config);
    }

    public Servo(int channel, int minRate, int maxRate, int outputPeriod) {
        PwmServoControllerConfig config = new PwmServoControllerConfig(channel, minRate, maxRate, outputPeriod);
        this(config);
    }

    public Servo WithFullAngle(double angle) {
        this.fullAngle = angle;
        return this;
    }

    public void setAngle(double angle) {
        setPercent(angle / fullAngle);
    }
    
}
