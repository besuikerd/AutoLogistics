package com.besuikerd.autologistics.common.lib.data;

import net.minecraft.nbt.NBTTagCompound;

public interface INBTDeserializable {
    void readFromNBT(NBTTagCompound compound);
}
