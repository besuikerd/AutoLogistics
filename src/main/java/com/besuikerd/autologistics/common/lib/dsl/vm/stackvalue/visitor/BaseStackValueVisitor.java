package com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.visitor;


import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.*;

public abstract class BaseStackValueVisitor<ARG, RES, THROWS extends Throwable> implements StackValueVisitor<ARG, RES, THROWS>{
    @Override
    public RES visitStackValue(StackValue value, ARG arg) throws THROWS {
        return null;
    }

    @Override
    public RES visitIntegerValue(IntegerValue value, ARG arg) throws THROWS {
        return visitStackValue(value, arg);
    }

    @Override
    public RES visitDecimalValue(DecimalValue value, ARG arg) throws THROWS {
        return visitStackValue(value, arg);
    }

    @Override
    public RES visitStringValue(StringValue value, ARG arg) throws THROWS {
        return visitStackValue(value, arg);
    }

    @Override
    public RES visitBooleanValue(BooleanValue value, ARG arg) throws THROWS {
        return visitStackValue(value, arg);
    }

    @Override
    public RES visitNilValue(NilValue value, ARG arg) throws THROWS {
        return visitStackValue(value, arg);
    }

    @Override
    public RES visitRecurse(Recurse value, ARG arg) throws THROWS {
        return visitStackValue(value, arg);
    }

    @Override
    public RES visitObjectValue(ObjectValue value, ARG arg) throws THROWS {
        return visitStackValue(value, arg);
    }

    @Override
    public RES visitListValue(ListValue value, ARG arg) throws THROWS {
        return visitStackValue(value, arg);
    }

    @Override
    public RES visitNativeFunctionValue(NativeFunctionValue value, ARG arg) throws THROWS {
        return visitStackValue(value, arg);
    }

    @Override
    public RES visitClosureValue(ClosureValue value, ARG arg) throws THROWS {
        return visitStackValue(value, arg);
    }
}
