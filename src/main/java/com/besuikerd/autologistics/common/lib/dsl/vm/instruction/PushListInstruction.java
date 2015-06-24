package com.besuikerd.autologistics.common.lib.dsl.vm.instruction;

import com.besuikerd.autologistics.common.lib.dsl.vm.VirtualMachine;
import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.visitor.InstructionVisitor;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.ListValue;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.StackValue;

import java.util.ArrayList;
import java.util.List;

public class PushListInstruction implements Instruction{
    public final int length;

    public PushListInstruction(int length) {
        this.length = length;
    }

    @Override
    public void execute(VirtualMachine machine) {
        List<StackValue> list = new ArrayList<StackValue>();
        for(int i = 0 ; i < length ; i++){
            list.add(machine.pop());
        }
        machine.push(new ListValue(list));
    }

    @Override
    public <ARG, RES, THROWS extends Throwable> RES accept(InstructionVisitor<ARG, RES, THROWS> visitor, ARG arg) throws THROWS {
        return visitor.visitPushListInstruction(this, arg);
    }
}
