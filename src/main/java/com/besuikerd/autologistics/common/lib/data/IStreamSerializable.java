package com.besuikerd.autologistics.common.lib.data;

import java.io.DataOutput;
import java.io.IOException;

public interface IStreamSerializable {
    void serialize(DataOutput stream) throws IOException;
}