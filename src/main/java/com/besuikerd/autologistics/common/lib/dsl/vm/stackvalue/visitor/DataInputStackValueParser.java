package com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.visitor;

import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.Instruction;
import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.visitor.DataInputInstructionParser;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.*;

import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.StackValues;

import java.io.DataInput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataInputStackValueParser {
    public static StackValue parse(DataInput input) throws IOException{
        int ordinal = input.readByte();
        if(ordinal >= 0 && ordinal < StackValues.values().length) {
            int length;
            switch (StackValues.values()[ordinal]) {
                case BOOLEAN:
                    return input.readBoolean() ? BooleanValue.trueValue : BooleanValue.falseValue;
                case INT:
                    return new IntegerValue(input.readInt());
                case DOUBLE:
                    return new DoubleValue(input.readDouble());
                case NIL:
                    return NilValue.instance;
                case STRING:
                    return new StringValue(input.readUTF());
                case LIST:
                    length = input.readInt();
                    List<StackValue> list = new ArrayList<StackValue>(length);
                    for (int i = 0; i < length; i++) {
                        list.add(parse(input));
                    }
                    return new ListValue(list);
                case OBJECT:
                    length = input.readInt();
                    Map<String, StackValue> mapping = new HashMap<String, StackValue>();
                    for (int i = 0; i < length; i++) {
                        mapping.put(input.readUTF(), parse(input));
                    }
                    return new ObjectValue(mapping);
                case RECURSE:
                    return Recurse.instance;
                case NATIVE:
                    return new NativeFunctionValue(input.readUTF());
                case CLOSURE:
                    length = input.readInt();
                    List<String> bindings = new ArrayList<String>(length);
                    for (int i = 0; i < length; i++) {
                        bindings.add(input.readUTF());
                    }

                    length = input.readInt();
                    Map<String, StackValue> free = new HashMap<String, StackValue>();
                    for (int i = 0; i < length; i++) {
                        String key = input.readUTF();
                        StackValue value = parse(input);
                        free.put(key, value);
                    }

                    length = input.readInt();
                    List<Instruction> body = new ArrayList<Instruction>(length);
                    for (int i = 0; i < length; i++) {
                        body.add(DataInputInstructionParser.parse(input));
                    }
                    return new ClosureValue(bindings, free, body);
                default:
                    throw new IllegalArgumentException("unknown stackvalue with ordinal value " + ordinal);
            }
        } else{
            return null;
        }
    }
}