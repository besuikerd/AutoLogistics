package com.besuikerd.autologistics.common.lib.dsl.vm.instruction;

import com.besuikerd.autologistics.common.lib.dsl.vm.VirtualMachine;
import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.visitor.InstructionVisitor;

public class PopInstruction implements Instruction{
    public static final PopInstruction instance = new PopInstruction();

    @Override
    public void execute(VirtualMachine machine) {
        machine.pop();
    }

    @Override
    public <ARG, RES, THROWS extends Throwable> RES accept(InstructionVisitor<ARG, RES, THROWS> visitor, ARG arg) throws THROWS {
        return visitor.visitPopInstruction(this, arg);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "()";
    }
}
