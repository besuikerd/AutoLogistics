package com.besuikerd.autologistics.common.tile.logistic.filter;

import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.ListValue;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.StackValue;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.StackValues;
import com.besuikerd.autologistics.common.lib.registry.ListRegistry;

public class LogisticFilterRegistry extends ListRegistry<IFilterProvider> {
    public static final LogisticFilterRegistry instance = new LogisticFilterRegistry();

    public LogisticFilterRegistry() {
        register(LogisticFilterItem.FilterProvider.instance);
        register(LogisticFilterName.FilterProvider.instance);
        register(LogisticFilterPosition.FilterProvider.instance);
    }

    public ILogisticFilter tryGetFilter(StackValue value){
        for(IFilterProvider provider : registry){
            ILogisticFilter filter = provider.provide(value);
            if(filter != null){
                return filter;
            }
        }
        return null;
    }

    public boolean filterExists(StackValue value){
        return tryGetFilter(value) != null;
    }

    public ILogisticFilter[] tryGetFilterList(StackValue value){
        ListValue list;
        if((list = StackValues.tryExpectType(ListValue.class, value)) != null){
            ILogisticFilter[] filters = new ILogisticFilter[list.value.size()];
            int offset = 0;
            for(StackValue elem : list.value){
                ILogisticFilter filter = LogisticFilterRegistry.instance.tryGetFilter(elem);
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

    public ILogisticFilter[] tryGetFilterOrFilterList(StackValue value){
        ILogisticFilter[] filters;
        ILogisticFilter filter;
        if((filter = LogisticFilterRegistry.instance.tryGetFilter(value)) != null) {
            filters = new ILogisticFilter[]{filter};
        } else if((filters = LogisticFilterRegistry.instance.tryGetFilterList(value)) == null){
            return null;
        }
        return filters;
    }
}