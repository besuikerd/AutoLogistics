package com.besuikerd.autologistics.common.tile.logistic.filter;

import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.ListValue;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.StackValue;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.StackValues;
import com.besuikerd.autologistics.common.lib.registry.ListRegistry;

public class LogisticFilterRegistry extends ListRegistry<IFilterProvider> {
    public static final LogisticFilterRegistry instance = new LogisticFilterRegistry();

    public ILogisticFilter getFilter(StackValue value){
        for(IFilterProvider provider : registry){
            ILogisticFilter filter = provider.provide(value);
            if(filter != null){
                return filter;
            }
        }
        return null;
    }

    public boolean filterExists(StackValue value){
        return getFilter(value) != null;
    }

    public ILogisticFilter[] tryGetFilterList(StackValue value){
        ListValue list;
        if((list = StackValues.tryExpectType(ListValue.class, value)) != null){
            ILogisticFilter[] filters = new ILogisticFilter[list.value.size()];
            int offset = 0;
            for(StackValue elem : list.value){
                ILogisticFilter filter = LogisticFilterRegistry.instance.getFilter(elem);
                if(filter == null){
                    return null;
                } else{
                    filters[offset++] = filter;
                }
            }
            return filters;
        }
        return null;
    }
}