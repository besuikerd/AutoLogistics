package com.besuikerd.autologistics.common.lib.dsl.vm.instruction.compare;

import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.visitor.InstructionVisitor;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.NumericStackValue;

public class NEQInstruction extends CompareInstruction{
    public static final NEQInstruction instance = new NEQInstruction();
    @Override
    public boolean compute(NumericStackValue<?> left, NumericStackValue<?> right) {
        return !left.value.equals(right.value);
    }

    @Override
    public <ARG, RES, THROWS extends Throwable> RES accept(InstructionVisitor<ARG, RES, THROWS> visitor, ARG arg) throws THROWS {
        return visitor.visitNEQInstruction(this, arg);
    }
}
