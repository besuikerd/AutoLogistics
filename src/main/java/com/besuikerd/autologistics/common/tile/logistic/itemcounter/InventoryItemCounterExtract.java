package com.besuikerd.autologistics.common.tile.logistic.itemcounter;

import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.ObjectValue;
import com.besuikerd.autologistics.common.tile.logistic.filter.LogisticFilter;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

public class InventoryItemCounterExtract extends AbstractInventoryItemCounter{
    public static final InventoryItemCounterExtract instance = new InventoryItemCounterExtract();

    @Override
    protected boolean canCountSided(ISidedInventory inventory, int slotIndex, ItemStack stack, EnumFacing side) {
        return inventory.canExtractItem(slotIndex, stack, side.ordinal());
    }
}
