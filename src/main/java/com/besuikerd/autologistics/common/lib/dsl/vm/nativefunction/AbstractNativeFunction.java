package com.besuikerd.autologistics.common.lib.dsl.vm.nativefunction;

import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.ObjectValue;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.StackValue;
import com.besuikerd.autologistics.common.lib.dsl.vm.VirtualMachine;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.StackValues;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.visitor.TypeStackValueVisitor;

import java.util.List;

public abstract class AbstractNativeFunction implements NativeFunction{
    protected boolean ensureLength(VirtualMachine vm, List<StackValue> args, int length) {
        if(args.size() != length){
            vm.crash("Expected number of arguments: " + length + ", got: " + args.size());
            return false;
        }
        return true;
    }

    protected <A extends StackValue> A expectType(VirtualMachine vm, Class<A> cls, StackValue value){
        return StackValues.expectType(vm, cls, value);
    }

    protected <A extends StackValue> A extractField(VirtualMachine vm, Class<A> cls, String field, ObjectValue obj){
        return StackValues.extractField(vm, cls, field, obj);
    }
}