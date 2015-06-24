package com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.visitor;


import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.*;

public abstract class BaseStackValueVisitor<ARG, RES> implements StackValueVisitor<ARG, RES>{
    @Override
    public RES visitStackValue(StackValue value, ARG arg) {
        return null;
    }

    @Override
    public RES visitIntegerValue(IntegerValue value, ARG arg) {
        return visitStackValue(value, arg);
    }

    @Override
    public RES visitDoubleValue(DoubleValue value, ARG arg) {
        return visitStackValue(value, arg);
    }

    @Override
    public RES visitStringValue(StringValue value, ARG arg) {
        return visitStackValue(value, arg);
    }

    @Override
    public RES visitBooleanValue(BooleanValue value, ARG arg) {
        return visitStackValue(value, arg);
    }

    @Override
    public RES visitNilValue(NilValue value, ARG arg) {
        return visitStackValue(value, arg);
    }

    @Override
    public RES visitRecurse(Recurse value, ARG arg) {
        return visitStackValue(value, arg);
    }

    @Override
    public RES visitObjectValue(ObjectValue value, ARG arg) {
        return visitStackValue(value, arg);
    }

    @Override
    public RES visitListValue(ListValue value, ARG arg) {
        return visitStackValue(value, arg);
    }

    @Override
    public RES visitNativeFunctionValue(NativeFunctionValue value, ARG arg) {
        return visitStackValue(value, arg);
    }

    @Override
    public RES visitClosureValue(ClosureValue value, ARG arg) {
        return visitStackValue(value, arg);
    }
}
