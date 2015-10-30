package com.besuikerd.autologistics.common.lib.dsl.vm.instruction.numeric;

import com.besuikerd.autologistics.common.lib.dsl.vm.VirtualMachine;
import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.Instruction;
import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.visitor.InstructionVisitor;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.*;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.visitor.NumericStackValueVisitor;

public class AddInstruction implements Instruction {
    public static final AddInstruction instance = new AddInstruction();

    @Override
    public void execute(VirtualMachine machine) {
        StackValue rightValue = machine.pop();
        StackValue leftValue = machine.pop();

        NumericStackValue<?> numLeft = leftValue.accept(NumericStackValueVisitor.instance, null);
        if(numLeft != null){
            NumericStackValue<?> numRight = rightValue.accept(NumericStackValueVisitor.instance, null);
            if(numRight != null){ //we can do a numeric add
                double result = numLeft.value.doubleValue() + numRight.value.doubleValue();
                StackValue stackValue = numLeft.value instanceof Integer && numRight.value instanceof Integer ? new IntegerValue((int) result) : new DecimalValue(result);
                machine.push(stackValue);
                return;
            }
        }

        //one of the sides was not numeric, string append instead
        machine.push(new StringValue(leftValue.stringRepresentation() + rightValue.stringRepresentation()));
    }

    @Override
    public <ARG, RES, THROWS extends Throwable> RES accept(InstructionVisitor<ARG, RES, THROWS> visitor, ARG arg) throws THROWS {
        return visitor.visitAddInstruction(this, arg);
    }
}
