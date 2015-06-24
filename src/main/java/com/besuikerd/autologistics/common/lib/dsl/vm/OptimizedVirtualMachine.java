package com.besuikerd.autologistics.common.lib.dsl.vm;

import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.Instruction;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.StackValue;

import java.util.Collection;
import java.util.Iterator;
import java.util.Stack;

public class OptimizedVirtualMachine extends DefaultVirtualMachine{

    private Stack<Iterator<Instruction>> pushAllInstructions;
    private Iterator<Instruction> pushAllInstructionsIterator;

    public OptimizedVirtualMachine(){
        this.pushAllInstructions = new Stack<Iterator<Instruction>>();
    }

    @Override
    public Instruction popInstruction() {
        Instruction popped;
        if(pushAllInstructionsIterator != null){
            popped = pushAllInstructionsIterator.next();
            if(!pushAllInstructionsIterator.hasNext()){
                pushAllInstructionsIterator = pushAllInstructions.isEmpty() ? null : pushAllInstructions.pop();
            }
        } else{
            popped = instructions.pop();
        }
        return popped;
    }

    @Override
    public void pushInstructions(Collection<Instruction> instructions) {
        Iterator<Instruction> iterator = instructions.iterator();
        if(pushAllInstructionsIterator != null){
            pushAllInstructions.push(pushAllInstructionsIterator);
            pushAllInstructionsIterator = iterator;
        }
    }
}
