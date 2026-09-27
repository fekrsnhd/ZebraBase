package first.lib.hardware.motors.pwmMotors;

import org.wpilib.drivers.motor.Koors40;
import org.wpilib.drivers.motor.PWMSparkFlex;
import org.wpilib.drivers.motor.PWMSparkMax;
import org.wpilib.drivers.motor.PWMTalonFX;
import org.wpilib.drivers.motor.PWMTalonSRX;
import org.wpilib.drivers.motor.PWMVenom;
import org.wpilib.drivers.motor.PWMVictorSPX;
import org.wpilib.drivers.motor.Spark;
import org.wpilib.drivers.motor.SparkMini;
import org.wpilib.drivers.motor.Talon;
import org.wpilib.drivers.motor.VictorSP;
import org.wpilib.hardware.motor.PWMMotorController;

import first.lib.hardware.motors.Motor;

public class PwmMotor extends Motor{

    private final PwmMotorConfig config;
    private final PWMMotorController motorController;

    private PwmMotor(PwmMotorConfig cfg) {
        this.config = cfg;

        switch (config.motorType) {
            case Koors40 -> motorController = new Koors40(cfg.port);
            case PWMSparkFlex -> motorController = new PWMSparkFlex(cfg.port);
            case PWMSparkMax -> motorController = new PWMSparkMax(cfg.port);
            case PWMTalonFX -> motorController = new PWMTalonFX(cfg.port);
            case PWMTalonSRX -> motorController = new PWMTalonSRX(cfg.port);
            case PWMVenom -> motorController = new PWMVenom(cfg.port);
            case PWMVictorSPX -> motorController = new PWMVictorSPX(cfg.port);
            case Spark -> motorController = new Spark(cfg.port);
            case SparkMini -> motorController = new SparkMini(cfg.port);
            case Talon -> motorController = new Talon(cfg.port);
            case VictorSP -> motorController = new VictorSP(cfg.port);
            default -> motorController = new PWMSparkMax(cfg.port);
        }

        motorController.setInverted(config.inverted);
    }

    public PwmMotor(PwmMotorConfig cfg, PwmMotor master) {
        this(cfg);
        master.addFollower(this);
    }

    protected PWMMotorController getMotorController() { return motorController; }
    protected PwmMotorConfig getConfig() { return config; }

    public void setThrottle(double throttle) { motorController.setThrottle(throttle); }
    public void setVoltage(double voltage) { motorController.setVoltage(voltage); }
    public void stopMotor() { motorController.stopMotor(); }
    public void disable() { motorController.disable(); }
    public double getThrottle() { return motorController.getThrottle(); }
    public double getVoltage() { return motorController.getVoltage(); }
    public void setInverted(boolean inverted) { motorController.setInverted(inverted); }
    public boolean getInverted() { return motorController.getInverted(); }
    public void addFollower(PwmMotor follower) { motorController.addFollower(follower.getMotorController()); }
    public void enableDeadbandElimination(boolean eliminateDeadband) { motorController.enableDeadbandElimination(eliminateDeadband); }
    public int getChannel() { return motorController.getChannel(); }
    public int getPwmHandle() { return motorController.getPwmHandle(); }
    public String getDescription() { return motorController.getDescription(); }
    public void setExpiration(double expirationTime) { motorController.setExpiration(expirationTime); }
    public double getExpiration() { return motorController.getExpiration(); }
    public void setSafetyEnabled(boolean enabled) { motorController.setSafetyEnabled(enabled); }
    public boolean isSafetyEnabled() { return motorController.isSafetyEnabled(); }
    public void feed() { motorController.feed(); }
    public void check() { motorController.check(); }
    public boolean isAlive() { return motorController.isAlive(); }

    @Override public void runDuty(double percent) { setThrottle(percent); }
    @Override public void stop()  { stopMotor(); }
    
    public static class PwmKoors40 extends PwmMotor {
        public PwmKoors40(int channel, boolean inverted) { super(new PwmMotorConfig(channel, inverted, PwmMotorType.Koors40)); }
        public PwmKoors40(int channel, boolean inverted, PwmMotor master) { super(new PwmMotorConfig(channel, inverted, PwmMotorType.Koors40), master); }
    }

    public static class PwmSparkFlex extends PwmMotor {
        public PwmSparkFlex(int channel, boolean inverted) { super(new PwmMotorConfig(channel, inverted, PwmMotorType.PWMSparkFlex)); }
        public PwmSparkFlex(int channel, boolean inverted, PwmMotor master) { super(new PwmMotorConfig(channel, inverted, PwmMotorType.PWMSparkFlex), master); }
    }

    public static class PwmSparkMax extends PwmMotor {
        public PwmSparkMax(int channel, boolean inverted) { super(new PwmMotorConfig(channel, inverted, PwmMotorType.PWMSparkMax)); }
        public PwmSparkMax(int channel, boolean inverted, PwmMotor master) { super(new PwmMotorConfig(channel, inverted, PwmMotorType.PWMSparkMax), master); }
    }

    public static class PwmTalonFX extends PwmMotor {
        public PwmTalonFX(int channel, boolean inverted) { super(new PwmMotorConfig(channel, inverted, PwmMotorType.PWMTalonFX)); }
        public PwmTalonFX(int channel, boolean inverted, PwmMotor master) { super(new PwmMotorConfig(channel, inverted, PwmMotorType.PWMTalonFX), master); }
    }

    public static class PwmTalonSRX extends PwmMotor {
        public PwmTalonSRX(int channel, boolean inverted) { super(new PwmMotorConfig(channel, inverted, PwmMotorType.PWMTalonSRX)); }
        public PwmTalonSRX(int channel, boolean inverted, PwmMotor master) { super(new PwmMotorConfig(channel, inverted, PwmMotorType.PWMTalonSRX), master); }
    }

    public static class PwmVenom extends PwmMotor {
        public PwmVenom(int channel, boolean inverted) { super(new PwmMotorConfig(channel, inverted, PwmMotorType.PWMVenom)); }
        public PwmVenom(int channel, boolean inverted, PwmMotor master) { super(new PwmMotorConfig(channel, inverted, PwmMotorType.PWMVenom), master); }
    }

    public static class PwmVictorSPX extends PwmMotor {
        public PwmVictorSPX(int channel, boolean inverted) { super(new PwmMotorConfig(channel, inverted, PwmMotorType.PWMVictorSPX)); }
        public PwmVictorSPX(int channel, boolean inverted, PwmMotor master) { super(new PwmMotorConfig(channel, inverted, PwmMotorType.PWMVictorSPX), master); }
    }

    public static class PwmSpark extends PwmMotor {
        public PwmSpark(int channel, boolean inverted) { super(new PwmMotorConfig(channel, inverted, PwmMotorType.Spark)); }
        public PwmSpark(int channel, boolean inverted, PwmMotor master) { super(new PwmMotorConfig(channel, inverted, PwmMotorType.Spark), master); }
    }

    public static class PwmSparkMini extends PwmMotor {
        public PwmSparkMini(int channel, boolean inverted) { super(new PwmMotorConfig(channel, inverted, PwmMotorType.SparkMini)); }
        public PwmSparkMini(int channel, boolean inverted, PwmMotor master) { super(new PwmMotorConfig(channel, inverted, PwmMotorType.SparkMini), master); }
    }

    public static class PwmTalon extends PwmMotor {
        public PwmTalon(int channel, boolean inverted) { super(new PwmMotorConfig(channel, inverted, PwmMotorType.Talon)); }
        public PwmTalon(int channel, boolean inverted, PwmMotor master) { super(new PwmMotorConfig(channel, inverted, PwmMotorType.Talon), master); }
    }

    public static class PwmVictorSP extends PwmMotor {
        public PwmVictorSP(int channel, boolean inverted) { super(new PwmMotorConfig(channel, inverted, PwmMotorType.VictorSP)); }
        public PwmVictorSP(int channel, boolean inverted, PwmMotor master) { super(new PwmMotorConfig(channel, inverted, PwmMotorType.VictorSP), master); }
    }

    private enum PwmMotorType {
        Koors40,
        PWMSparkFlex,
        PWMSparkMax,
        PWMTalonFX,
        PWMTalonSRX,
        PWMVenom,
        PWMVictorSPX,
        Spark,
        SparkMini,
        Talon,
        VictorSP
    }

    private static class PwmMotorConfig {
        private int port;
        private boolean inverted;
        private PwmMotorType motorType;
        
        protected PwmMotorConfig(int cfgPort, boolean cfgInvert, PwmMotorType cfgMotorType) {
            this.port = cfgPort;
            this.inverted = cfgInvert;
            this.motorType = cfgMotorType;
        }
    }
}