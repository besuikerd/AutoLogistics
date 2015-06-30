package com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.visitor;

import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.*;
import static com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.StackValues.*;

public class TypeStackValueVisitor extends BaseStackValueVisitor<Void, String, RuntimeException>{
    public static final TypeStackValueVisitor instance = new TypeStackValueVisitor();

    @Override
    public String visitStackValue(StackValue value, Void aVoid) {
        return "unknown";
    }

    @Override
    public String visitIntegerValue(IntegerValue value, Void aVoid) {
        return INT.type;
    }

    @Override
    public String visitDoubleValue(DoubleValue value, Void aVoid) {
        return DOUBLE.type;
    }

    @Override
    public String visitStringValue(StringValue value, Void aVoid) {
        return STRING.type;
    }

    @Override
    public String visitBooleanValue(BooleanValue value, Void aVoid) {
        return BOOLEAN.type;
    }

    @Override
    public String visitNilValue(NilValue value, Void aVoid) {
        return NIL.type;
    }

    @Override
    public String visitObjectValue(ObjectValue value, Void aVoid) {
        return OBJECT.type;
    }

    @Override
    public String visitRecurse(Recurse value, Void aVoid) {
        return RECURSE.type;
    }

    @Override
    public String visitListValue(ListValue value, Void aVoid) {
        return LIST.type;
    }

    @Override
    public String visitNativeFunctionValue(NativeFunctionValue value, Void aVoid) {
        return NATIVE.type;
    }

    @Override
    public String visitClosureValue(ClosureValue value, Void aVoid) {
        return CLOSURE.type;
    }
}
