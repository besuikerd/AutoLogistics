package com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue;

import com.besuikerd.autologistics.common.lib.collection.Stack;
import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.Instruction;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.visitor.StackValueVisitor;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class ClosureValue implements StackValue{
    public final List<String> bindings;
    public final Map<String, StackValue> free;
    public final Collection<Instruction> body;

    public ClosureValue(List<String> bindings, Map<String, StackValue> mappings, Collection<Instruction> body) {
        this.bindings = bindings;
        this.free = mappings;
        this.body = body;
    }

    @Override
    public String stringRepresentation() {
        return "Closure(" + bindings.size() + ")";
    }

    @Override
    public <A, B, T extends Throwable> B accept(StackValueVisitor<A, B, T> visitor, A arg) throws T {
        return visitor.visitClosureValue(this, arg);
    }
}