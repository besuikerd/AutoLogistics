package com.besuikerd.autologistics.common.tile.logistic.transferrable;

import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.StackValue;
import com.besuikerd.autologistics.common.tile.logistic.filter.ILogisticFilter;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractTransferrable<A extends StackValue, B extends StackValue> implements ITransferrable<A,B>{
    public List<IInventory> filterInventories(TileEntity tile, List<IInventory> inventories, ILogisticFilter filter){
        List<IInventory> filtered = new ArrayList<IInventory>();
        for(IInventory inventory : inventories){
            TileEntity invTile = (TileEntity) inventory;
            if(filter.passesBlockFilter(tile, invTile)){
                filtered.add(inventory);
            }
        }
        return filtered;
    }
}
