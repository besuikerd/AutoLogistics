package com.besuikerd.autologistics.common.lib.dsl.vm.instruction.compare;


import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.InstructionVisitor;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.NumericStackValue;

public class LTEInstruction extends CompareInstruction{
    public static final LTEInstruction instance = new LTEInstruction();
    @Override
    public boolean compute(NumericStackValue<?> left, NumericStackValue<?> right) {
        return left.value.doubleValue() <= right.value.doubleValue();
    }

    @Override
    public <ARG, RES> RES accept(InstructionVisitor<ARG, RES> visitor, ARG arg) {
        return visitor.visitLTEInstruction(this, arg);
    }
}
