package com.besuikerd.autologistics.common.lib.dsl.vm.nativefunction;

import com.besuikerd.autologistics.common.lib.dsl.vm.VirtualMachine;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.StackValue;
import java.util.List;

public interface NativeFunction {
    boolean matchesSignature(List<StackValue> args);
    StackValue call(VirtualMachine vm, List<StackValue> args);
}