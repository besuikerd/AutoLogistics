package com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue;

import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.visitor.StackValueVisitor;

public class Recurse implements StackValue{
    public static final Recurse instance = new Recurse();

    @Override
    public String stringRepresentation() {
        return "recurse";
    }

    @Override
    public <A, B, T extends Throwable> B accept(StackValueVisitor<A, B, T> visitor, A arg) throws T {
        return visitor.visitRecurse(this, arg);
    }
}
