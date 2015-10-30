package com.besuikerd.autologistics.common.lib.dsl.vm.nativefunction;

import com.besuikerd.autologistics.common.lib.dsl.vm.VirtualMachine;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.NilValue;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.StackValue;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.StringValue;

import java.util.List;

public class NativeFunctionToString extends AbstractNativeFunction{
    public static final NativeFunctionToString instance = new NativeFunctionToString();

    @Override
    public StackValue call(VirtualMachine vm, List<StackValue> args) {
        if(ensureLength(vm, args, 1)){
            StackValue arg = args.get(0);
            return new StringValue(arg.stringRepresentation());
        }
        return NilValue.instance;
    }
}
