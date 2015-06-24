package com.besuikerd.autologistics.common.lib.dsl.vm.nativefunction;

import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.StackValue;
import com.besuikerd.autologistics.common.lib.dsl.vm.VirtualMachine;

import java.util.List;

public abstract class AbstractNativeFunction implements NativeFunction{
    boolean ensureLength(VirtualMachine vm, List<StackValue> args, int length) {
        if(args.size() != length){
            vm.crash("Expected number of arguments: " + length + ", got: " + args.size());
            return false;
        }
        return true;
    }
}
