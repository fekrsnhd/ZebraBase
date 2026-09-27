package first.lib.hardware.powerDistribution;

import org.wpilib.hardware.bus.CANPort;
import org.wpilib.hardware.power.PowerDistribution;
import org.wpilib.hardware.power.PowerDistribution.ModuleType;
import org.wpilib.hardware.hal.PowerDistributionFaults;
import org.wpilib.hardware.hal.PowerDistributionStickyFaults;
import org.wpilib.hardware.hal.PowerDistributionVersion;

public class PowerDistributionBoard {
    private final PowerDistribution powerDist;

    private PowerDistributionBoard(CANPort port, int id, ModuleType type) {
        powerDist = new PowerDistribution(port, id, type);
    }

    private PowerDistributionBoard(CANPort port) {
        powerDist = new PowerDistribution(port);
    }

    public double getVoltage() {
        return powerDist.getVoltage();
    }

    public double getTemperature() {
        return powerDist.getTemperature();
    }

    public double getCurrent(int channel) {
        return powerDist.getCurrent(channel);
    }

    public double[] getAllCurrents() {
        return powerDist.getAllCurrents();
    }

    public double getTotalCurrent() {
        return powerDist.getTotalCurrent();
    }

    public double getTotalPower() {
        return powerDist.getTotalPower();
    }

    public double getTotalEnergy() {
        return powerDist.getTotalEnergy();
    }

    public void resetTotalEnergy() {
        powerDist.resetTotalEnergy();
    }

    public void clearStickyFaults() {
        powerDist.clearStickyFaults();
    }

    public int getModule() {
        return powerDist.getModule();
    }

    public ModuleType getType() {
        return powerDist.getType();
    }

    public int getNumChannels() {
        return powerDist.getNumChannels();
    }

    public boolean getSwitchableChannel() {
        return powerDist.getSwitchableChannel();
    }

    public void setSwitchableChannel(boolean enabled) {
        powerDist.setSwitchableChannel(enabled);
    }

    public PowerDistributionVersion getVersion() {
        return powerDist.getVersion();
    }

    public PowerDistributionFaults getFaults() {
        return powerDist.getFaults();
    }

    public PowerDistributionStickyFaults getStickyFaults() {
        return powerDist.getStickyFaults();
    }

    public PowerDistribution getRawPowerDistribution() {
        return powerDist;
    }


    public static class PDH extends PowerDistributionBoard {
        public PDH(CANPort port, int id) {
            super(port, id, ModuleType.REV);
        }

        public PDH(CANPort port) {
            super(port);
        }
    }

    public static class PDP extends PowerDistributionBoard {
        public PDP(CANPort port, int id) {
            super(port, id, ModuleType.CTRE);
        }

        public PDP(CANPort port) {
            super(port);
        }
    }
}