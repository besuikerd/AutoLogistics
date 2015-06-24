package com.besuikerd.autologistics.common.lib.dsl.vm;

import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.Instruction;
import com.besuikerd.autologistics.common.lib.dsl.vm.nativefunction.NativeFunction;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.StackValue;

import java.util.Collection;
import java.util.List;

public interface VirtualMachine {

    void push(StackValue value);
    StackValue pop();
    List<StackValue> pop(int n);

    void reset();
    void load(Collection<Instruction> instructions);
    void run(int n);
    void cycle();

    void pushInstruction(Instruction instruction);
    void pushInstructions(Collection<Instruction> instructions);
    Instruction popInstruction();

    void openScope();
    void closeScope();

    void crash(String message);

    StackValue get(String name);
    void put(String name, StackValue value, boolean isLocal);

    void addNative(String name, NativeFunction f);
    void addGlobal(String name, StackValue value);
    NativeFunction getNative(String name);

    boolean isTerminated();
    boolean isErrorState();
    String getErrorMessage();
}