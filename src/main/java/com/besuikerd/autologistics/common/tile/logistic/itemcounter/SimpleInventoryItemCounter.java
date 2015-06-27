package com.besuikerd.autologistics.common.tile.logistic.itemcounter;

import com.besuikerd.autologistics.common.tile.logistic.filter.ILogisticFilter;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class SimpleInventoryItemCounter implements IInventoryItemCounter{
    public static final SimpleInventoryItemCounter instance = new SimpleInventoryItemCounter();

    @Override
    public int count(IInventory inventory, ILogisticFilter filter) {
        int count = 0;
        for(int slotIndex = 0 ; slotIndex < inventory.getSizeInventory() ; slotIndex++){
            ItemStack stack = inventory.getStackInSlot(slotIndex);
            if(stack != null && filter.passesItemFilter(stack)){
                count += stack.stackSize;
            }
        }
        return count;
    }

    @Override
    public boolean canUseSlot(IInventory inventory, int slotIndex, ItemStack stack, ILogisticFilter filter) {
        return true;
    }
}
