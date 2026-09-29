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

/**
 * Universal wrapper for various PWM motor controllers.
 * Subclasses handle instantiation for specific hardware types.
 */
public class PwmMotor implements Motor {

    private final PwmMotorConfig config;
    private final PWMMotorController motorController;

    /**
     * Constructs a PwmMotor based on the provided configuration.
     * @param cfg Configuration defining the PWM channel and motor type.
     */
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

    /**
     * Constructs a PwmMotor as a follower to a master motor.
     * @param cfg Configuration defining the PWM channel and motor type.
     * @param master The master motor to follow.
     */
    public PwmMotor(PwmMotorConfig cfg, PwmMotor master) {
        this(cfg);
        master.addFollower(this);
    }

    /** @return The underlying WPILib PWMMotorController instance */
    protected PWMMotorController getMotorController() { return motorController; }
    
    /** @return The configuration used to build this motor */
    protected PwmMotorConfig getConfig() { return config; }

    /** @param throttle Throttle value between -1.0 and 1.0 */
    public void setThrottle(double throttle) { motorController.setThrottle(throttle); }
    
    /** @param voltage Output voltage */
    public void setVoltage(double voltage) { motorController.setVoltage(voltage); }
    
    /** Stops the motor output */
    public void stopMotor() { motorController.stopMotor(); }
    
    /** Disables the motor controller */
    public void disable() { motorController.disable(); }
    
    /** @return The current set throttle */
    public double getThrottle() { return motorController.getThrottle(); }
    
    /** @return The current set voltage */
    public double getVoltage() { return motorController.getVoltage(); }
    
    /** @param inverted Sets whether the motor output is inverted */
    public void setInverted(boolean inverted) { motorController.setInverted(inverted); }
    
    /** @return True if the motor is inverted, false otherwise */
    public boolean getInverted() { return motorController.getInverted(); }
    
    /** @param follower Another PwmMotor to follow this instance */
    public void addFollower(PwmMotor follower) { motorController.addFollower(follower.getMotorController()); }
    
    /** @param eliminateDeadband True to eliminate deadband region */
    public void enableDeadbandElimination(boolean eliminateDeadband) { motorController.enableDeadbandElimination(eliminateDeadband); }
    
    /** @return The PWM channel the motor is connected to */
    public int getChannel() { return motorController.getChannel(); }
    
    /** @return The native handle of the PWM channel */
    public int getPwmHandle() { return motorController.getPwmHandle(); }
    
    /** @return A descriptive string for the motor */
    public String getDescription() { return motorController.getDescription(); }
    
    /** @param expirationTime The motor safety expiration time */
    public void setExpiration(double expirationTime) { motorController.setExpiration(expirationTime); }
    
    /** @return The motor safety expiration time */
    public double getExpiration() { return motorController.getExpiration(); }
    
    /** @param enabled True to enable motor safety features */
    public void setSafetyEnabled(boolean enabled) { motorController.setSafetyEnabled(enabled); }
    
    /** @return True if motor safety is enabled */
    public boolean isSafetyEnabled() { return motorController.isSafetyEnabled(); }
    
    /** Feeds the motor safety object */
    public void feed() { motorController.feed(); }
    
    /** Checks the motor safety status */
    public void check() { motorController.check(); }
    
    /** @return True if the motor controller is alive */
    public boolean isAlive() { return motorController.isAlive(); }

    @Override public void runDuty(double percent) { setThrottle(percent); }
    @Override public void stop()  { stopMotor(); }
    
    /** PwmMotor instantiation wrapper for Koors40 */
    public static class PwmKoors40 extends PwmMotor {
        public PwmKoors40(int channel, boolean inverted) { super(new PwmMotorConfig(channel, inverted, PwmMotorType.Koors40)); }
        public PwmKoors40(int channel, boolean inverted, PwmMotor master) { super(new PwmMotorConfig(channel, inverted, PwmMotorType.Koors40), master); }
    }

    /** PwmMotor instantiation wrapper for Spark Flex */
    public static class PwmSparkFlex extends PwmMotor {
        public PwmSparkFlex(int channel, boolean inverted) { super(new PwmMotorConfig(channel, inverted, PwmMotorType.PWMSparkFlex)); }
        public PwmSparkFlex(int channel, boolean inverted, PwmMotor master) { super(new PwmMotorConfig(channel, inverted, PwmMotorType.PWMSparkFlex), master); }
    }

    /** PwmMotor instantiation wrapper for Spark Max */
    public static class PwmSparkMax extends PwmMotor {
        public PwmSparkMax(int channel, boolean inverted) { super(new PwmMotorConfig(channel, inverted, PwmMotorType.PWMSparkMax)); }
        public PwmSparkMax(int channel, boolean inverted, PwmMotor master) { super(new PwmMotorConfig(channel, inverted, PwmMotorType.PWMSparkMax), master); }
    }

    /** PwmMotor instantiation wrapper for Talon FX */
    public static class PwmTalonFX extends PwmMotor {
        public PwmTalonFX(int channel, boolean inverted) { super(new PwmMotorConfig(channel, inverted, PwmMotorType.PWMTalonFX)); }
        public PwmTalonFX(int channel, boolean inverted, PwmMotor master) { super(new PwmMotorConfig(channel, inverted, PwmMotorType.PWMTalonFX), master); }
    }

    /** PwmMotor instantiation wrapper for Talon SRX */
    public static class PwmTalonSRX extends PwmMotor {
        public PwmTalonSRX(int channel, boolean inverted) { super(new PwmMotorConfig(channel, inverted, PwmMotorType.PWMTalonSRX)); }
        public PwmTalonSRX(int channel, boolean inverted, PwmMotor master) { super(new PwmMotorConfig(channel, inverted, PwmMotorType.PWMTalonSRX), master); }
    }

    /** PwmMotor instantiation wrapper for Venom */
    public static class PwmVenom extends PwmMotor {
        public PwmVenom(int channel, boolean inverted) { super(new PwmMotorConfig(channel, inverted, PwmMotorType.PWMVenom)); }
        public PwmVenom(int channel, boolean inverted, PwmMotor master) { super(new PwmMotorConfig(channel, inverted, PwmMotorType.PWMVenom), master); }
    }

    /** PwmMotor instantiation wrapper for Victor SPX */
    public static class PwmVictorSPX extends PwmMotor {
        public PwmVictorSPX(int channel, boolean inverted) { super(new PwmMotorConfig(channel, inverted, PwmMotorType.PWMVictorSPX)); }
        public PwmVictorSPX(int channel, boolean inverted, PwmMotor master) { super(new PwmMotorConfig(channel, inverted, PwmMotorType.PWMVictorSPX), master); }
    }

    /** PwmMotor instantiation wrapper for basic Spark */
    public static class PwmSpark extends PwmMotor {
        public PwmSpark(int channel, boolean inverted) { super(new PwmMotorConfig(channel, inverted, PwmMotorType.Spark)); }
        public PwmSpark(int channel, boolean inverted, PwmMotor master) { super(new PwmMotorConfig(channel, inverted, PwmMotorType.Spark), master); }
    }

    /** PwmMotor instantiation wrapper for Spark Mini */
    public static class PwmSparkMini extends PwmMotor {
        public PwmSparkMini(int channel, boolean inverted) { super(new PwmMotorConfig(channel, inverted, PwmMotorType.SparkMini)); }
        public PwmSparkMini(int channel, boolean inverted, PwmMotor master) { super(new PwmMotorConfig(channel, inverted, PwmMotorType.SparkMini), master); }
    }

    /** PwmMotor instantiation wrapper for Talon */
    public static class PwmTalon extends PwmMotor {
        public PwmTalon(int channel, boolean inverted) { super(new PwmMotorConfig(channel, inverted, PwmMotorType.Talon)); }
        public PwmTalon(int channel, boolean inverted, PwmMotor master) { super(new PwmMotorConfig(channel, inverted, PwmMotorType.Talon), master); }
    }

    /** PwmMotor instantiation wrapper for Victor SP */
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