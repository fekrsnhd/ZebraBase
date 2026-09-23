package first.lib.hardware.encoders.absolute;

import first.lib.hardware.encoders.Encoder;
import first.lib.hardware.encoders.EncoderConfig;

public abstract class AbsoluteEncoder extends Encoder {
    
    protected AbsoluteEncoder(EncoderConfig cfg) {
        super(cfg);
    }

}