package com.besuikerd.autologistics.common.lib.dsl.vm.instruction;

import com.besuikerd.autologistics.common.lib.dsl.vm.VirtualMachine;
import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.visitor.InstructionVisitor;

public class NopInstruction implements Instruction{
    private int amount;

    public NopInstruction(int amount) {
        this.amount = amount;
    }

    public NopInstruction() {
        this(1);
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public void execute(VirtualMachine machine) {
        if(--amount != 0){
            machine.pushInstruction(this);
        }
    }

    @Override
    public <ARG, RES, THROWS extends Throwable> RES accept(InstructionVisitor<ARG, RES, THROWS> visitor, ARG arg) throws THROWS {
        return visitor.visitNopInstruction(this, arg);
    }
}
