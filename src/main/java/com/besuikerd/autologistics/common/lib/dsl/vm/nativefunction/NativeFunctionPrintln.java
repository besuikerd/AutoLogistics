package com.besuikerd.autologistics.common.lib.dsl.vm.nativefunction;

public class NativeFunctionPrintln extends NativeFunctionOutput{
    public static final NativeFunctionPrintln instance = new NativeFunctionPrintln();

    @Override
    public void writeOutput(String msg) {
        System.out.println(msg);
    }
}
