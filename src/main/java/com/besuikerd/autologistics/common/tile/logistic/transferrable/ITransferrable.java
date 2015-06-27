package com.besuikerd.autologistics.common.tile.logistic.transferrable;

import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.StackValue;
import net.minecraft.tileentity.TileEntity;

public interface ITransferrable<A extends StackValue, B extends StackValue> {
    StackValue transferTo(TileEntity source, A from, B to);
}