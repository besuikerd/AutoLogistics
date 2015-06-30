package com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.visitor;


import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.*;

public interface StackValueVisitor<ARG, RES, THROWS extends Throwable> {
    RES visitStackValue(StackValue value, ARG arg) throws THROWS;
    RES visitIntegerValue(IntegerValue value, ARG arg)throws THROWS;
    RES visitDoubleValue(DoubleValue value, ARG arg) throws THROWS;

    RES visitStringValue(StringValue value, ARG arg) throws THROWS;
    RES visitBooleanValue(BooleanValue value, ARG arg) throws THROWS;
    RES visitNilValue(NilValue value, ARG arg) throws THROWS;
    RES visitRecurse(Recurse value, ARG arg) throws THROWS;

    RES visitObjectValue(ObjectValue value, ARG arg) throws THROWS;
    RES visitListValue(ListValue value, ARG arg) throws THROWS;

    RES visitNativeFunctionValue(NativeFunctionValue value, ARG arg) throws THROWS;
    RES visitClosureValue(ClosureValue value, ARG arg) throws THROWS;
}
