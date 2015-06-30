package com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.visitor;

import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.*;

public class SafeBaseStackValueVisitor<ARG, RES> extends BaseStackValueVisitor<ARG, RES, RuntimeException>{
    @Override
    public RES visitStackValue(StackValue value, ARG arg){
        return super.visitStackValue(value, arg);
    }

    @Override
    public RES visitIntegerValue(IntegerValue value, ARG arg) {
        return super.visitIntegerValue(value, arg);
    }

    @Override
    public RES visitDoubleValue(DoubleValue value, ARG arg) {
        return super.visitDoubleValue(value, arg);
    }

    @Override
    public RES visitStringValue(StringValue value, ARG arg) {
        return super.visitStringValue(value, arg);
    }

    @Override
    public RES visitBooleanValue(BooleanValue value, ARG arg) {
        return super.visitBooleanValue(value, arg);
    }

    @Override
    public RES visitNilValue(NilValue value, ARG arg) {
        return super.visitNilValue(value, arg);
    }

    @Override
    public RES visitRecurse(Recurse value, ARG arg) {
        return super.visitRecurse(value, arg);
    }

    @Override
    public RES visitObjectValue(ObjectValue value, ARG arg) {
        return super.visitObjectValue(value, arg);
    }

    @Override
    public RES visitListValue(ListValue value, ARG arg) {
        return super.visitListValue(value, arg);
    }

    @Override
    public RES visitNativeFunctionValue(NativeFunctionValue value, ARG arg) {
        return super.visitNativeFunctionValue(value, arg);
    }

    @Override
    public RES visitClosureValue(ClosureValue value, ARG arg) {
        return super.visitClosureValue(value, arg);
    }
}
