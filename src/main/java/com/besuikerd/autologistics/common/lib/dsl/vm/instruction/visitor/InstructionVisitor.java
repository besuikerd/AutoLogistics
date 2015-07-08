package com.besuikerd.autologistics.common.lib.dsl.vm.instruction.visitor;

import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.*;
import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.compare.*;
import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.logical.*;
import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.numeric.*;

public interface InstructionVisitor<ARG, RES, THROWS extends Throwable> {
    RES visitInstruction(Instruction instruction, ARG arg) throws THROWS;
    RES visitAddInstruction(AddInstruction instruction, ARG arg) throws THROWS;
    RES visitAndInstruction(AndInstruction instruction, ARG arg) throws THROWS;
    RES visitBranchInstruction(BranchInstruction instruction, ARG arg) throws THROWS;
    RES visitCallInstruction(CallInstruction instruction, ARG arg) throws THROWS;
    RES visitCloseScopeInstruction(CloseScopeInstruction instruction, ARG arg) throws THROWS;
    RES visitCrashInstruction(CrashInstruction instruction, ARG arg) throws THROWS;
    RES visitDivInstruction(DivInstruction instruction, ARG arg) throws THROWS;
    RES visitEQInstruction(EQInstruction instruction, ARG arg) throws THROWS;
    RES visitGetFieldInstruction(GetFieldInstruction instruction, ARG arg) throws THROWS;
    RES visitGetInstruction(GetInstruction instruction, ARG arg) throws THROWS;
    RES visitGTEInstruction(GTEInstruction instruction, ARG arg) throws THROWS;
    RES visitGTInstruction(GTInstruction instruction, ARG arg) throws THROWS;
    RES visitLoadInstruction(LoadInstruction instruction, ARG arg) throws THROWS;
    RES visitLTEInstruction(LTEInstruction instruction, ARG arg) throws THROWS;
    RES visitLTInstruction(LTInstruction instruction, ARG arg) throws THROWS;
    RES visitModInstruction(ModInstruction instruction, ARG arg) throws THROWS;
    RES visitMulInstruction(MulInstruction instruction, ARG arg) throws THROWS;
    RES visitNEQInstruction(NEQInstruction instruction, ARG arg) throws THROWS;
    RES visitOpenScopeInstruction(OpenScopeInstruction instruction, ARG arg) throws THROWS;
    RES visitOrInstruction(OrInstruction instruction, ARG arg) throws THROWS;
    RES visitPopInstruction(PopInstruction instruction, ARG arg) throws THROWS;
    RES visitPushClosureInstruction(PushClosureInstruction instruction, ARG arg) throws THROWS;
    RES visitPushInstruction(PushInstruction instruction, ARG arg) throws THROWS;
    RES visitPushListInstruction(PushListInstruction instruction, ARG arg) throws THROWS;
    RES visitPushObjectInstruction(PushObjectInstruction instruction, ARG arg) throws THROWS;
    RES visitPutFieldInstruction(PutFieldInstruction instruction, ARG arg) throws THROWS;
    RES visitPutInstruction(PutInstruction instruction, ARG arg) throws THROWS;
    RES visitRepeatedBranchInstruction(RepeatedBranchInstruction instruction, ARG arg) throws THROWS;
    RES visitSubInstruction(SubInstruction instruction, ARG arg) throws THROWS;
    RES visitDupInstruction(DupInstruction instruction, ARG arg) throws THROWS;
    RES visitNopInstruction(NopInstruction instruction, ARG arg) throws THROWS;
}