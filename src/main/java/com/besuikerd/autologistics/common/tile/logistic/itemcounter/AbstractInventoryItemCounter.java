package com.besuikerd.autologistics.common.tile.logistic.itemcounter;

import com.besuikerd.autologistics.common.lib.util.ArrayUtil;
import com.besuikerd.autologistics.common.tile.logistic.filter.ILogisticFilter;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

public abstract class AbstractInventoryItemCounter implements IInventoryItemCounter {

    @Override
    public int count(IInventory inventory, ILogisticFilter filter) {
        int count = 0;
        if(inventory instanceof ISidedInventory){
            ISidedInventory sided = (ISidedInventory) inventory;
            for(EnumFacing facing : filter.getValidSides()){
                for(int slotIndex : sided.getAccessibleSlotsFromSide(facing.ordinal())){
                    ItemStack stack = inventory.getStackInSlot(slotIndex);
                    if(stack != null && passesItemFilter(filter, stack) && canCountSided(sided, slotIndex, stack, facing)){
                        count += stack.stackSize;
                    }
                }
            }
        } else{
            for(int slotIndex = 0 ; slotIndex < inventory.getSizeInventory() ; slotIndex++){
                ItemStack stack = inventory.getStackInSlot(slotIndex);
                if(stack != null && passesItemFilter(filter, stack)){
                    count += stack.stackSize;
                }
            }
        }
        return count;
    }

    @Override
    public boolean canUseSlot(IInventory inventory, int slotIndex, ItemStack stack, ILogisticFilter filter) {
        if(inventory instanceof ISidedInventory){
            ISidedInventory sided = (ISidedInventory) inventory;
            for(EnumFacing side : filter.getValidSides()){
                if(ArrayUtil.contains(sided.getAccessibleSlotsFromSide(side.ordinal()), slotIndex) && canCountSided(sided, slotIndex, stack, side)){
                    return true;
                }
            }
            return false;
        } else{
            return true;
        }
    }

    protected boolean passesItemFilter(ILogisticFilter filter, ItemStack stack){
        return filter.passesItemFilter(stack);
    }

    protected abstract boolean canCountSided(ISidedInventory inventory, int slotIndex, ItemStack stack, EnumFacing side);
}
