package com.besuikerd.autologistics.common.lib.dsl.vm.instruction;

import com.besuikerd.autologistics.common.lib.dsl.vm.VirtualMachine;
import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.visitor.InstructionVisitor;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.visitor.BooleanStackValueVisitor;

import java.util.Collection;
import java.util.Collections;

public class RepeatedBranchInstruction implements Instruction{
    public Collection<Instruction> branchWhile;

    public RepeatedBranchInstruction(Collection<Instruction> branchWhile) {
        this.branchWhile = branchWhile;
    }

    @Override
    public void execute(VirtualMachine machine) {
        Boolean b = machine.pop().accept(BooleanStackValueVisitor.instance, null);
        if(b == null){
            machine.crash("Expected boolean value for branch");
        } else if(b){
            machine.pushInstruction(this);
            machine.pushInstructions(branchWhile);
        }
    }

    @Override
    public <ARG, RES, THROWS extends Throwable> RES accept(InstructionVisitor<ARG, RES, THROWS> visitor, ARG arg) throws THROWS {
        return visitor.visitRepeatedBranchInstruction(this, arg);
    }
}
