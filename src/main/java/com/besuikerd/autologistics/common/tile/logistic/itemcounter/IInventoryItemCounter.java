package com.besuikerd.autologistics.common.tile.logistic.itemcounter;

import com.besuikerd.autologistics.common.tile.logistic.filter.ILogisticFilter;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public interface IInventoryItemCounter {
    int count(IInventory inventory, ILogisticFilter filter);
    boolean canUseSlot(IInventory inventory, int slotIndex, ItemStack stack, ILogisticFilter filter);
}
