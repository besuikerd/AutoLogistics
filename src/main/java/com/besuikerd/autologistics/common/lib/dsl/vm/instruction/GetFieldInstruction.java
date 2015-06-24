package com.besuikerd.autologistics.common.lib.dsl.vm.instruction;

import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.ListValue;
import com.besuikerd.autologistics.common.lib.dsl.vm.VirtualMachine;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.IntegerValue;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.ObjectValue;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.StackValue;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.StringValue;

public class GetFieldInstruction implements Instruction{
    public static final GetFieldInstruction instance = new GetFieldInstruction();

    @Override
    public void execute(VirtualMachine machine) {
        StackValue index = machine.pop();
        StackValue obj = machine.pop();

        if(index instanceof StringValue && obj instanceof ObjectValue){
            StringValue key = (StringValue) index;
            ObjectValue objectValue = (ObjectValue) obj;
            StackValue value = objectValue.mapping.get(key.value);
            if(value == null){
                machine.crash("could not find key: " + key.value);
            } else{
                machine.push(value);
            }
        } else if(index instanceof IntegerValue && obj instanceof ListValue){
            IntegerValue key = (IntegerValue) index;
            ListValue listValue = (ListValue) obj;
            if(key.value >= listValue.value.size()){
                machine.crash("index out of bounds: " + key.stringRepresentation());
            } else{
                machine.push(listValue.value.get(key.value));
            }
        } else{
            machine.crash("can not get index " + index + " of " + obj);
        }
    }

    @Override
    public <ARG, RES> RES accept(InstructionVisitor<ARG, RES> visitor, ARG arg) {
        return visitor.visitGetFieldInstruction(this, arg);
    }
}
