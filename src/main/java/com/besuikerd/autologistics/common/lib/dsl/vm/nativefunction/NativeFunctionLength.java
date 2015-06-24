package com.besuikerd.autologistics.common.lib.dsl.vm.nativefunction;

import com.besuikerd.autologistics.common.lib.dsl.vm.VirtualMachine;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.IntegerValue;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.ListValue;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.NilValue;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.StackValue;

import java.util.List;

public class NativeFunctionLength extends AbstractNativeFunction{
    public static final NativeFunctionLength instance = new NativeFunctionLength();

    @Override
    public StackValue call(VirtualMachine vm, List<StackValue> args) {
        if(ensureLength(vm, args, 1)){
            StackValue list = args.get(0);
            if(list instanceof ListValue){
                return new IntegerValue(((ListValue) list).value.size());
            } else {
                vm.crash("cannot get length of " + list.stringRepresentation());
            }
        }
        return NilValue.instance;
    }
}
