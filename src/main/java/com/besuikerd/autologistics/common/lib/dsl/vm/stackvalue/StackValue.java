package com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue;

import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.visitor.StackValueVisitor;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.visitor.TypeStackValueVisitor;

public interface StackValue {
    String stringRepresentation();
    <A,B, T extends Throwable> B accept(StackValueVisitor<A,B, T> visitor, A arg) throws T;
}