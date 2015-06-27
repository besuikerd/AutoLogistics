package com.besuikerd.autologistics.common.tile.logistic.transferrable;

import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.StackValue;
import com.besuikerd.autologistics.common.lib.registry.ListRegistry;
import com.besuikerd.autologistics.common.tile.logistic.filter.LogisticFilterItem;
import com.besuikerd.autologistics.common.tile.logistic.filter.LogisticFilterName;
import com.besuikerd.autologistics.common.tile.logistic.filter.LogisticFilterPosition;
import com.besuikerd.autologistics.common.tile.logistic.filter.LogisticFilterRegistry;

public class TransferrableRegistry extends ListRegistry<ITransferrableProvider<?, ?>> {
    public static final TransferrableRegistry instance = new TransferrableRegistry();

    static{
        LogisticFilterRegistry.instance.register(LogisticFilterItem.FilterProvider.instance);
        LogisticFilterRegistry.instance.register(LogisticFilterName.FilterProvider.instance);
        LogisticFilterRegistry.instance.register(LogisticFilterPosition.FilterProvider.instance);

        TransferrableRegistry.instance.register(TransferrableMoveItems.TransferrableProvider.instance);
        TransferrableRegistry.instance.register(TransferrableCraftFrom.TransferrableProvider.instance);
        TransferrableRegistry.instance.register(TransferrableCraftTo.TransferrableProvider.instance);
    }

    @SuppressWarnings("unchecked")
    public <A extends StackValue,B extends StackValue> ITransferrable<A, B> getTransferrable(A from, B to){
        for(ITransferrableProvider<?, ?> provider : registry){
            ITransferrable<A, B> transferrable = (ITransferrable<A, B>) provider.provide(from ,to);
            if(transferrable != null){
                return transferrable;
            }
        }
        return null;
    }
}
