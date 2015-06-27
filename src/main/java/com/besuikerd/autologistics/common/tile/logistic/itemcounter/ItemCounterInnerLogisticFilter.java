package com.besuikerd.autologistics.common.tile.logistic.itemcounter;

import com.besuikerd.autologistics.common.tile.logistic.filter.ILogisticFilter;
import com.besuikerd.autologistics.common.tile.logistic.filter.ItemFilter;
import net.minecraft.item.ItemStack;

public abstract class ItemCounterInnerLogisticFilter extends AbstractInventoryItemCounter{
    protected ILogisticFilter[] filters;

    public ItemCounterInnerLogisticFilter(ILogisticFilter... filters) {
        this.filters = filters;
    }

    @Override
    protected boolean passesItemFilter(ILogisticFilter filter, ItemStack stack) {
        for(ILogisticFilter itemFilter : filters){
            if(!itemFilter.passesItemFilter(stack)){
                return false;
            }
        }
        return true;
    }
}
