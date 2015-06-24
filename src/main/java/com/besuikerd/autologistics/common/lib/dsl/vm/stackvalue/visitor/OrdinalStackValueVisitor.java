package com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.visitor;

import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.*;
import static com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.StackValues.*;

public class OrdinalStackValueVisitor implements StackValueVisitor<Void, Integer, RuntimeException>{
    public static final OrdinalStackValueVisitor instance = new OrdinalStackValueVisitor();

    @Override
    public Integer visitStackValue(StackValue value, Void aVoid) {
        return -1;
    }

    @Override
    public Integer visitIntegerValue(IntegerValue value, Void aVoid) {
        return INT.ordinal();
    }

    @Override
    public Integer visitDoubleValue(DoubleValue value, Void aVoid) {
        return DOUBLE.ordinal();
    }

    @Override
    public Integer visitStringValue(StringValue value, Void aVoid) {
        return STRING.ordinal();
    }

    @Override
    public Integer visitBooleanValue(BooleanValue value, Void aVoid) {
        return BOOLEAN.ordinal();
    }

    @Override
    public Integer visitNilValue(NilValue value, Void aVoid) {
        return NIL.ordinal();
    }

    @Override
    public Integer visitRecurse(Recurse value, Void aVoid) {
        return RECURSE.ordinal();
    }

    @Override
    public Integer visitObjectValue(ObjectValue value, Void aVoid) {
        return OBJECT.ordinal();
    }

    @Override
    public Integer visitListValue(ListValue value, Void aVoid) {
        return LIST.ordinal();
    }

    @Override
    public Integer visitNativeFunctionValue(NativeFunctionValue value, Void aVoid) {
        return NATIVE.ordinal();
    }

    @Override
    public Integer visitClosureValue(ClosureValue value, Void aVoid) {
        return CLOSURE.ordinal();
    }
}
