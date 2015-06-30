package com.besuikerd.autologistics.common.lib.dsl.vm.instruction;


import com.besuikerd.autologistics.common.lib.dsl.vm.VirtualMachine;
import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.visitor.InstructionVisitor;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.StackValue;

public class GetInstruction implements Instruction{
    public final String name;

    public GetInstruction(String name) {
        this.name = name;
    }

    @Override
    public void execute(VirtualMachine machine) {
        StackValue value = machine.get(name);
        if(value == null){
            machine.crash("value not found: " + name);
        } else{
            machine.push(value);
        }
    }

    @Override
    public String toString() {
        return "GetInstruction(" + name + ")";
    }

    @Override
    public <ARG, RES, THROWS extends Throwable> RES accept(InstructionVisitor<ARG, RES, THROWS> visitor, ARG arg) throws THROWS {
        return visitor.visitGetInstruction(this, arg);
    }
}
