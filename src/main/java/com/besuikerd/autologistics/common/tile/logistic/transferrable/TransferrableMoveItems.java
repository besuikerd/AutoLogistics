package com.besuikerd.autologistics.common.tile.logistic.transferrable;

import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.*;
import com.besuikerd.autologistics.common.lib.util.MathUtil;
import com.besuikerd.autologistics.common.tile.logistic.LazyTileFinder;
import com.besuikerd.autologistics.common.tile.logistic.filter.ILogisticFilter;
import com.besuikerd.autologistics.common.tile.logistic.filter.LogisticFilterRegistry;
import com.besuikerd.autologistics.common.tile.logistic.itemcounter.InventoryItemCounterExtract;
import com.besuikerd.autologistics.common.tile.logistic.itemcounter.InventoryItemCounterInsert;
import com.besuikerd.autologistics.common.tile.traits.TileCable;
import com.google.common.collect.ImmutableSet;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TransferrableMoveItems implements ITransferrable<ObjectValue, ObjectValue>{
    public static final TransferrableMoveItems instance = new TransferrableMoveItems();

    @Override
    public StackValue transferTo(TileEntity source, ObjectValue from, ObjectValue to) {
        ILogisticFilter fromFilter;
        ILogisticFilter toFilter;
        if(
                (fromFilter = LogisticFilterRegistry.instance.getFilter(from)) != null
                        && (toFilter = LogisticFilterRegistry.instance.getFilter(to)) != null
                ){
            List<IInventory> allInventories = new LazyTileFinder<IInventory>(IInventory.class, TileCable.class, source).allValues();
            List<IInventory> toInventories = filterInventories(source, allInventories, toFilter);

            transferLoop:
            for(IInventory fromInventory : allInventories){
                TileEntity fromTile = (TileEntity) fromInventory;
                if(fromFilter.passesBlockFilter(source, fromTile)){
                    int fromItemsCounted = InventoryItemCounterExtract.instance.count(fromInventory, fromFilter);
                    int fromLimit = fromFilter.getAmount() == -1 ? Integer.MAX_VALUE : fromItemsCounted - fromFilter.getAmount();
                    if(fromLimit > 0){ // we are allowed to extract #fromLimit items


                        for(int fromSlotIndex = 0 ; fromSlotIndex < fromInventory.getSizeInventory() ; fromSlotIndex++){
                            ItemStack fromStack = fromInventory.getStackInSlot(fromSlotIndex);
                            if(fromStack != null && fromFilter.passesItemFilter(fromStack) && InventoryItemCounterExtract.instance.canUseSlot(fromInventory, fromSlotIndex, fromStack, fromFilter)){
                                boolean transferedItems = false;
                                for(IInventory toInventory : toInventories){ //let's find an inventory that we can put this stuff into
                                    int toItemsCounted = InventoryItemCounterInsert.instance.count(toInventory, toFilter);
                                    int toLimit = toFilter.getAmount() == -1 ? Integer.MAX_VALUE : toFilter.getAmount() - toItemsCounted;
                                    if(toLimit > 0) { //we are allowed to insert #fromLimit items

                                        toInventorySlotLoop:
                                        for(int toSlotIndex = 0 ; toSlotIndex < toInventory.getSizeInventory() ; toSlotIndex++){
                                            ItemStack toStack = toInventory.getStackInSlot(toSlotIndex);
                                            if((toStack == null || toFilter.passesItemFilter(toStack) && fromStack.isItemEqual(toStack) && ItemStack.areItemStackTagsEqual(fromStack, toStack)) && InventoryItemCounterInsert.instance.canUseSlot(toInventory, toSlotIndex, fromStack, toFilter)){
                                                if(toStack == null){ //empty slot; we can simply transfer the whole stack
                                                    int toTransfer = MathUtil.min(fromLimit, toLimit, fromStack.stackSize);
                                                    ItemStack splitStack = fromInventory.decrStackSize(fromSlotIndex, toTransfer);
                                                    toInventory.setInventorySlotContents(toSlotIndex, splitStack);

                                                    fromLimit -= toTransfer;
                                                    toLimit -= toTransfer;

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
                                                        transferedItems = true;
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

    private List<IInventory> filterInventories(TileEntity tile, List<IInventory> inventories, ILogisticFilter filter){
        List<IInventory> filtered = new ArrayList<IInventory>();
        for(IInventory inventory : inventories){
            TileEntity invTile = (TileEntity) inventory;
            if(filter.passesBlockFilter(tile, invTile)){
                filtered.add(inventory);
            }
        }
        return filtered;
    }

    public static class TransferrableProvider implements ITransferrableProvider<ObjectValue, ObjectValue>{
        public static final TransferrableProvider instance = new TransferrableProvider();

        public static final Set<String> validItemFilterTypes = ImmutableSet.of("item", "relative", "absolute", "name");

        //TODO generify this?
        public static final boolean isItemFilterType(StackValue value){
            ObjectValue obj;
            StringValue type;
            return
                   (obj = StackValues.tryExpectType(ObjectValue.class, value)) != null
                && ((type) = StackValues.tryExtractField(StringValue.class, "type", obj)) != null
                && validItemFilterTypes.contains(type.value);
        }

        @Override
        public ITransferrable<ObjectValue, ObjectValue> provide(StackValue from, StackValue to) {
            return isItemFilterType(from) && isItemFilterType(to) ? TransferrableMoveItems.instance : null;
        }
    }
}
