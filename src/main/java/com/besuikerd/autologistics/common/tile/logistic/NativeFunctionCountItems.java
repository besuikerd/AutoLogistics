package com.besuikerd.autologistics.common.tile.logistic;

import com.besuikerd.autologistics.common.lib.dsl.vm.VirtualMachine;
import com.besuikerd.autologistics.common.lib.dsl.vm.nativefunction.AbstractNativeFunction;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.IntegerValue;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.StackValue;
import com.besuikerd.autologistics.common.tile.logistic.filter.ILogisticFilter;
import com.besuikerd.autologistics.common.tile.logistic.filter.LogisticFilterRegistry;
import com.besuikerd.autologistics.common.tile.logistic.itemcounter.SimpleInventoryItemCounter;
import com.besuikerd.autologistics.common.tile.traits.TileCable;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NativeFunctionCountItems extends AbstractNativeFunction{
    private TileEntity tile;

    public NativeFunctionCountItems(TileEntity tile) {
        this.tile = tile;
    }

    @Override
    public StackValue call(VirtualMachine vm, List<StackValue> args) {
        if(ensureLength(vm, args, 1)){
            ILogisticFilter[] filters = LogisticFilterRegistry.instance.tryGetFilterOrFilterList(args.get(0));
            if(filters == null){
                return IntegerValue.zeroValue;
            } else{
                Iterator<IInventory> allInventories = new LazyTileFinder<IInventory>(IInventory.class, TileCable.class, tile);
                int count = 0;
                List<ILogisticFilter> validFilters = new ArrayList<ILogisticFilter>(filters.length);
                while(allInventories.hasNext()){
                    IInventory inventory = allInventories.next();
                    TileEntity inventoryTile = (TileEntity) inventory;
                    validFilters.clear();
                    for(ILogisticFilter filter : filters){
                        if(filter.passesBlockFilter(tile, inventoryTile)){
                            validFilters.add(filter);
                        }
                    }

                    for(int slotIndex = 0 ; slotIndex < inventory.getSizeInventory(); slotIndex++){
                        ItemStack stack = inventory.getStackInSlot(slotIndex);
                        if(stack != null){
                            for(ILogisticFilter filter : validFilters){
                                if(filter.passesItemFilter(stack)){
                                    count += stack.stackSize;
                                    break;
                                }
                            }
                        }
                    }
                }
                return new IntegerValue(count);
            }
        }
        return IntegerValue.zeroValue;
    }



}
