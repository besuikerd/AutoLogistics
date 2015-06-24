package com.besuikerd.autologistics.common.lib.dsl.vm.instruction.numeric;

import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.InstructionVisitor;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.NumericStackValue;

public class AddInstruction extends NumericInstruction{
    public static final AddInstruction instance = new AddInstruction();
    @Override
    public Double compute(NumericStackValue<?> left, NumericStackValue<?> right) {
        return left.value.doubleValue() + right.value.doubleValue();
    }

    @Override
    public <ARG, RES> RES accept(InstructionVisitor<ARG, RES> visitor, ARG arg) {
        return visitor.visitAddInstruction(this, arg);
    }
}
