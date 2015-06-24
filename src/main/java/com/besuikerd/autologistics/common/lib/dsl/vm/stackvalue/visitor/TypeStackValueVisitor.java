package com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.visitor;

import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.*;

public class TypeStackValueVisitor extends BaseStackValueVisitor<Void, String>{
    public static final TypeStackValueVisitor instance = new TypeStackValueVisitor();

    @Override
    public String visitStackValue(StackValue value, Void aVoid) {
        return "unknown";
    }

    @Override
    public String visitIntegerValue(IntegerValue value, Void aVoid) {
        return "int";
    }

    @Override
    public String visitDoubleValue(DoubleValue value, Void aVoid) {
        return "double";
    }

    @Override
    public String visitStringValue(StringValue value, Void aVoid) {
        return "string";
    }

    @Override
    public String visitBooleanValue(BooleanValue value, Void aVoid) {
        return "boolean";
    }

    @Override
    public String visitNilValue(NilValue value, Void aVoid) {
        return "null";
    }

    @Override
    public String visitObjectValue(ObjectValue value, Void aVoid) {
        return "object";
    }

    @Override
    public String visitListValue(ListValue value, Void aVoid) {
        return "list";
    }

    @Override
    public String visitNativeFunctionValue(NativeFunctionValue value, Void aVoid) {
        return "native";
    }

    @Override
    public String visitClosureValue(ClosureValue value, Void aVoid) {
        return "closure";
    }
}
