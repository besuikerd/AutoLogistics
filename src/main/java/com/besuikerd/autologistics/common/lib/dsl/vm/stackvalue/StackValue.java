package com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue;

import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.visitor.StackValueVisitor;

public interface StackValue {
    String stringRepresentation();
    <A,B> B accept(StackValueVisitor<A,B> visitor, A arg);
}