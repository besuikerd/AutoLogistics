package com.besuikerd.autologistics.common.lib.dsl.vm.instruction;

import com.besuikerd.autologistics.common.lib.dsl.vm.VirtualMachine;

public class PopInstruction implements Instruction{
    public static final PopInstruction instance = new PopInstruction();

    @Override
    public void execute(VirtualMachine machine) {
        machine.pop();
    }

    @Override
    public <ARG, RES> RES accept(InstructionVisitor<ARG, RES> visitor, ARG arg) {
        return visitor.visitPopInstruction(this, arg);
    }
}
