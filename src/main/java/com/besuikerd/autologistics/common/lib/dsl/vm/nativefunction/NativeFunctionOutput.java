package com.besuikerd.autologistics.common.lib.dsl.vm.nativefunction;

import com.besuikerd.autologistics.common.lib.dsl.vm.VirtualMachine;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.NilValue;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.StackValue;

import java.util.List;

public abstract class NativeFunctionOutput extends AbstractNativeFunction{
    @Override
    public StackValue call(VirtualMachine vm, List<StackValue> args) {
        if(ensureLength(vm, args, 1)){
            writeOutput(args.get(0).stringRepresentation());
        }
        return NilValue.instance;
    }

    public abstract void writeOutput(String msg);
}
