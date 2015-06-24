package com.besuikerd.autologistics.common.lib.dsl.vm.instruction.logical;


import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.InstructionVisitor;

public class AndInstruction extends LogicalInstruction{
    public static final AndInstruction instance = new AndInstruction();

    @Override
    public boolean compute(Boolean left, Boolean right) {
        return left && right;
    }

    @Override
    public <ARG, RES> RES accept(InstructionVisitor<ARG, RES> visitor, ARG arg) {
        return visitor.visitAndInstruction(this, arg);
    }
}
