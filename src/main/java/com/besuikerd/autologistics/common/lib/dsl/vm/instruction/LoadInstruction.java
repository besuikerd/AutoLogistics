package com.besuikerd.autologistics.common.lib.dsl.vm.instruction;

import com.besuikerd.autologistics.common.lib.dsl.vm.VirtualMachine;

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
    public <ARG, RES> RES accept(InstructionVisitor<ARG, RES> visitor, ARG arg) {
        return visitor.visitLoadInstruction(this, arg);
    }
}
