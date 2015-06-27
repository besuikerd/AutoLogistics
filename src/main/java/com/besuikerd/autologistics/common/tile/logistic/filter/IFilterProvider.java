package com.besuikerd.autologistics.common.tile.logistic.filter;

import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.StackValue;

public interface IFilterProvider {
    ILogisticFilter provide(StackValue value);
}