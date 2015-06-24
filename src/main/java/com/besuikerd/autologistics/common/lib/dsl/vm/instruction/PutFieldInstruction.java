package com.besuikerd.autologistics.common.lib.dsl.vm.instruction;

import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.visitor.InstructionVisitor;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.NilValue;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.ListValue;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.ObjectValue;
import com.besuikerd.autologistics.common.lib.dsl.vm.VirtualMachine;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.IntegerValue;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.StackValue;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.StringValue;

public class PutFieldInstruction implements Instruction{
    public static final PutFieldInstruction instance = new PutFieldInstruction();

    @Override
    public void execute(VirtualMachine machine) {
        StackValue value = machine.pop();
        StackValue index = machine.pop();
        StackValue obj = machine.pop();

        if(index instanceof StringValue && obj instanceof ObjectValue){
            StringValue key = (StringValue) index;
            ObjectValue objectValue = (ObjectValue) obj;
            objectValue.mapping.put(key.value, value);
        } else if(index instanceof IntegerValue && obj instanceof ListValue){
            IntegerValue key = (IntegerValue) index;
            ListValue listValue = (ListValue) obj;
            for(int i = listValue.value.size() ; i < key.value ; i++){
                listValue.value.add(NilValue.instance);
            }
            listValue.value.add(value);
        } else{
            machine.crash("cannot assign field with index " + index.stringRepresentation() + " to " + obj.stringRepresentation());
        }
    }

    @Override
    public <ARG, RES, THROWS extends Throwable> RES accept(InstructionVisitor<ARG, RES, THROWS> visitor, ARG arg) throws THROWS {
        return visitor.visitPutFieldInstruction(this, arg);
    }
}
