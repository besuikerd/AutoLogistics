package com.besuikerd.autologistics.common.tile.logistic.filter;

import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.StackValue;
import com.besuikerd.autologistics.common.tile.logistic.filter.item.IItemFilter;
import com.besuikerd.autologistics.common.tile.logistic.filter.item.ItemFilterRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class LogisticFilterItemFilter extends AbstractLogisticFilter{

    private IItemFilter itemFilter;

    public LogisticFilterItemFilter(StackValue value, IItemFilter itemFilter) {
        super(value);
        this.itemFilter = itemFilter;
    }

    @Override
    public boolean passesBlockFilterImpl(TileEntity from, TileEntity to) {
        return false;
    }

    @Override
    public boolean passesItemFilterImpl(ItemStack stack) {
        return itemFilter.passesFilter(stack);
    }

    public static class FilterProvider implements IFilterProvider{
        public static final FilterProvider instance = new FilterProvider();

        @Override
        public ILogisticFilter provide(StackValue value) {
            IItemFilter filter = ItemFilterRegistry.instance.getFilter(value);
            if(filter != null){
                return new LogisticFilterItemFilter(value, filter);
            }
            return null;
        }
    }
}
