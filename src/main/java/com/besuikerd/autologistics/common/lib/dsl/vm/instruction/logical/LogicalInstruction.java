package com.besuikerd.autologistics.common.lib.dsl.vm.instruction.logical;

import com.besuikerd.autologistics.common.lib.dsl.vm.VirtualMachine;
import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.Instruction;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.BooleanValue;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.visitor.BooleanStackValueVisitor;

public abstract class LogicalInstruction implements Instruction {
    public abstract boolean compute(Boolean left, Boolean right);

    @Override
    public void execute(VirtualMachine machine) {
        Boolean left = machine.pop().accept(BooleanStackValueVisitor.instance, null);
        if(left == null){
            machine.crash("left hand side should be a boolean value");
        } else{
            Boolean right = machine.pop().accept(BooleanStackValueVisitor.instance, null);
            if(right == null){
                machine.crash("right hand side should be a boolean value");
            } else{
                boolean result = compute(left, right);
                machine.push(new BooleanValue(result));
            }
        }
    }
}
