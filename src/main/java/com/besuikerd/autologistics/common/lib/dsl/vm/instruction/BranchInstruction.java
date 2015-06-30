package com.besuikerd.autologistics.common.lib.dsl.vm.instruction;


import com.besuikerd.autologistics.common.lib.dsl.vm.VirtualMachine;
import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.visitor.InstructionVisitor;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.visitor.BooleanStackValueVisitor;

import java.util.Collection;

public class BranchInstruction implements Instruction {

    public final Collection<Instruction> branchTrue;
    public final Collection<Instruction> branchFalse;

    public BranchInstruction(Collection<Instruction> branchTrue, Collection<Instruction> branchFalse) {
        this.branchTrue = branchTrue;
        this.branchFalse = branchFalse;
    }

    @Override
    public void execute(VirtualMachine machine) {
        Boolean b = machine.pop().accept(BooleanStackValueVisitor.instance, null);
        if(b == null){
            machine.crash("Expected boolean value for branch");
        } else{
            machine.pushInstructions(b ? branchTrue : branchFalse);
        }
    }

    @Override
    public <ARG, RES, THROWS extends Throwable> RES accept(InstructionVisitor<ARG, RES, THROWS> visitor, ARG arg) throws THROWS {
        return visitor.visitBranchInstruction(this, arg);
    }
}