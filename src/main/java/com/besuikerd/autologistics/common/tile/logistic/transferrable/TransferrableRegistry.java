package com.besuikerd.autologistics.common.tile.logistic.transferrable;

import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.StackValue;
import com.besuikerd.autologistics.common.lib.registry.ListRegistry;

public class TransferrableRegistry extends ListRegistry<ITransferrableProvider<?, ?>> {
    public static final TransferrableRegistry instance = new TransferrableRegistry();

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
