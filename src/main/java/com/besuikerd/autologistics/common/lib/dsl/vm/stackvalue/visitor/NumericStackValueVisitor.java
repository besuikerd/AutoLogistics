package com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.visitor;

import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.DecimalValue;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.IntegerValue;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.NumericStackValue;

public class NumericStackValueVisitor extends BaseStackValueVisitor<Void, NumericStackValue<?>, RuntimeException> {
    public static final NumericStackValueVisitor instance = new NumericStackValueVisitor();

    @Override
    public NumericStackValue visitDecimalValue(DecimalValue value, Void arg) {
        return value;
    }

    @Override
    public NumericStackValue visitIntegerValue(IntegerValue value, Void arg) {
        return value;
    }
}
