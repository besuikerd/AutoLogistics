package com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue;

import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.visitor.StackValueVisitor;

public class BooleanValue implements StackValue {
    public static final BooleanValue trueValue = new BooleanValue(true);
    public static final BooleanValue falseValue = new BooleanValue(false);

    public final boolean value;

    public BooleanValue(boolean value) {
        this.value = value;
    }

    @Override
    public String stringRepresentation() {
        return Boolean.toString(value);
    }

    @Override
    public String toString() {
        return "BooleanValue(" + value + ")";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof BooleanValue && ((BooleanValue) obj).value == value;
    }

    @Override
    public <A, B, T extends Throwable> B accept(StackValueVisitor<A, B, T> visitor, A arg) throws T{
        return visitor.visitBooleanValue(this, arg);
    }
}