package com.besuikerd.autologistics.common.tile.logistic.filter;

import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.ObjectValue;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.StackValue;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class LogisticFilterItemFilter extends AbstractLogisticFilter{

    private ItemFilter itemFilter;

    public LogisticFilterItemFilter(StackValue value, ItemFilter itemFilter) {
        super(value);
        this.itemFilter = itemFilter;
    }

    @Override
    public boolean passesBlockFilter(TileEntity from, TileEntity to) {
        return false;
    }

    @Override
    public boolean passesItemFilter(ItemStack stack) {
        return itemFilter.passesFilter(stack);
    }

    @Override
    public int getMeta() {
        return itemFilter.meta;
    }

    public static class FilterProvider implements IFilterProvider{
        public static final FilterProvider instance = new FilterProvider();

        @Override
        public ILogisticFilter provide(StackValue value) {
            ItemFilter filter = ItemFilter.fromStackValue(value);
            if(filter != null){
                return new LogisticFilterItemFilter(value, filter);
            }
            return null;
        }
    }
}
