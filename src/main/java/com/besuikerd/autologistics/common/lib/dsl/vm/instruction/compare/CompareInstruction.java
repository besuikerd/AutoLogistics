package com.besuikerd.autologistics.common.lib.dsl.vm.instruction.compare;

import com.besuikerd.autologistics.common.lib.dsl.vm.VirtualMachine;
import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.Instruction;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.BooleanValue;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.NumericStackValue;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.visitor.NumericStackValueVisitor;

public abstract class CompareInstruction implements Instruction {
    public abstract boolean compute(NumericStackValue<?> left, NumericStackValue<?> right);

    @Override
    public void execute(VirtualMachine machine) {
        NumericStackValue<?> right = machine.pop().accept(NumericStackValueVisitor.instance, null);
        if(right == null){
            machine.crash("right hand side should be a numeric value");
        } else{
            NumericStackValue<?> left = machine.pop().accept(NumericStackValueVisitor.instance, null);
            if(left == null){
                machine.crash("left hand side should be a numeric value");
            } else{
                boolean result = compute(left, right);
                machine.push(new BooleanValue(result));
            }
        }
    }
}