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
    private Instruction peek;

    private boolean crashed;

    public OptimizedVirtualMachine(){
        this.pushAllInstructions = new Stack<Iterator<Instruction>>();
    }

    @Override
    public void reset() {
        super.reset();
        crashed = false;
    }

    @Override
    public void crash(String message) {
        crashed = true;
        super.crash(message);
    }

    @Override
    public Instruction popInstruction() {
        Instruction popped;
        if(peek != null) {
            popped = peek;
            peek = null;
        } else if(pushAllInstructionsIterator != null){
            popped = pushAllInstructionsIterator.next();
            if(!pushAllInstructionsIterator.hasNext()){
                if(pushAllInstructions.isEmpty()){
                    pushAllInstructionsIterator = null;
                } else{
                    Iterator<Instruction> poppedIt = pushAllInstructions.pop();
                    if(poppedIt.hasNext()){
                        pushAllInstructionsIterator = poppedIt;
                    }
                }
            }
        } else{
            popped = super.popInstruction();
        }
        return popped;
    }

    @Override
    public void pushInstructions(Collection<Instruction> instructions) {
        if(instructions.isEmpty()){
            return;
        }

        Iterator<Instruction> iterator = instructions.iterator();
        if(peek != null){
            pushAllInstructions.push(new PeekIterator<Instruction>(peek));
            peek = null;
        }
        if(pushAllInstructionsIterator != null){
            pushAllInstructions.push(pushAllInstructionsIterator);
        }
        pushAllInstructionsIterator = iterator;
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
            pushAllInstructionsIterator = null;
        }
        for(Iterator<Instruction> it : pushAllInstructions){
            while(it.hasNext()){
                instructions.add(insertPos, it.next());
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

    private Instruction peekInstruction(){
        if(peek == null){
            peek = popInstruction();
        }
        return peek;
    }

    private boolean hasInstructions(){
        return peek != null || pushAllInstructionsIterator != null || !instructions.isEmpty();
    }

    @Override
    public boolean isTerminated() {
        return !hasInstructions();
    }

    @Override
    public boolean isErrorState() {
        return crashed;
    }

    private class PeekIterator<A> implements Iterator<A>{
        boolean hasNext;
        private A value;

        public PeekIterator(A value) {
            this.value = value;
            this.hasNext = true;
        }

        @Override
        public boolean hasNext() {
            return hasNext;
        }

        @Override
        public A next() {
            hasNext = false;
            return value;
        }
    }
}
