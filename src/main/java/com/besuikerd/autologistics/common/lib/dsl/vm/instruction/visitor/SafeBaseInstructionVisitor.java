package com.besuikerd.autologistics.common.lib.dsl.vm.instruction.visitor;

import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.*;
import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.compare.*;
import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.logical.AndInstruction;
import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.logical.OrInstruction;
import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.numeric.*;

public abstract class SafeBaseInstructionVisitor<ARG, RES> extends BaseInstructionVisitor<ARG, RES, RuntimeException> {
    @Override
    public RES visitInstruction(Instruction instruction, ARG arg) {
        return super.visitInstruction(instruction, arg);
    }

    @Override
    public RES visitAddInstruction(AddInstruction instruction, ARG arg) {
        return super.visitAddInstruction(instruction, arg);
    }

    @Override
    public RES visitAndInstruction(AndInstruction instruction, ARG arg) {
        return super.visitAndInstruction(instruction, arg);
    }

    @Override
    public RES visitBranchInstruction(BranchInstruction instruction, ARG arg) {
        return super.visitBranchInstruction(instruction, arg);
    }

    @Override
    public RES visitCallInstruction(CallInstruction instruction, ARG arg) {
        return super.visitCallInstruction(instruction, arg);
    }

    @Override
    public RES visitCloseScopeInstruction(CloseScopeInstruction instruction, ARG arg) {
        return super.visitCloseScopeInstruction(instruction, arg);
    }

    @Override
    public RES visitCrashInstruction(CrashInstruction instruction, ARG arg) {
        return super.visitCrashInstruction(instruction, arg);
    }

    @Override
    public RES visitDivInstruction(DivInstruction instruction, ARG arg) {
        return super.visitDivInstruction(instruction, arg);
    }

    @Override
    public RES visitEQInstruction(EQInstruction instruction, ARG arg) {
        return super.visitEQInstruction(instruction, arg);
    }

    @Override
    public RES visitGetFieldInstruction(GetFieldInstruction instruction, ARG arg) {
        return super.visitGetFieldInstruction(instruction, arg);
    }

    @Override
    public RES visitGetInstruction(GetInstruction instruction, ARG arg) {
        return super.visitGetInstruction(instruction, arg);
    }

    @Override
    public RES visitGTEInstruction(GTEInstruction instruction, ARG arg) {
        return super.visitGTEInstruction(instruction, arg);
    }

    @Override
    public RES visitGTInstruction(GTInstruction instruction, ARG arg) {
        return super.visitGTInstruction(instruction, arg);
    }

    @Override
    public RES visitLoadInstruction(LoadInstruction instruction, ARG arg) {
        return super.visitLoadInstruction(instruction, arg);
    }

    @Override
    public RES visitLTEInstruction(LTEInstruction instruction, ARG arg) {
        return super.visitLTEInstruction(instruction, arg);
    }

    @Override
    public RES visitLTInstruction(LTInstruction instruction, ARG arg) {
        return super.visitLTInstruction(instruction, arg);
    }

    @Override
    public RES visitModInstruction(ModInstruction instruction, ARG arg) {
        return super.visitModInstruction(instruction, arg);
    }

    @Override
    public RES visitMulInstruction(MulInstruction instruction, ARG arg) {
        return super.visitMulInstruction(instruction, arg);
    }

    @Override
    public RES visitNEQInstruction(NEQInstruction instruction, ARG arg) {
        return super.visitNEQInstruction(instruction, arg);
    }

    @Override
    public RES visitOpenScopeInstruction(OpenScopeInstruction instruction, ARG arg) {
        return super.visitOpenScopeInstruction(instruction, arg);
    }

    @Override
    public RES visitOrInstruction(OrInstruction instruction, ARG arg) {
        return super.visitOrInstruction(instruction, arg);
    }

    @Override
    public RES visitPopInstruction(PopInstruction instruction, ARG arg) {
        return super.visitPopInstruction(instruction, arg);
    }

    @Override
    public RES visitPushClosureInstruction(PushClosureInstruction instruction, ARG arg) {
        return super.visitPushClosureInstruction(instruction, arg);
    }

    @Override
    public RES visitPushInstruction(PushInstruction instruction, ARG arg) {
        return super.visitPushInstruction(instruction, arg);
    }

    @Override
    public RES visitPushListInstruction(PushListInstruction instruction, ARG arg) {
        return super.visitPushListInstruction(instruction, arg);
    }

    @Override
    public RES visitPushObjectInstruction(PushObjectInstruction instruction, ARG arg) {
        return super.visitPushObjectInstruction(instruction, arg);
    }

    @Override
    public RES visitPutFieldInstruction(PutFieldInstruction instruction, ARG arg) {
        return super.visitPutFieldInstruction(instruction, arg);
    }

    @Override
    public RES visitPutInstruction(PutInstruction instruction, ARG arg) {
        return super.visitPutInstruction(instruction, arg);
    }

    @Override
    public RES visitRepeatedBranchInstruction(RepeatedBranchInstruction instruction, ARG arg) {
        return super.visitRepeatedBranchInstruction(instruction, arg);
    }

    @Override
    public RES visitSubInstruction(SubInstruction instruction, ARG arg) {
        return super.visitSubInstruction(instruction, arg);
    }
}
