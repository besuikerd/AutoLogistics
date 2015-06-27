package com.besuikerd.autologistics.common.tile.logistic.itemcounter;

import com.besuikerd.autologistics.common.tile.logistic.filter.ILogisticFilter;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

public class ItemCounterInnerLogisticFilterInsert extends ItemCounterInnerLogisticFilter {
    public ItemCounterInnerLogisticFilterInsert(ILogisticFilter... filters) {
        super(filters);
    }

    @Override
    protected boolean canCountSided(ISidedInventory inventory, int slotIndex, ItemStack stack, EnumFacing side) {
        return inventory.canInsertItem(slotIndex, stack, side.ordinal());
    }
}
