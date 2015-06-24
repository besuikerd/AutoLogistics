package com.besuikerd.autologistics.common.lib.dsl.vm.instruction.numeric;

import com.besuikerd.autologistics.common.lib.dsl.vm.VirtualMachine;
import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.Instruction;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.DoubleValue;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.IntegerValue;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.NumericStackValue;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.StackValue;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.visitor.NumericStackValueVisitor;

public abstract class NumericInstruction implements Instruction {
    public abstract Double compute(NumericStackValue<?> left, NumericStackValue<?> right);

    @Override
    public void execute(VirtualMachine machine) {
        StackValue rightValue = machine.pop();
        NumericStackValue<?> right = rightValue.accept(NumericStackValueVisitor.instance, null);
        if(right == null){
            machine.crash("right hand side should be a numeric value, got: " + rightValue.stringRepresentation());
        } else{
            StackValue leftValue = machine.pop();
            NumericStackValue<?> left = leftValue.accept(NumericStackValueVisitor.instance, null);
            if(left == null){
                machine.crash("left hand side should be a numeric value, got: " + leftValue.stringRepresentation());
            } else{
                Double result = compute(left, right);
                StackValue stackValue = right.value instanceof Integer && left.value instanceof Integer ? new IntegerValue(result.intValue()) : new DoubleValue(result);
                machine.push(stackValue);
            }
        }
    }
}
