package com.besuikerd.autologistics.common.lib.dsl.vm.instruction;

import com.besuikerd.autologistics.common.lib.dsl.vm.VirtualMachine;

public interface Instruction {
    void execute(VirtualMachine machine);
    <ARG, RES> RES accept(InstructionVisitor<ARG, RES> visitor, ARG arg);
}