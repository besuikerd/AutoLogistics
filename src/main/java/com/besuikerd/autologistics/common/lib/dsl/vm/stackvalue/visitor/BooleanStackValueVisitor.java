package com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.visitor;


import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.BooleanValue;

public class BooleanStackValueVisitor extends BaseStackValueVisitor<Void, Boolean, RuntimeException>{
    public static final BooleanStackValueVisitor instance = new BooleanStackValueVisitor();

    @Override
    public Boolean visitBooleanValue(BooleanValue value, Void aVoid) {
        return value.value;
    }
}
