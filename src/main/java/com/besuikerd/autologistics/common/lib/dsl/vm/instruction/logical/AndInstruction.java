package com.besuikerd.autologistics.common.lib.dsl.vm.instruction.logical;


import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.visitor.InstructionVisitor;

public class AndInstruction extends LogicalInstruction{
    public static final AndInstruction instance = new AndInstruction();

    @Override
    public boolean compute(Boolean left, Boolean right) {
        return left && right;
    }

    @Override
    public <ARG, RES, THROWS extends Throwable> RES accept(InstructionVisitor<ARG, RES, THROWS> visitor, ARG arg) throws THROWS {
        return visitor.visitAndInstruction(this, arg);
    }
}
