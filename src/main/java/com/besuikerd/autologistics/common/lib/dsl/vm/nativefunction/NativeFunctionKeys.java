package com.besuikerd.autologistics.common.lib.dsl.vm.nativefunction;

import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.*;
import com.besuikerd.autologistics.common.lib.dsl.vm.VirtualMachine;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.visitor.TypeStackValueVisitor;

import java.util.ArrayList;
import java.util.List;

public class NativeFunctionKeys extends AbstractNativeFunction{
    public static final NativeFunctionKeys instance = new NativeFunctionKeys();

    @Override
    public StackValue call(VirtualMachine vm, List<StackValue> args) {
        if(ensureLength(vm, args, 1)){
            StackValue arg = args.get(0);
            if(arg instanceof ObjectValue){
                ObjectValue obj = (ObjectValue) arg;
                List<StackValue> keys = new ArrayList<StackValue>(obj.mapping.size());
                for(String key: obj.mapping.keySet()){
                    keys.add(new StringValue((key)));
                }
                return new ListValue(keys);
            } else{
                vm.crash("Expected type: object, got: " + arg.accept(TypeStackValueVisitor.instance, null));
            }
        }
        return NilValue.instance;
    }
}
