package com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.visitor;


import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.*;

public interface StackValueVisitor<ARG, RES> {
    RES visitStackValue(StackValue value, ARG arg);
    RES visitIntegerValue(IntegerValue value, ARG arg);
    RES visitDoubleValue(DoubleValue value, ARG arg);

    RES visitStringValue(StringValue value, ARG arg);
    RES visitBooleanValue(BooleanValue value, ARG arg);
    RES visitNilValue(NilValue value, ARG arg);
    RES visitRecurse(Recurse value, ARG arg);

    RES visitObjectValue(ObjectValue value, ARG arg);
    RES visitListValue(ListValue value, ARG arg);

    RES visitNativeFunctionValue(NativeFunctionValue value, ARG arg);
    RES visitClosureValue(ClosureValue value, ARG arg);
}
