package com.besuikerd.autologistics.common.lib.dsl.vm.instruction.compare;

import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.InstructionVisitor;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.NumericStackValue;

public class NEQInstruction extends CompareInstruction{
    public static final NEQInstruction instance = new NEQInstruction();
    @Override
    public boolean compute(NumericStackValue<?> left, NumericStackValue<?> right) {
        return !left.value.equals(right.value);
    }

    @Override
    public <ARG, RES> RES accept(InstructionVisitor<ARG, RES> visitor, ARG arg) {
        return visitor.visitNEQInstruction(this, arg);
    }
}
