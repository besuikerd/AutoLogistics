package com.besuikerd.autologistics.common.lib.dsl.vm.instruction.numeric;

import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.visitor.InstructionVisitor;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.NumericStackValue;

public class SubInstruction extends NumericInstruction{
    public static final SubInstruction instance = new SubInstruction();

    @Override
    public Double compute(NumericStackValue<?> left, NumericStackValue<?> right) {
        return left.value.doubleValue() - right.value.doubleValue();
    }

    @Override
    public <ARG, RES, THROWS extends Throwable> RES accept(InstructionVisitor<ARG, RES, THROWS> visitor, ARG arg) throws THROWS {
        return visitor.visitSubInstruction(this, arg);
    }
}
