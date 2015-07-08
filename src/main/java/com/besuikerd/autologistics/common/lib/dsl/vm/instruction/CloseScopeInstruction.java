package com.besuikerd.autologistics.common.lib.dsl.vm.instruction;

import com.besuikerd.autologistics.common.lib.dsl.vm.VirtualMachine;
import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.visitor.InstructionVisitor;

public class CloseScopeInstruction implements Instruction{
    public static final CloseScopeInstruction instance = new CloseScopeInstruction();

    @Override
    public void execute(VirtualMachine machine) {
        machine.closeScope();
    }

    @Override
    public <ARG, RES, THROWS extends Throwable> RES accept(InstructionVisitor<ARG, RES, THROWS> visitor, ARG arg) throws THROWS {
        return visitor.visitCloseScopeInstruction(this, arg);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "()";
    }
}
