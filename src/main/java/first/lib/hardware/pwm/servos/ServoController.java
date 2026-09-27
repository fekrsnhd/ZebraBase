package first.lib.hardware.pwm.servos;

import org.wpilib.hardware.discrete.PWM;

public class ServoController {
    private final PWM pwmHardware;
    public final ServoControllerConfig config;

    public ServoController(ServoControllerConfig cfg) {
        this.config = cfg;
        pwmHardware = new PWM(config.channel);
        if (config.outputPeriod != -1) {
            pwmHardware.setOutputPeriod(config.outputPeriod);
        }
    }

    public void setPulseWidth(int pulseWidth) {
        pulseWidth = Math.clamp(pulseWidth, config.minRate, config.maxRate);
        pwmHardware.setPulseTimeMicroseconds(pulseWidth);
    }

    public void setPercent(double percent) {
        int pulseWidth = (int) (config.minRate + (percent * (config.maxRate - config.minRate)));
        pwmHardware.setPulseTimeMicroseconds(pulseWidth);
    }

    public int getPulseWidth() {
        return pwmHardware.getPulseTimeMicroseconds();
    }

    public double getPercent() {
        return ((pwmHardware.getPulseTimeMicroseconds() - config.minRate) * 1.0) / (config.maxRate - config.minRate);
    }

}
