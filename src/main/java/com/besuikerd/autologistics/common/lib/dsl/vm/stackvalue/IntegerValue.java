package com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue;

import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.visitor.StackValueVisitor;

public class IntegerValue extends NumericStackValue<Integer>{
    public static final IntegerValue zeroValue = new IntegerValue(0);

    public IntegerValue(int value) {
        super(value);
    }

    @Override
    public <A, B, T extends Throwable> B accept(StackValueVisitor<A, B, T> visitor, A arg) throws T {
        return visitor.visitIntegerValue(this, arg);
    }
}
