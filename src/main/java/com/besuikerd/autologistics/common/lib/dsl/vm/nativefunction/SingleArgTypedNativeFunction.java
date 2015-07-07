package com.besuikerd.autologistics.common.lib.dsl.vm.nativefunction;

import com.besuikerd.autologistics.common.lib.dsl.vm.VirtualMachine;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.StackValue;

import java.util.List;

public abstract class SingleArgTypedNativeFunction<A extends StackValue> extends TypedNativeFunction{
    public SingleArgTypedNativeFunction(Class<A> argType) {
        super(argType);
    }

    @Override
    @SuppressWarnings("unchecked")
    public StackValue call(VirtualMachine vm, List<StackValue> args) {
        return call((A) args.get(0));
    }

    public abstract StackValue call (A arg);
}
