package first.lib.hardware.powerDistribution;

import org.wpilib.hardware.bus.CANPort;
import org.wpilib.hardware.power.PowerDistribution;
import org.wpilib.hardware.power.PowerDistribution.ModuleType;
import org.wpilib.hardware.hal.PowerDistributionFaults;
import org.wpilib.hardware.hal.PowerDistributionStickyFaults;
import org.wpilib.hardware.hal.PowerDistributionVersion;

/**
 * Universal wrapper for power distribution modules.
 */
public class PowerDistributionBoard {
    private final PowerDistribution powerDist;

    /**
     * Constructs a PowerDistributionBoard with a specific ID and type.
     * @param port The CAN port.
     * @param id The CAN ID.
     * @param type The type of module (REV or CTRE).
     */
    private PowerDistributionBoard(CANPort port, int id, ModuleType type) {
        powerDist = new PowerDistribution(port, id, type);
    }

    /**
     * Constructs a PowerDistributionBoard using default assignments on the given port.
     * @param port The CAN port.
     */
    private PowerDistributionBoard(CANPort port) {
        powerDist = new PowerDistribution(port);
    }

    /**
     * Retrieves the input voltage to the power distribution module.
     * @return The voltage in Volts.
     */
    public double getVoltage() {
        return powerDist.getVoltage();
    }

    /**
     * Retrieves the temperature of the power distribution module.
     * @return The temperature in Celsius.
     */
    public double getTemperature() {
        return powerDist.getTemperature();
    }

    /**
     * Retrieves the current draw on a specific channel.
     * @param channel The channel to query.
     * @return The current in Amps.
     */
    public double getCurrent(int channel) {
        return powerDist.getCurrent(channel);
    }

    /**
     * Retrieves the current draw on all available channels.
     * @return An array of currents in Amps.
     */
    public double[] getAllCurrents() {
        return powerDist.getAllCurrents();
    }

    /**
     * Retrieves the total current draw from all channels.
     * @return The total current in Amps.
     */
    public double getTotalCurrent() {
        return powerDist.getTotalCurrent();
    }

    /**
     * Retrieves the total power drawn from the module.
     * @return The total power in Watts.
     */
    public double getTotalPower() {
        return powerDist.getTotalPower();
    }

    /**
     * Retrieves the total energy consumed by the module since boot or reset.
     * @return Total energy in Joules.
     */
    public double getTotalEnergy() {
        return powerDist.getTotalEnergy();
    }

    /**
     * Resets the total energy counter to zero.
     */
    public void resetTotalEnergy() {
        powerDist.resetTotalEnergy();
    }

    /**
     * Clears all sticky faults registered on the module.
     */
    public void clearStickyFaults() {
        powerDist.clearStickyFaults();
    }

    /**
     * Retrieves the CAN ID of the module.
     * @return The module ID.
     */
    public int getModule() {
        return powerDist.getModule();
    }

    /**
     * Retrieves the hardware type of the module.
     * @return The ModuleType (REV or CTRE).
     */
    public ModuleType getType() {
        return powerDist.getType();
    }

    /**
     * Retrieves the number of current-monitoring channels available.
     * @return The number of channels.
     */
    public int getNumChannels() {
        return powerDist.getNumChannels();
    }

    /**
     * Checks if the switchable channel is currently enabled.
     * @return True if enabled, false otherwise.
     */
    public boolean getSwitchableChannel() {
        return powerDist.getSwitchableChannel();
    }

    /**
     * Sets the state of the switchable channel (if the hardware supports it).
     * @param enabled True to enable, false to disable.
     */
    public void setSwitchableChannel(boolean enabled) {
        powerDist.setSwitchableChannel(enabled);
    }

    /**
     * Retrieves the firmware/hardware version of the module.
     * @return The PowerDistributionVersion data.
     */
    public PowerDistributionVersion getVersion() {
        return powerDist.getVersion();
    }

    /**
     * Retrieves the current active faults on the module.
     * @return The PowerDistributionFaults data.
     */
    public PowerDistributionFaults getFaults() {
        return powerDist.getFaults();
    }

    /**
     * Retrieves the historical sticky faults on the module.
     * @return The PowerDistributionStickyFaults data.
     */
    public PowerDistributionStickyFaults getStickyFaults() {
        return powerDist.getStickyFaults();
    }

    /**
     * Retrieves the underlying WPILib PowerDistribution object.
     * @return The raw PowerDistribution instance.
     */
    public PowerDistribution getRawPowerDistribution() {
        return powerDist;
    }

    /**
     * Implementation for a REV Power Distribution Hub (PDH).
     */
    public static class PDH extends PowerDistributionBoard {
        public PDH(CANPort port, int id) {
            super(port, id, ModuleType.REV);
        }

        public PDH(CANPort port) {
            super(port);
        }
    }

    /**
     * Implementation for a CTRE Power Distribution Panel (PDP).
     */
    public static class PDP extends PowerDistributionBoard {
        public PDP(CANPort port, int id) {
            super(port, id, ModuleType.CTRE);
        }

        public PDP(CANPort port) {
            super(port);
        }
    }
}