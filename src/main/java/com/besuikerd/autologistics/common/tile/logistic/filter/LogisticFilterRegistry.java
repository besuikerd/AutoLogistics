package com.besuikerd.autologistics.common.tile.logistic.filter;

import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.ObjectValue;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.StackValue;
import com.besuikerd.autologistics.common.lib.registry.AbstractListRegistry;

import java.util.ArrayList;
import java.util.List;

public class LogisticFilterRegistry extends AbstractListRegistry<IFilterProvider>{
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
}