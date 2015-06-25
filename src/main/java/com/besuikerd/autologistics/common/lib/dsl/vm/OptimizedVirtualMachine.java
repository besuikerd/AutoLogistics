package com.besuikerd.autologistics.common.lib.dsl.vm;

import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.Instruction;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.StackValue;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
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

    /**
     * forces lazy iterators to add their contents to the instructions stack
     */
    protected void unlazy(){
        int insertPos = instructions.size();
        if(pushAllInstructionsIterator != null){
            while(pushAllInstructionsIterator.hasNext()){
                instructions.add(insertPos, pushAllInstructionsIterator.next());
            }
        }
        for(Iterator<Instruction> it : pushAllInstructions){
            while(it.hasNext()){
                instructions.add(insertPos, pushAllInstructionsIterator.next());
            }
        }
    }

    @Override
    protected void serializeInstructions(DataOutput output) throws IOException {
        //we have to iterate these anyways, might as well add them to the instruction stack
        unlazy();
        super.serializeInstructions(output);
    }

    @Override
    protected void deserializeInstructions(DataInput input) throws IOException {
        pushAllInstructions.clear();
        pushAllInstructionsIterator = null;
        super.deserializeInstructions(input);
    }

    //TODO fix isErrorState and getErrorMessage
}
