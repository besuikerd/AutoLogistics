package com.besuikerd.autologistics.common.lib.dsl.vm.instruction;

import com.besuikerd.autologistics.common.lib.dsl.vm.VirtualMachine;

public class OpenScopeInstruction implements Instruction{
    public static final OpenScopeInstruction instance = new OpenScopeInstruction();

    @Override
    public void execute(VirtualMachine machine) {
        machine.openScope();
    }

    @Override
    public <ARG, RES> RES accept(InstructionVisitor<ARG, RES> visitor, ARG arg) {
        return visitor.visitOpenScopeInstruction(this, arg);
    }
}
