package com.besuikerd.autologistics.common.lib.dsl.vm.instruction;

import com.besuikerd.autologistics.common.lib.dsl.vm.VirtualMachine;
import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.visitor.InstructionVisitor;

import java.util.Collection;

public class LoadInstruction implements Instruction{
    public final Collection<Instruction> instructions;

    public LoadInstruction(Collection<Instruction> instructions) {
        this.instructions = instructions;
    }

    @Override
    public void execute(VirtualMachine machine) {
        machine.pushInstructions(instructions);
    }

    @Override
    public <ARG, RES, THROWS extends Throwable> RES accept(InstructionVisitor<ARG, RES, THROWS> visitor, ARG arg) throws THROWS {
        return visitor.visitLoadInstruction(this, arg);
    }
}
