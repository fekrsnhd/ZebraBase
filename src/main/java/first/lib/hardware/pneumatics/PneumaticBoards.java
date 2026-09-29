package first.lib.hardware.pneumatics;

import org.wpilib.hardware.bus.CANPort;
import org.wpilib.hardware.pneumatic.PneumaticsModuleType;

/**
 * Represents a pneumatic control board and its connection details.
 */
public class PneumaticBoards {
    private final int id;
    private final PneumaticsModuleType type;
    private final CANPort port;

    /**
     * Constructs a generic pneumatic board representation.
     * @param port The CAN port the board is connected to.
     * @param id The CAN ID of the board.
     * @param type The specific type of the pneumatics module.
     */
    private PneumaticBoards(CANPort port, int id, PneumaticsModuleType type) {
        this.port = port;
        this.id = id;
        this.type = type;
    }

    /**
     * Retrieves the CAN ID of the pneumatic board.
     * @return The CAN ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Retrieves the hardware type of the pneumatic board.
     * @return The module type.
     */
    public PneumaticsModuleType getType() {
        return type;
    }

    /**
     * Retrieves the CAN port the board is operating on.
     * @return The CANPort object.
     */
    public CANPort getPort() {
        return port;
    }

    /**
     * Implementation for a REV Robotics Pneumatic Hub (PH).
     */
    public static class PH extends PneumaticBoards {
        /**
         * Constructs a REV Pneumatic Hub definition.
         * @param port The CAN port connection.
         * @param id The CAN ID for this PH.
         */
        public PH(CANPort port, int id) {
            super(port, id, PneumaticsModuleType.REV_PH);
        }
    }

    /**
     * Implementation for a CTRE Pneumatics Control Module (PCM).
     */
    public static class PCM extends PneumaticBoards {
        /**
         * Constructs a CTRE Pneumatics Control Module definition.
         * @param port The CAN port connection.
         * @param id The CAN ID for this PCM.
         */
        public PCM(CANPort port, int id) {
            super(port, id, PneumaticsModuleType.CTRE_PCM);
        }
    }
}