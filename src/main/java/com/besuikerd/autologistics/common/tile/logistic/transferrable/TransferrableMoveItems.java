package com.besuikerd.autologistics.common.tile.logistic.transferrable;

import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.*;
import com.besuikerd.autologistics.common.lib.util.MathUtil;
import com.besuikerd.autologistics.common.tile.logistic.LazyTileFinder;
import com.besuikerd.autologistics.common.tile.logistic.filter.ILogisticFilter;
import com.besuikerd.autologistics.common.tile.logistic.filter.LogisticFilterRegistry;
import com.besuikerd.autologistics.common.tile.logistic.itemcounter.InventoryItemCounterExtract;
import com.besuikerd.autologistics.common.tile.logistic.itemcounter.InventoryItemCounterInsert;
import com.besuikerd.autologistics.common.tile.traits.TileCable;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import java.util.*;

public class TransferrableMoveItems extends AbstractTransferrable<StackValue, StackValue>{

    protected ILogisticFilter[] fromFilters;
    protected ILogisticFilter[] toFilters;

    public TransferrableMoveItems(ILogisticFilter[] fromFilters, ILogisticFilter[] toFilters) {
        this.fromFilters = fromFilters;
        this.toFilters = toFilters;
    }

    @Override
    public StackValue transferTo(TileEntity source, StackValue from, StackValue to) {
        List<IInventory> allInventories = new LazyTileFinder<IInventory>(IInventory.class, TileCable.class, source).allValues();

        for(ILogisticFilter fromFilter : fromFilters){
            for(ILogisticFilter toFilter : toFilters){
                int transferred = tryTransferTo(source, allInventories, fromFilter, toFilter);
                if(transferred > 0){
                    return resultValue(transferred);
                }
            }
        }
        return resultValue(0);
    }

    public int tryTransferTo(TileEntity source, List<IInventory> allInventories, ILogisticFilter fromFilter, ILogisticFilter toFilter){
        List<IInventory> toInventories = filterInventories(source, allInventories, toFilter);

        int amountTransferred = 0;

        transferLoop:
        for(IInventory fromInventory : allInventories){
            TileEntity fromTile = (TileEntity) fromInventory;
            if(fromFilter.passesBlockFilter(source, fromTile)){
                int fromItemsCounted = InventoryItemCounterExtract.instance.count(fromInventory, fromFilter);
                int fromLimit = fromFilter.getAmount() == -1 ? Integer.MAX_VALUE : fromItemsCounted - fromFilter.getAmount();
                if(fromLimit > 0){ // we are allowed to extract #fromLimit items


                    for(int fromSlotIndex = 0 ; fromSlotIndex < fromInventory.getSizeInventory() ; fromSlotIndex++){
                        ItemStack fromStack = fromInventory.getStackInSlot(fromSlotIndex);

                        if(fromStack != null && fromStack.stackSize > 0 && fromFilter.passesItemFilter(fromStack) && InventoryItemCounterExtract.instance.canUseSlot(fromInventory, fromSlotIndex, fromStack, fromFilter)){

                            for(IInventory toInventory : toInventories){ //let's find an inventory that we can put this stuff into
                                int toItemsCounted = InventoryItemCounterInsert.instance.count(toInventory, toFilter);
                                int toLimit = toFilter.getAmount() == -1 ? Integer.MAX_VALUE : toFilter.getAmount() - toItemsCounted;
                                if(toLimit > 0) { //we are allowed to insert #fromLimit items

                                    toInventorySlotLoop:
                                    for(int toSlotIndex = 0 ; toSlotIndex < toInventory.getSizeInventory() ; toSlotIndex++){
                                        ItemStack toStack = toInventory.getStackInSlot(toSlotIndex);
                                        if((toStack == null || fromStack.isItemEqual(toStack) && ItemStack.areItemStackTagsEqual(fromStack, toStack)) && toFilter.passesItemFilter(fromStack) && InventoryItemCounterInsert.instance.canUseSlot(toInventory, toSlotIndex, fromStack, toFilter)){
                                            if(toStack == null){ //empty slot; we can simply transfer the whole stack
                                                int toTransfer = MathUtil.min(fromLimit, toLimit, fromStack.stackSize);
                                                ItemStack splitStack = fromInventory.decrStackSize(fromSlotIndex, toTransfer);
                                                toInventory.setInventorySlotContents(toSlotIndex, splitStack);

                                                amountTransferred += toTransfer;

                                                break transferLoop;
                                            } else{
                                                int toTransfer = MathUtil.min(fromLimit, toLimit, fromStack.stackSize, toStack.getMaxStackSize() - toStack.stackSize);
                                                ItemStack splitStack = fromInventory.decrStackSize(fromSlotIndex, toTransfer);
                                                ItemStack mergeStack = toInventory.getStackInSlot(toSlotIndex);
                                                mergeStack.stackSize += splitStack.stackSize;
                                                toInventory.setInventorySlotContents(toSlotIndex, mergeStack);
                                                if(fromInventory.getStackInSlot(fromSlotIndex) == null){ //we transferred the whole fromStack!
                                                    break transferLoop;
                                                } else{
                                                    amountTransferred += toTransfer;
                                                }

                                                fromLimit -= toTransfer;
                                                toLimit -= toTransfer;
                                            }

                                            if(fromLimit == 0){ //we cannot transfer more, so we are done!
                                                break transferLoop;
                                            }

                                            if(toLimit == 0){ //we cannot tranfer any more items into this inventory
                                                break toInventorySlotLoop;
                                            }
                                        }
                                    }
                                }
                            }
                            if(amountTransferred > 0){ //we transferred part of the fromStack!
                                break transferLoop;
                            }
                        }
                    }
                }
            }
        }
        return amountTransferred;
    }


    public StackValue resultValue(int amountTransferred){
        ObjectValue result = new ObjectValue();
        result.put("success", amountTransferred > 0 ? BooleanValue.trueValue : BooleanValue.falseValue);
        result.put("amount", amountTransferred == 0 ? IntegerValue.zeroValue : new IntegerValue(amountTransferred));
        return result;
    }



    public static class TransferrableProvider implements ITransferrableProvider<StackValue, StackValue>{
        public static final TransferrableProvider instance = new TransferrableProvider();

        @Override
        public ITransferrable<StackValue, StackValue> provide(StackValue from, StackValue to) {
            ILogisticFilter[] fromFilters;
            ILogisticFilter[] toFilters;
            if(
                   (fromFilters = LogisticFilterRegistry.instance.tryGetFilterOrFilterList(from)) == null
                || (toFilters = LogisticFilterRegistry.instance.tryGetFilterOrFilterList(to)) == null
            ){
                return null;
            }

            return new TransferrableMoveItems(fromFilters, toFilters);
        }
    }
}
