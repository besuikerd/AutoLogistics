package com.besuikerd.autologistics.common.lib.dsl.vm.instruction;

import com.besuikerd.autologistics.common.lib.dsl.vm.VirtualMachine;
import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.visitor.InstructionVisitor;

public class OpenScopeInstruction implements Instruction{
    public static final OpenScopeInstruction instance = new OpenScopeInstruction();

    @Override
    public void execute(VirtualMachine machine) {
        machine.openScope();
    }

    @Override
    public <ARG, RES, THROWS extends Throwable> RES accept(InstructionVisitor<ARG, RES, THROWS> visitor, ARG arg) throws THROWS {
        return visitor.visitOpenScopeInstruction(this, arg);
    }
}
