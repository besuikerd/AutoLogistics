package com.besuikerd.autologistics.common.tile.logistic;

import com.besuikerd.autologistics.common.lib.dsl.vm.VirtualMachine;
import com.besuikerd.autologistics.common.lib.dsl.vm.nativefunction.AbstractNativeFunction;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.*;
import com.besuikerd.autologistics.common.lib.util.MathUtil;
import com.besuikerd.autologistics.common.tile.logistic.filter.*;
import com.besuikerd.autologistics.common.tile.logistic.itemcounter.InventoryItemCounterExtract;
import com.besuikerd.autologistics.common.tile.logistic.itemcounter.InventoryItemCounterInsert;
import com.besuikerd.autologistics.common.tile.logistic.transferrable.*;
import com.besuikerd.autologistics.common.tile.traits.TileCable;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;
import java.util.List;

public class NativeFunctionItemTransfer extends AbstractNativeFunction {

    private TileEntity tile;

    static{
        LogisticFilterRegistry.instance.register(LogisticFilterItem.FilterProvider.instance);
        LogisticFilterRegistry.instance.register(LogisticFilterName.FilterProvider.instance);
        LogisticFilterRegistry.instance.register(LogisticFilterPosition.FilterProvider.instance);

        TransferrableRegistry.instance.register(TransferrableMoveItems.TransferrableProvider.instance);
        TransferrableRegistry.instance.register(TransferrableCraftFrom.TransferrableProvider.instance);
        TransferrableRegistry.instance.register(TransferrableCraftTo.TransferrableProvider.instance);
    }

    public NativeFunctionItemTransfer(TileEntity tile) {
        this.tile = tile;
    }

    @Override
    public StackValue call(VirtualMachine vm, List<StackValue> args) {
        if(ensureLength(vm, args, 2)){
            StackValue valueFrom = args.get(0);
            StackValue valueTo = args.get(1);

            ITransferrable<StackValue, StackValue> transferrable = TransferrableRegistry.instance.getTransferrable(valueFrom, valueTo);
            if(transferrable != null){
                return transferrable.transferTo(tile, valueFrom, valueTo);
            }
        }
        return NilValue.instance;
    }
}
