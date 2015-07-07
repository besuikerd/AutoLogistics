package com.besuikerd.autologistics.common.lib.dsl.vm.nativefunction;

import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.BooleanValue;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.StackValue;

public class NativeFunctionNegateBoolean extends SingleArgTypedNativeFunction<BooleanValue> {
    public static final NativeFunctionNegateBoolean instance = new NativeFunctionNegateBoolean();

    public NativeFunctionNegateBoolean(){
        super(BooleanValue.class);
    }

    @Override
    public StackValue call(BooleanValue arg) {
        return arg.value ? BooleanValue.falseValue : BooleanValue.trueValue;
    }
}
