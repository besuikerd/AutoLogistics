package com.besuikerd.autologistics.common.lib.dsl.vm.instruction;

import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.ObjectValue;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.StackValue;
import com.besuikerd.autologistics.common.lib.dsl.vm.VirtualMachine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PushObjectInstruction implements Instruction{
    public final List<String> keys;

    public PushObjectInstruction(List<String> keys) {
        this.keys = keys;
    }


    @Override
    public void execute(VirtualMachine machine) {
        Map<String, StackValue> mapping = new HashMap<String, StackValue>();
        for(String key : keys){
            mapping.put(key, machine.pop());
        }
        machine.push(new ObjectValue(mapping));
    }

    @Override
    public <ARG, RES> RES accept(InstructionVisitor<ARG, RES> visitor, ARG arg) {
        return visitor.visitPushObjectInstruction(this, arg);
    }
}
