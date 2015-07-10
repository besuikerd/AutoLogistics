package com.besuikerd.autologistics.common.tile.logistic.filter.item;

import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.BooleanValue;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.ObjectValue;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.StackValue;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.StackValues;
import net.minecraft.item.ItemStack;

public abstract class AbstractItemFilter implements IItemFilter{
    protected boolean inverted;

    public AbstractItemFilter(StackValue value){
        extractAttributes(value);
    }

    protected void extractAttributes(StackValue value){
        boolean inverted = false;
        ObjectValue obj;
        if((obj = StackValues.tryExpectType(ObjectValue.class, value)) != null){
            BooleanValue optInverted;
            if((optInverted = StackValues.tryExtractField(BooleanValue.class, "inverted", obj)) != null){
                inverted = optInverted.value;
            }
        }
        this.inverted = inverted;
    }

    @Override
    public final boolean passesFilter(ItemStack stack) {
        boolean passesFilter = passesFilterImpl(stack);
        return inverted ^ passesFilter;
    }

    public abstract boolean passesFilterImpl(ItemStack stack);
}