package com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue;

import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.visitor.StackValueVisitor;

public class NilValue implements StackValue{
    public static final NilValue instance = new NilValue();

    @Override
    public String stringRepresentation() {
        return "null";
    }

    @Override
    public <A, B, T extends Throwable> B accept(StackValueVisitor<A, B, T> visitor, A arg) throws T {
        return visitor.visitNilValue(this, arg);
    }
}
