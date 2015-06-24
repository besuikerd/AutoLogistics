package com.besuikerd.autologistics.common.lib.dsl.vm.instruction;

import com.besuikerd.autologistics.common.lib.dsl.vm.VirtualMachine;

public class CloseScopeInstruction implements Instruction{
    public static final CloseScopeInstruction instance = new CloseScopeInstruction();

    @Override
    public void execute(VirtualMachine machine) {
        machine.closeScope();
    }

    @Override
    public <ARG, RES> RES accept(InstructionVisitor<ARG, RES> visitor, ARG arg) {
        return visitor.visitCloseScopeInstruction(this, arg);
    }
}
