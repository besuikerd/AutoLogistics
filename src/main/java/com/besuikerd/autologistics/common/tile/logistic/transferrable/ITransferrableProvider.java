package com.besuikerd.autologistics.common.tile.logistic.transferrable;

import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.StackValue;

public interface ITransferrableProvider<FROM extends StackValue, TO extends StackValue> {
    ITransferrable<FROM, TO> provide(StackValue from, StackValue to);
}