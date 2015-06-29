package com.besuikerd.autologistics.common.tile.logistic.filter.item;

import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.StackValue;

public interface IItemFilterProvider {
    public IItemFilter provide(StackValue value);
}
