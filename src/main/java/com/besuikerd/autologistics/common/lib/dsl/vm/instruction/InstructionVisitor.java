package com.besuikerd.autologistics.common.lib.dsl.vm.instruction;

import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.compare.*;
import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.logical.*;
import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.numeric.*;

public interface InstructionVisitor<ARG, RES> {
    RES visitInstruction(Instruction instruction, ARG arg);
    RES visitAddInstruction(AddInstruction instruction, ARG arg);
    RES visitAndInstruction(AndInstruction instruction, ARG arg);
    RES visitBranchInstruction(BranchInstruction instruction, ARG arg);
    RES visitCallInstruction(CallInstruction instruction, ARG arg);
    RES visitCloseScopeInstruction(CloseScopeInstruction instruction, ARG arg);
    RES visitCrashInstruction(CrashInstruction instruction, ARG arg);
    RES visitDivInstruction(DivInstruction instruction, ARG arg);
    RES visitEQInstruction(EQInstruction instruction, ARG arg);
    RES visitGetFieldInstruction(GetFieldInstruction instruction, ARG arg);
    RES visitGetInstruction(GetInstruction instruction, ARG arg);
    RES visitGTEInstruction(GTEInstruction instruction, ARG arg);
    RES visitGTInstruction(GTInstruction instruction, ARG arg);
    RES visitLoadInstruction(LoadInstruction instruction, ARG arg);
    RES visitLTEInstruction(LTEInstruction instruction, ARG arg);
    RES visitLTInstruction(LTInstruction instruction, ARG arg);
    RES visitModInstruction(ModInstruction instruction, ARG arg);
    RES visitMulInstruction(MulInstruction instruction, ARG arg);
    RES visitNEQInstruction(NEQInstruction instruction, ARG arg);
    RES visitOpenScopeInstruction(OpenScopeInstruction instruction, ARG arg);
    RES visitOrInstruction(OrInstruction instruction, ARG arg);
    RES visitPopInstruction(PopInstruction instruction, ARG arg);
    RES visitPushClosureInstruction(PushClosureInstruction instruction, ARG arg);
    RES visitPushInstruction(PushInstruction instruction, ARG arg);
    RES visitPushListInstruction(PushListInstruction instruction, ARG arg);
    RES visitPushObjectInstruction(PushObjectInstruction instruction, ARG arg);
    RES visitPutFieldInstruction(PutFieldInstruction instruction, ARG arg);
    RES visitPutInstruction(PutInstruction instruction, ARG arg);
    RES visitRepeatedBranchInstruction(RepeatedBranchInstruction instruction, ARG arg);
    RES visitSubInstruction(SubInstruction instruction, ARG arg);
}