package com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue;

public abstract class NumericStackValue<A extends Number> implements StackValue{
    public final A value;

    public NumericStackValue(A value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" + value + ")";
    }

    @Override
    public boolean equals(Object obj) {
        return getClass().isInstance(obj) && getClass().cast(obj).value.equals(value);
    }

    @Override
    public String stringRepresentation() {
        return value.toString();
    }
}
