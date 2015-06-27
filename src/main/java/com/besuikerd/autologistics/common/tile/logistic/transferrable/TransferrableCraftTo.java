package com.besuikerd.autologistics.common.tile.logistic.transferrable;

import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.ObjectValue;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.StackValue;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.StackValues;
import net.minecraft.tileentity.TileEntity;

public class TransferrableCraftTo implements ITransferrable<ObjectValue, ObjectValue>{
    public static TransferrableCraftTo instance = new TransferrableCraftTo();

    @Override
    public StackValue transferTo(TileEntity source, ObjectValue from, ObjectValue to) {
        return null;
    }

    public static class TransferrableProvider implements ITransferrableProvider<ObjectValue, ObjectValue>{
        public static final TransferrableProvider instance = new TransferrableProvider();

        @Override
        public ITransferrable<ObjectValue, ObjectValue> provide(StackValue from, StackValue to) {
            return
                   StackValues.tryGetObjectOfType("craftFrom", from) != null
                && TransferrableMoveItems.TransferrableProvider.isItemFilterType(to)
                ? TransferrableCraftTo.instance : null;
        }
    }
}
