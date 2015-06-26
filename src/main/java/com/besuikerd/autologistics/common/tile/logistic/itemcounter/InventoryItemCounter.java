package com.besuikerd.autologistics.common.tile.logistic.itemcounter;

import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.ObjectValue;
import com.besuikerd.autologistics.common.tile.logistic.filter.LogisticFilter;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public interface InventoryItemCounter {
    int count(IInventory inventory, LogisticFilter filter);
    boolean canUseSlot(IInventory inventory, int slotIndex, ItemStack stack, LogisticFilter filter);
}
