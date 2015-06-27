package com.besuikerd.autologistics.common.tile.logistic.transferrable;

import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.*;
import net.minecraft.tileentity.TileEntity;

public class TransferrableCraftFrom implements ITransferrable<ObjectValue, ListValue>{
    public static final TransferrableCraftFrom instance = new TransferrableCraftFrom();

    @Override
    public StackValue transferTo(TileEntity source, ObjectValue from, ListValue to) {
        ObjectValue recipe = new ObjectValue();
        recipe.put("type", new StringValue("craftFrom"));
        recipe.put("input", from);
        recipe.put("recipe", to);
        return recipe;
    }

    public static class TransferrableProvider implements ITransferrableProvider<ObjectValue, ListValue>{
        public static final TransferrableProvider instance = new TransferrableProvider();

        @Override
        public ITransferrable<ObjectValue, ListValue> provide(StackValue from, StackValue to) {
            ObjectValue obj;
            ListValue recipe;
            if(
                    (obj = StackValues.tryExpectType(ObjectValue.class, from)) != null && TransferrableMoveItems.TransferrableProvider.isItemFilterType(obj)
                    && (recipe = StackValues.tryExpectType(ListValue.class, to)) != null
            ){
                for(StackValue value : recipe.value){
                    if(!TransferrableMoveItems.TransferrableProvider.isItemFilterType(value)){
                        return null;
                    }
                }
                return TransferrableCraftFrom.instance;
            }
            return null;
        }
    }

}
