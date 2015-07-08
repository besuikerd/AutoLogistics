package com.besuikerd.autologistics.common.lib.dsl.vm.instruction.visitor;

import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.*;
import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.compare.*;
import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.logical.AndInstruction;
import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.logical.OrInstruction;
import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.numeric.*;

public abstract class BaseInstructionVisitor<ARG, RES, THROWS extends Throwable> implements InstructionVisitor<ARG, RES, THROWS> {
    @Override
    public RES visitInstruction(Instruction instruction, ARG arg) throws THROWS {
        return null;
    }

    @Override
    public RES visitAddInstruction(AddInstruction instruction, ARG arg) throws THROWS {
        return visitInstruction(instruction, arg);
    }

    @Override
    public RES visitAndInstruction(AndInstruction instruction, ARG arg) throws THROWS {
        return visitInstruction(instruction, arg);
    }

    @Override
    public RES visitBranchInstruction(BranchInstruction instruction, ARG arg) throws THROWS {
        return visitInstruction(instruction, arg);
    }

    @Override
    public RES visitCallInstruction(CallInstruction instruction, ARG arg) throws THROWS {
        return visitInstruction(instruction, arg);
    }

    @Override
    public RES visitCloseScopeInstruction(CloseScopeInstruction instruction, ARG arg) throws THROWS {
        return visitInstruction(instruction, arg);
    }

    @Override
    public RES visitCrashInstruction(CrashInstruction instruction, ARG arg) throws THROWS {
        return visitInstruction(instruction, arg);
    }

    @Override
    public RES visitDivInstruction(DivInstruction instruction, ARG arg) throws THROWS {
        return visitInstruction(instruction, arg);
    }

    @Override
    public RES visitEQInstruction(EQInstruction instruction, ARG arg) throws THROWS {
        return visitInstruction(instruction, arg);
    }

    @Override
    public RES visitGetFieldInstruction(GetFieldInstruction instruction, ARG arg) throws THROWS {
        return visitInstruction(instruction, arg);
    }

    @Override
    public RES visitGetInstruction(GetInstruction instruction, ARG arg) throws THROWS {
        return visitInstruction(instruction, arg);
    }

    @Override
    public RES visitGTEInstruction(GTEInstruction instruction, ARG arg) throws THROWS {
        return visitInstruction(instruction, arg);
    }

    @Override
    public RES visitGTInstruction(GTInstruction instruction, ARG arg) throws THROWS {
        return visitInstruction(instruction, arg);
    }

    @Override
    public RES visitLoadInstruction(LoadInstruction instruction, ARG arg) throws THROWS {
        return visitInstruction(instruction, arg);
    }

    @Override
    public RES visitLTEInstruction(LTEInstruction instruction, ARG arg) throws THROWS {
        return visitInstruction(instruction, arg);
    }

    @Override
    public RES visitLTInstruction(LTInstruction instruction, ARG arg) throws THROWS {
        return visitInstruction(instruction, arg);
    }

    @Override
    public RES visitModInstruction(ModInstruction instruction, ARG arg) throws THROWS {
        return visitInstruction(instruction, arg);
    }

    @Override
    public RES visitMulInstruction(MulInstruction instruction, ARG arg) throws THROWS {
        return visitInstruction(instruction, arg);
    }

    @Override
    public RES visitNEQInstruction(NEQInstruction instruction, ARG arg) throws THROWS {
        return visitInstruction(instruction, arg);
    }

    @Override
    public RES visitOpenScopeInstruction(OpenScopeInstruction instruction, ARG arg) throws THROWS {
        return visitInstruction(instruction, arg);
    }

    @Override
    public RES visitOrInstruction(OrInstruction instruction, ARG arg) throws THROWS {
        return visitInstruction(instruction, arg);
    }

    @Override
    public RES visitPopInstruction(PopInstruction instruction, ARG arg) throws THROWS {
        return visitInstruction(instruction, arg);
    }

    @Override
    public RES visitPushClosureInstruction(PushClosureInstruction instruction, ARG arg) throws THROWS {
        return visitInstruction(instruction, arg);
    }

    @Override
    public RES visitPushInstruction(PushInstruction instruction, ARG arg) throws THROWS {
        return visitInstruction(instruction, arg);
    }

    @Override
    public RES visitPushListInstruction(PushListInstruction instruction, ARG arg) throws THROWS {
        return visitInstruction(instruction, arg);
    }

    @Override
    public RES visitPushObjectInstruction(PushObjectInstruction instruction, ARG arg) throws THROWS {
        return visitInstruction(instruction, arg);
    }

    @Override
    public RES visitPutFieldInstruction(PutFieldInstruction instruction, ARG arg) throws THROWS {
        return visitInstruction(instruction, arg);
    }

    @Override
    public RES visitPutInstruction(PutInstruction instruction, ARG arg) throws THROWS {
        return visitInstruction(instruction, arg);
    }

    @Override
    public RES visitRepeatedBranchInstruction(RepeatedBranchInstruction instruction, ARG arg) throws THROWS {
        return visitInstruction(instruction, arg);
    }

    @Override
    public RES visitSubInstruction(SubInstruction instruction, ARG arg) throws THROWS {
        return visitInstruction(instruction, arg);
    }

    @Override
    public RES visitDupInstruction(DupInstruction instruction, ARG arg) throws THROWS {
        return visitInstruction(instruction, arg);
    }

    @Override
    public RES visitNopInstruction(NopInstruction instruction, ARG arg) throws THROWS {
        return visitInstruction(instruction, arg);
    }
}
