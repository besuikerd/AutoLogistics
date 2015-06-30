package com.besuikerd.autologistics.common.lib.dsl.vm.nativefunction;

import com.besuikerd.autologistics.common.lib.dsl.vm.VirtualMachine;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.NilValue;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.StackValue;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.StringValue;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.visitor.TypeStackValueVisitor;

import java.util.List;

public class NativeFunctionType extends AbstractNativeFunction{
    public static final NativeFunctionType instance = new NativeFunctionType();

    @Override
    public StackValue call(VirtualMachine vm, List<StackValue> args) {
        if(ensureLength(vm, args, 1)){
            return new StringValue(args.get(0).accept(TypeStackValueVisitor.instance, null));
        }
        return NilValue.instance;
    }
}
