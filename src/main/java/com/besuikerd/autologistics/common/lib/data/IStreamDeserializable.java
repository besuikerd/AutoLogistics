package com.besuikerd.autologistics.common.lib.data;

import java.io.DataInput;
import java.io.IOException;

public interface IStreamDeserializable {
    void deserialize(DataInput stream) throws IOException;
}
