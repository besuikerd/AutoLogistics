package com.besuikerd.autologistics.common.lib.dsl.vm.instruction.compare;


import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.InstructionVisitor;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.NumericStackValue;

public class LTInstruction extends CompareInstruction{
    public static final LTInstruction instance = new LTInstruction();
    @Override
    public boolean compute(NumericStackValue<?> left, NumericStackValue<?> right) {
        return left.value.doubleValue() < right.value.doubleValue();
    }

    @Override
    public <ARG, RES> RES accept(InstructionVisitor<ARG, RES> visitor, ARG arg) {
        return visitor.visitLTInstruction(this, arg);
    }
}
