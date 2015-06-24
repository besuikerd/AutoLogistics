package com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue;

import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.visitor.StackValueVisitor;

public class StringValue implements StackValue{
    public String value;

    public StringValue(String value) {
        this.value = value;
    }

    @Override
    public String stringRepresentation() {
        return value;
    }

    @Override
    public String toString() {
        return "StringValue(" + value + ")";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof StringValue && ((StringValue) obj).value.equals(value);
    }

    @Override
    public <A, B> B accept(StackValueVisitor<A, B> visitor, A arg) {
        return visitor.visitStringValue(this, arg);
    }
}
