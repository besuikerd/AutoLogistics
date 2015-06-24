package com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue;

import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.visitor.StackValueVisitor;

public class IntegerValue extends NumericStackValue<Integer>{

    public IntegerValue(int value) {
        super(value);
    }

    @Override
    public <A, B> B accept(StackValueVisitor<A, B> visitor, A arg) {
        return visitor.visitIntegerValue(this, arg);
    }
}
