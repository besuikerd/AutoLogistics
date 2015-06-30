package com.besuikerd.autologistics.common.lib.data;

import net.minecraft.nbt.NBTTagCompound;

public interface INBTSerializable {
    void writeToNBT(NBTTagCompound compound);
}
