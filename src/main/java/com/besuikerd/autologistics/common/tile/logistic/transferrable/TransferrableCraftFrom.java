package com.besuikerd.autologistics.common.tile.logistic.transferrable;

import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.*;
import com.besuikerd.autologistics.common.tile.logistic.filter.LogisticFilterRegistry;
import net.minecraft.tileentity.TileEntity;

public class TransferrableCraftFrom implements ITransferrable<StackValue, ListValue>{
    public static final TransferrableCraftFrom instance = new TransferrableCraftFrom();

    @Override
    public StackValue transferTo(TileEntity source, StackValue from, ListValue to) {
        ObjectValue recipe = new ObjectValue();
        recipe.put("type", new StringValue("craftFrom"));
        recipe.put("input", from);
        recipe.put("recipe", to);
        return recipe;
    }

    public static class TransferrableProvider implements ITransferrableProvider<StackValue, ListValue>{
        public static final TransferrableProvider instance = new TransferrableProvider();

        @Override
        public ITransferrable<StackValue, ListValue> provide(StackValue from, StackValue to) {
            if(!LogisticFilterRegistry.instance.filterExists(from) && LogisticFilterRegistry.instance.tryGetFilterList(from) == null){
                return null;
            }

            ListValue recipe;
            if((recipe = StackValues.tryExpectType(ListValue.class, to)) != null){
                for(StackValue value : recipe.value){
                    ListValue row;
                    if((row = StackValues.tryExpectType(ListValue.class, value)) == null){
                        return null;
                    } else{
                        for(StackValue elem : row.value){
                            if(!LogisticFilterRegistry.instance.filterExists(elem)){
                                return null;
                            }
                        }
                    }
                }

            }
            return TransferrableCraftFrom.instance;
        }
    }

}
