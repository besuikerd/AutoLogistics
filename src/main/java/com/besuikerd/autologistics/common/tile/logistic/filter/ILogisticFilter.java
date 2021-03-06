package com.besuikerd.autologistics.common.tile.logistic.filter;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public interface ILogisticFilter {
    int getAmount();
    EnumFacing[] getValidSides();

    boolean passesItemFilter(ItemStack stack);
    boolean passesBlockFilter(TileEntity from, TileEntity to);
}
