package com.besuikerd.autologistics.common.tile.logistic.filter.item;

import com.besuikerd.autologistics.common.lib.util.ItemStackUtils;
import com.besuikerd.autologistics.common.tile.logistic.filter.ILogisticFilter;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public class LogisticFilterItemStack implements ILogisticFilter{
    private ItemStack stack;
    private int amount;
    private EnumFacing[] validSides;

    public LogisticFilterItemStack(ItemStack stack, int amount, EnumFacing[] validSides) {
        this.stack = stack;
        this.amount = amount;
        this.validSides = validSides;
    }

    public LogisticFilterItemStack(ItemStack stack){
        this(stack, -1, EnumFacing.values());
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public EnumFacing[] getValidSides() {
        return validSides;
    }

    @Override
    public boolean passesItemFilter(ItemStack stack) {
        return ItemStackUtils.areStackable(stack, this.stack);
    }

    @Override
    public boolean passesBlockFilter(TileEntity from, TileEntity to) {
        return false;
    }
}
