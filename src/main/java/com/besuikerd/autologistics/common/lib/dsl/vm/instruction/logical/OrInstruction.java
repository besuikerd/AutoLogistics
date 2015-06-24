package com.besuikerd.autologistics.common.lib.dsl.vm.instruction.logical;


import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.InstructionVisitor;

public class OrInstruction extends LogicalInstruction{
    public static final OrInstruction instance = new OrInstruction();

    @Override
    public boolean compute(Boolean left, Boolean right) {
        return left || right;
    }

    @Override
    public <ARG, RES> RES accept(InstructionVisitor<ARG, RES> visitor, ARG arg) {
        return visitor.visitOrInstruction(this, arg);
    }
}
