package com.besuikerd.autologistics.common.tile.logistic.filter.item;

import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.StackValue;
import com.besuikerd.autologistics.common.lib.registry.ListRegistry;

public class ItemFilterRegistry extends ListRegistry<IItemFilterProvider>{
    public static final ItemFilterRegistry instance = new ItemFilterRegistry();

    public ItemFilterRegistry() {
        register(ItemFilterIdOrOreDict.ItemFilterProvider.instance);
        register(ItemFilterName.ItemFilterProvider.instance);
    }

    public IItemFilter getFilter(StackValue value){
        for(IItemFilterProvider provider : registry){
            IItemFilter filter = provider.provide(value);
            if(filter != null){
                return filter;
            }
        }
        return null;
    }

    public boolean filterExists(StackValue value){
        return getFilter(value) != null;
    }
}
