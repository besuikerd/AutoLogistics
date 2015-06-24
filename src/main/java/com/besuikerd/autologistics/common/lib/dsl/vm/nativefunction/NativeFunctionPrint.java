package com.besuikerd.autologistics.common.lib.dsl.vm.nativefunction;

public class NativeFunctionPrint extends NativeFunctionOutput{
    public static final NativeFunctionPrint instance = new NativeFunctionPrint();

    @Override
    public void writeOutput(String msg) {
        System.out.print(msg);
    }
}
