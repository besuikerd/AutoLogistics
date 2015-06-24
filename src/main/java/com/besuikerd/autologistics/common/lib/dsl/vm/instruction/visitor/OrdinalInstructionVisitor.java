package com.besuikerd.autologistics.common.lib.dsl.vm.instruction.visitor;

import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.*;
import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.compare.*;
import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.logical.AndInstruction;
import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.logical.OrInstruction;
import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.numeric.*;
import static com.besuikerd.autologistics.common.lib.dsl.vm.instruction.Instructions.*;

public class OrdinalInstructionVisitor extends SafeBaseInstructionVisitor<Void, Integer>{
    public static final OrdinalInstructionVisitor instance = new OrdinalInstructionVisitor();

    @Override
    public Integer visitInstruction(Instruction instruction, Void aVoid) {
        return -1;
    }

    @Override
    public Integer visitAddInstruction(AddInstruction instruction, Void aVoid) {
        return ADD.ordinal();
    }

    @Override
    public Integer visitAndInstruction(AndInstruction instruction, Void aVoid) {
        return AND.ordinal();
    }

    @Override
    public Integer visitBranchInstruction(BranchInstruction instruction, Void aVoid) {
        return BRANCH.ordinal();
    }

    @Override
    public Integer visitCallInstruction(CallInstruction instruction, Void aVoid) {
        return CALL.ordinal();
    }

    @Override
    public Integer visitCloseScopeInstruction(CloseScopeInstruction instruction, Void aVoid) {
        return CLOSE_SCOPE.ordinal();
    }

    @Override
    public Integer visitCrashInstruction(CrashInstruction instruction, Void aVoid) {
        return CRASH.ordinal();
    }

    @Override
    public Integer visitDivInstruction(DivInstruction instruction, Void aVoid) {
        return DIV.ordinal();
    }

    @Override
    public Integer visitEQInstruction(EQInstruction instruction, Void aVoid) {
        return EQ.ordinal();
    }

    @Override
    public Integer visitGetFieldInstruction(GetFieldInstruction instruction, Void aVoid) {
        return GET_FIELD.ordinal();
    }

    @Override
    public Integer visitGetInstruction(GetInstruction instruction, Void aVoid) {
        return GET.ordinal();
    }

    @Override
    public Integer visitGTEInstruction(GTEInstruction instruction, Void aVoid) {
        return GTE.ordinal();
    }

    @Override
    public Integer visitGTInstruction(GTInstruction instruction, Void aVoid) {
        return GT.ordinal();
    }

    @Override
    public Integer visitLoadInstruction(LoadInstruction instruction, Void aVoid) {
        return LOAD.ordinal();
    }

    @Override
    public Integer visitLTEInstruction(LTEInstruction instruction, Void aVoid) {
        return LTE.ordinal();
    }

    @Override
    public Integer visitLTInstruction(LTInstruction instruction, Void aVoid) {
        return LT.ordinal();
    }

    @Override
    public Integer visitModInstruction(ModInstruction instruction, Void aVoid) {
        return MOD.ordinal();
    }

    @Override
    public Integer visitMulInstruction(MulInstruction instruction, Void aVoid) {
        return MUL.ordinal();
    }

    @Override
    public Integer visitNEQInstruction(NEQInstruction instruction, Void aVoid) {
        return NEQ.ordinal();
    }

    @Override
    public Integer visitOpenScopeInstruction(OpenScopeInstruction instruction, Void aVoid) {
        return OPEN_SCOPE.ordinal();
    }

    @Override
    public Integer visitOrInstruction(OrInstruction instruction, Void aVoid) {
        return OR.ordinal();
    }

    @Override
    public Integer visitPopInstruction(PopInstruction instruction, Void aVoid) {
        return POP.ordinal();
    }

    @Override
    public Integer visitPushClosureInstruction(PushClosureInstruction instruction, Void aVoid) {
        return PUSH_CLOSURE.ordinal();
    }

    @Override
    public Integer visitPushInstruction(PushInstruction instruction, Void aVoid) {
        return PUSH.ordinal();
    }

    @Override
    public Integer visitPushListInstruction(PushListInstruction instruction, Void aVoid) {
        return PUSH_LIST.ordinal();
    }

    @Override
    public Integer visitPushObjectInstruction(PushObjectInstruction instruction, Void aVoid) {
        return PUSH_OBJECT.ordinal();
    }

    @Override
    public Integer visitPutFieldInstruction(PutFieldInstruction instruction, Void aVoid) {
        return PUT_FIELD.ordinal();
    }

    @Override
    public Integer visitPutInstruction(PutInstruction instruction, Void aVoid) {
        return PUT.ordinal();
    }

    @Override
    public Integer visitRepeatedBranchInstruction(RepeatedBranchInstruction instruction, Void aVoid) {
        return REPEATED_BRANCH.ordinal();
    }

    @Override
    public Integer visitSubInstruction(SubInstruction instruction, Void aVoid) {
        return SUB.ordinal();
    }
}
