package com.besuikerd.autologistics.common.lib.dsl.vm.instruction;

import com.besuikerd.autologistics.common.lib.dsl.vm.VirtualMachine;
import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.visitor.InstructionVisitor;

public class PutInstruction implements Instruction{
    public final String name;
    public final boolean isLocal;

    public PutInstruction(String name, boolean isLocal) {
        this.name = name;
        this.isLocal = isLocal;
    }

    public PutInstruction(String name){
        this(name, false);
    }

    @Override
    public void execute(VirtualMachine machine) {
        machine.put(name, machine.pop(), isLocal);
    }

    @Override
    public <ARG, RES, THROWS extends Throwable> RES accept(InstructionVisitor<ARG, RES, THROWS> visitor, ARG arg) throws THROWS {
        return visitor.visitPutInstruction(this, arg);
    }

    @Override
    public String toString() {
        return "PutInstruction(" + name + ")";
    }
}
