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
    public <A, B> B accept(StackValueVisitor<A, B> visitor, A arg) {
        return visitor.visitNativeFunctionValue(this, arg);
    }
}
