package com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.visitor;

import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.Instruction;
import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.visitor.DataOutputInstructionVisitor;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.*;

import java.io.DataOutput;
import java.io.IOException;
import java.util.Map;

public class DataOutputStackValueVisitor extends BaseStackValueVisitor<DataOutput, Void, IOException> {
    public static final DataOutputStackValueVisitor instance = new DataOutputStackValueVisitor();

    @Override
    public Void visitStackValue(StackValue value, DataOutput dataOutput) throws IOException {
        dataOutput.writeByte(value.accept(OrdinalStackValueVisitor.instance, null));
        return null;
    }

    @Override
    public Void visitIntegerValue(IntegerValue value, DataOutput dataOutput) throws IOException {
        visitStackValue(value, dataOutput);
        dataOutput.writeInt(value.value);
        return null;
    }

    @Override
    public Void visitDoubleValue(DoubleValue value, DataOutput dataOutput) throws IOException {
        visitStackValue(value, dataOutput);
        dataOutput.writeDouble(value.value);
        return null;
    }

    @Override
    public Void visitStringValue(StringValue value, DataOutput dataOutput) throws IOException {
        visitStackValue(value, dataOutput);
        dataOutput.writeUTF(value.value);
        return null;
    }

    @Override
    public Void visitBooleanValue(BooleanValue value, DataOutput dataOutput) throws IOException {
        visitStackValue(value, dataOutput);
        dataOutput.writeBoolean(value.value);
        return null;
    }

    @Override
    public Void visitNilValue(NilValue value, DataOutput dataOutput) throws IOException {
        visitStackValue(value, dataOutput);
        return null;
    }

    @Override
    public Void visitRecurse(Recurse value, DataOutput dataOutput) throws IOException {
        visitStackValue(value, dataOutput);
        return null;
    }

    @Override
    public Void visitObjectValue(ObjectValue value, DataOutput dataOutput) throws IOException {
        visitStackValue(value, dataOutput);
        dataOutput.writeInt(value.mapping.size());
        for(Map.Entry<String, StackValue> entry : value.mapping.entrySet()){
            dataOutput.writeUTF(entry.getKey());
            entry.getValue().accept(this, dataOutput);
        }
        return null;
    }

    @Override
    public Void visitListValue(ListValue value, DataOutput dataOutput) throws IOException {
        visitStackValue(value, dataOutput);
        dataOutput.writeInt(value.value.size());
        for(StackValue item : value.value){
            item.accept(this, dataOutput);
        }
        return null;
    }

    @Override
    public Void visitNativeFunctionValue(NativeFunctionValue value, DataOutput dataOutput) throws IOException {
        visitStackValue(value, dataOutput);
        dataOutput.writeUTF(value.name);
        return null;
    }

    @Override
    public Void visitClosureValue(ClosureValue value, DataOutput dataOutput) throws IOException {
        visitStackValue(value, dataOutput);

        dataOutput.writeInt(value.bindings.size());
        for(String binding : value.bindings){
            dataOutput.writeUTF(binding);
        }

        dataOutput.writeInt(value.free.size());
        for(Map.Entry<String, StackValue> entry : value.free.entrySet()){
            dataOutput.writeUTF(entry.getKey());
            entry.getValue().accept(this, dataOutput);
        }

        dataOutput.writeInt(value.body.size());
        for(Instruction instruction : value.body){
            instruction.accept(DataOutputInstructionVisitor.instance, dataOutput);
        }

        return null;
    }
}
