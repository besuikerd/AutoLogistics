package com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue;

import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.visitor.StackValueVisitor;

public class DoubleValue extends NumericStackValue<Double>{

    public DoubleValue(Double value) {
        super(value);
    }

    @Override
    public <A, B> B accept(StackValueVisitor<A, B> visitor, A arg) {
        return visitor.visitDoubleValue(this, arg);
    }
}
