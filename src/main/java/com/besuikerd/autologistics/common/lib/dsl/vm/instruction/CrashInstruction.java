package com.besuikerd.autologistics.common.lib.dsl.vm.instruction;

import com.besuikerd.autologistics.common.lib.dsl.vm.VirtualMachine;

public class CrashInstruction implements Instruction{
    public final String message;

    public CrashInstruction(String message) {
        this.message = message;
    }

    @Override
    public void execute(VirtualMachine machine) {
        machine.pushInstruction(this);
    }

    @Override
    public <ARG, RES> RES accept(InstructionVisitor<ARG, RES> visitor, ARG arg) {
        return visitor.visitCrashInstruction(this, arg);
    }
}