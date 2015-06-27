package com.besuikerd.autologistics.common.tile.logistic.itemcounter;

import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

public class InventoryItemCounterInsert extends AbstractInventoryItemCounter{
    public static final InventoryItemCounterInsert instance = new InventoryItemCounterInsert();

    @Override
    protected boolean canCountSided(ISidedInventory inventory, int slotIndex, ItemStack stack, EnumFacing side) {
        return inventory.canInsertItem(slotIndex, stack, side.ordinal());
    }
}
