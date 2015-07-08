package com.besuikerd.autologistics.common.lib.dsl.vm.instruction;

import com.besuikerd.autologistics.common.lib.dsl.vm.VirtualMachine;
import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.visitor.InstructionVisitor;

public class DupInstruction implements Instruction{
    public static final DupInstruction instance = new DupInstruction();

    @Override
    public void execute(VirtualMachine machine) {
        machine.dup();
    }

    @Override
    public <ARG, RES, THROWS extends Throwable> RES accept(InstructionVisitor<ARG, RES, THROWS> visitor, ARG arg) throws THROWS {
        return visitor.visitDupInstruction(this, arg);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "()";
    }
}
