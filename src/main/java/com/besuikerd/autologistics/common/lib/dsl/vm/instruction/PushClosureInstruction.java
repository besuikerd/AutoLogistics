package com.besuikerd.autologistics.common.lib.dsl.vm.instruction;

import com.besuikerd.autologistics.common.lib.collection.Stack;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.ClosureValue;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.Recurse;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.StackValue;
import com.besuikerd.autologistics.common.lib.dsl.vm.VirtualMachine;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PushClosureInstruction implements Instruction{
    /**
     * can be null; optionally named closure
     */
    public final String name;
    public final List<String> bindings;
    public final List<String> free;
    public final Collection<Instruction> body;

    public PushClosureInstruction(String name, List<String> bindings, List<String> free, Collection<Instruction> body) {
        this.name = name;
        this.bindings = bindings;
        this.free = free;
        this.body = body;
    }

    @Override
    public void execute(VirtualMachine machine) {
        Map<String, StackValue> frees = new HashMap<String, StackValue>();
        for(String name : free){
            StackValue freeValue = machine.get(name);
            if(freeValue == null){
                if(this.name != null && this.name.equals(name)){
                    frees.put(name, Recurse.instance);
                } else{
                    machine.crash("could not find free variable: " + name);
                    return;
                }
            } else{
                frees.put(name, freeValue);
            }
        }
        machine.push(new ClosureValue(bindings, frees, body));
    }

    @Override
    public <ARG, RES> RES accept(InstructionVisitor<ARG, RES> visitor, ARG arg) {
        return null;
    }
}
