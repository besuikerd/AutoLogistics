package com.besuikerd.autologistics.common.lib.dsl.vm.instruction;

import com.besuikerd.autologistics.common.lib.dsl.vm.VirtualMachine;

import java.util.Collection;
import java.util.Collections;

public class RepeatedBranchInstruction extends BranchInstruction{
    @SuppressWarnings("unchecked")
    public RepeatedBranchInstruction(Collection<Instruction> branchTrue) {
        super(branchTrue, Collections.EMPTY_LIST);
        branchTrue.add(this);
    }

    @Override
    public void execute(VirtualMachine machine) {
        super.execute(machine);
    }
}
