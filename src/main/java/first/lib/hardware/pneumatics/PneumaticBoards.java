package first.lib.hardware.pneumatics;

import org.wpilib.hardware.bus.CANPort;
import org.wpilib.hardware.pneumatic.PneumaticsModuleType;

public class PneumaticBoards {
    private final int id;
    private final PneumaticsModuleType type;
    private final CANPort port;

    private PneumaticBoards(CANPort port, int id, PneumaticsModuleType type) {
        this.port = port;
        this.id = id;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public PneumaticsModuleType getType() {
        return type;
    }

    public CANPort getPort() {
        return port;
    }

    public static class PH extends PneumaticBoards {
        public PH(CANPort port, int id) {
            super(port, id, PneumaticsModuleType.REV_PH);
        }
    }

    public static class PCM extends PneumaticBoards {
        public PCM(CANPort port, int id) {
            super(port, id, PneumaticsModuleType.CTRE_PCM);
        }
    }
}