package com.besuikerd.autologistics.common.tile.logistic;

import com.besuikerd.autologistics.common.lib.dsl.vm.VirtualMachine;
import com.besuikerd.autologistics.common.lib.dsl.vm.nativefunction.AbstractNativeFunction;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.*;
import com.besuikerd.autologistics.common.lib.util.MathUtil;
import com.besuikerd.autologistics.common.tile.logistic.filter.LogisticFilter;
import com.besuikerd.autologistics.common.tile.logistic.filter.LogisticFilterItem;
import com.besuikerd.autologistics.common.tile.logistic.filter.LogisticFilterPosition;
import com.besuikerd.autologistics.common.tile.logistic.itemcounter.InventoryItemCounterExtract;
import com.besuikerd.autologistics.common.tile.logistic.itemcounter.InventoryItemCounterInsert;
import com.besuikerd.autologistics.common.tile.traits.TileCable;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;

import java.util.ArrayList;
import java.util.List;

public class NativeFunctionItemTransfer extends AbstractNativeFunction {

    private TileEntity tile;

    public NativeFunctionItemTransfer(TileEntity tile) {
        this.tile = tile;
    }

    @Override
    public StackValue call(VirtualMachine vm, List<StackValue> args) {
        if(ensureLength(vm, args, 2)){
            StackValue valueFrom = args.get(0);
            StackValue valueTo = args.get(1);

            ObjectValue from;
            ObjectValue to;
            if(
                   (from = StackValues.tryExpectType(ObjectValue.class, valueFrom)) != null
                && (to = StackValues.tryExpectType(ObjectValue.class, valueTo)) != null
            ){
                return transferTo(from, to);
            }
        }
        return NilValue.instance;
    }

    public StackValue transferTo(ObjectValue from, ObjectValue to){

        LogisticFilter fromFilter;
        LogisticFilter toFilter;
        if(
               (fromFilter = getFilter(from)) != null
            && (toFilter = getFilter(to)) != null
        ){
            List<IInventory> allInventories = new LazyTileFinder<IInventory>(IInventory.class, TileCable.class, tile).allValues();
            List<IInventory> toInventories = filterInventories(allInventories, toFilter);

            for(IInventory fromInventory : allInventories){
                TileEntity fromTile = (TileEntity) fromInventory;
                if(fromFilter.passesBlockFilter(fromTile)){
                    int fromLimit = fromFilter.getAmount() == -1 ? Integer.MAX_VALUE : fromFilter.getAmount() - InventoryItemCounterExtract.instance.count(fromInventory, fromFilter);
                    if(fromLimit > 0){ // we are allowed to extract #fromLimit items

                        transferLoop:
                        for(int fromSlotIndex = 0 ; fromSlotIndex < fromInventory.getSizeInventory() ; fromSlotIndex++){
                            ItemStack fromStack = fromInventory.getStackInSlot(fromSlotIndex);
                            if(fromStack != null && InventoryItemCounterExtract.instance.canUseSlot(fromInventory, fromSlotIndex, fromStack, fromFilter)){
                                boolean transferedItems = false;
                                for(IInventory toInventory : toInventories){ //let's find an inventory that we can put this stuff into
                                    int toLimit = toFilter.getAmount() == -1 ? Integer.MAX_VALUE : fromFilter.getAmount() - InventoryItemCounterInsert.instance.count(toInventory, toFilter);
                                    if(toLimit > 0) { //we are allowed to insert #fromLimit items

                                        for(int toSlotIndex = 0 ; toSlotIndex < toInventory.getSizeInventory() ; toSlotIndex++){
                                            ItemStack toStack = toInventory.getStackInSlot(toSlotIndex);
                                            if((toStack == null || fromStack.isItemEqual(toStack) && ItemStack.areItemStackTagsEqual(fromStack, toStack)) && InventoryItemCounterInsert.instance.canUseSlot(toInventory, toSlotIndex, fromStack, toFilter)){
                                                if(toStack == null){ //empty slot; we can simply transfer the whole stack
                                                    fromInventory.setInventorySlotContents(fromSlotIndex, null);
                                                    toInventory.setInventorySlotContents(toSlotIndex, fromStack);
                                                    break transferLoop;
                                                } else{
                                                    int toTransfer = MathUtil.min(fromLimit, toLimit, fromStack.stackSize, toStack.getMaxStackSize() - toStack.stackSize);
                                                    fromInventory.decrStackSize(fromSlotIndex, toTransfer);
                                                    toInventory.decrStackSize(toSlotIndex, -toTransfer);
                                                    if(fromInventory.getStackInSlot(fromSlotIndex) == null){ //we transferred the whole fromStack!
                                                        break transferLoop;
                                                    } else{
                                                        transferedItems = true;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                if(transferedItems){ //we transferred part of the fromStack!
                                    break transferLoop;
                                }
                            }
                        }
                    }
                }
            }
        }
        return NilValue.instance;
    }

    private List<IInventory> filterInventories(List<IInventory> inventories, LogisticFilter filter){
        List<IInventory> filtered = new ArrayList<IInventory>();
        for(IInventory inventory : inventories){
            TileEntity tile = (TileEntity) inventory;
            if(filter.passesBlockFilter(tile)){
                filtered.add(inventory);
            }
        }
        return filtered;
    }

    private LogisticFilter getFilter(ObjectValue obj){
        LogisticFilter filter;
        if(
               (filter = LogisticFilterItem.fromObjectValue(obj)) != null
            || (filter = LogisticFilterPosition.fromObjectValue(obj)) != null
        ){
            filter = null;
        }
        return filter;
    }
}
