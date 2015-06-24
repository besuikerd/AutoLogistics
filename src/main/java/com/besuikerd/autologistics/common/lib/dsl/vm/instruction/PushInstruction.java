package com.besuikerd.autologistics.common.lib.dsl.vm.instruction;

import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.visitor.InstructionVisitor;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.*;
import com.besuikerd.autologistics.common.lib.dsl.vm.VirtualMachine;

public class PushInstruction implements Instruction{
    public static final PushInstruction pushNil = new PushInstruction(NilValue.instance);
    public static final PushInstruction pushTrue = new PushInstruction(BooleanValue.trueValue);
    public static final PushInstruction pushFalse = new PushInstruction(BooleanValue.falseValue);

    public final StackValue value;

    public PushInstruction(StackValue value) {
        this.value = value;
    }

    @Override
    public void execute(VirtualMachine machine) {
        machine.push(value);
    }

    @Override
    public <ARG, RES, THROWS extends Throwable> RES accept(InstructionVisitor<ARG, RES, THROWS> visitor, ARG arg) throws THROWS {
        return visitor.visitPushInstruction(this, arg);
    }

    @Override
    public String toString() {
        return "PushInstruction(" + value + ")";
    }
}
