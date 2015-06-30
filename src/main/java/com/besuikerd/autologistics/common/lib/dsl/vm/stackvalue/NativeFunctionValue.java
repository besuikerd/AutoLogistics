package com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue;

import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.visitor.StackValueVisitor;

public class NativeFunctionValue implements StackValue{
    public final String name;

    public NativeFunctionValue(String name) {
        this.name = name;
    }

    @Override
    public String stringRepresentation() {
        return "native(" + name + ")";
    }

    @Override
    public <A, B, T extends Throwable> B accept(StackValueVisitor<A, B, T> visitor, A arg) throws T {
        return visitor.visitNativeFunctionValue(this, arg);
    }
}
