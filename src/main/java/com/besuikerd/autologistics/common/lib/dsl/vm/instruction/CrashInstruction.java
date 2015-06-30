package com.besuikerd.autologistics.common.lib.dsl.vm.instruction;

import com.besuikerd.autologistics.common.lib.dsl.vm.VirtualMachine;
import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.visitor.InstructionVisitor;

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
    public <ARG, RES, THROWS extends Throwable> RES accept(InstructionVisitor<ARG, RES, THROWS> visitor, ARG arg) throws THROWS {
        return visitor.visitCrashInstruction(this, arg);
    }
}