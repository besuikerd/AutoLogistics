package com.besuikerd.autologistics.common.lib.dsl.vm.instruction.visitor;
import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.*;
import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.compare.*;
import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.logical.AndInstruction;
import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.logical.OrInstruction;
import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.numeric.*;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.visitor.DataOutputStackValueVisitor;

import java.io.DataOutput;
import java.io.IOException;

public class DataOutputInstructionVisitor extends BaseInstructionVisitor<DataOutput, Void, IOException>{
    public static final DataOutputInstructionVisitor instance = new DataOutputInstructionVisitor();

    @Override
    public Void visitInstruction(Instruction instruction, DataOutput dataOutput) throws IOException {
        int ordinal = instruction.accept(OrdinalInstructionVisitor.instance, null);
        dataOutput.write(ordinal);
        return null;
    }

    @Override
    public Void visitBranchInstruction(BranchInstruction instruction, DataOutput dataOutput) throws IOException {
        visitInstruction(instruction, dataOutput);
        dataOutput.writeInt(instruction.branchTrue.size());
        for(Instruction i : instruction.branchTrue){
            i.accept(this, dataOutput);
        }

        dataOutput.writeInt((instruction.branchFalse.size()));
        for(Instruction i : instruction.branchFalse){
            i.accept(this, dataOutput);
        }

        return null;
    }

    @Override
    public Void visitRepeatedBranchInstruction(RepeatedBranchInstruction instruction, DataOutput dataOutput) throws IOException {
        visitInstruction(instruction, dataOutput);
        dataOutput.writeInt(instruction.branchWhile.size());
        for(Instruction i : instruction.branchWhile){
            i.accept(this, dataOutput);
        }
        return null;
    }

    @Override
    public Void visitCallInstruction(CallInstruction instruction, DataOutput dataOutput) throws IOException {
        visitInstruction(instruction, dataOutput);
        dataOutput.writeInt(instruction.argCount);
        return null;
    }

    @Override
    public Void visitCrashInstruction(CrashInstruction instruction, DataOutput dataOutput) throws IOException {
        visitInstruction(instruction, dataOutput);
        dataOutput.writeUTF(instruction.message);
        return null;
    }

    @Override
    public Void visitGetInstruction(GetInstruction instruction, DataOutput dataOutput) throws IOException {
        visitInstruction(instruction, dataOutput);
        dataOutput.writeUTF(instruction.name);
        return null;
    }

    @Override
    public Void visitLoadInstruction(LoadInstruction instruction, DataOutput dataOutput) throws IOException {
        visitInstruction(instruction, dataOutput);
        dataOutput.writeInt(instruction.instructions.size());
        for(Instruction i : instruction.instructions){
            i.accept(this, dataOutput);
        }
        return null;
    }

    @Override
    public Void visitPushClosureInstruction(PushClosureInstruction instruction, DataOutput dataOutput) throws IOException {
        visitInstruction(instruction, dataOutput);
        dataOutput.writeUTF(instruction.name == null ? "_" : instruction.name);

        dataOutput.writeInt(instruction.bindings.size());
        for(String binding : instruction.bindings){
            dataOutput.writeUTF(binding);
        }

        dataOutput.writeInt(instruction.free.size());
        for(String free : instruction.free){
            dataOutput.writeUTF(free);
        }

        dataOutput.writeInt(instruction.body.size());
        for(Instruction i : instruction.body){
            i.accept(this, dataOutput);
        }

        return null;
    }

    @Override
    public Void visitPushInstruction(PushInstruction instruction, DataOutput dataOutput) throws IOException {
        visitInstruction(instruction, dataOutput);
        instruction.value.accept(DataOutputStackValueVisitor.instance, dataOutput);
        return null;
    }

    @Override
    public Void visitPushListInstruction(PushListInstruction instruction, DataOutput dataOutput) throws IOException {
        visitInstruction(instruction, dataOutput);
        dataOutput.writeInt(instruction.length);
        return null;
    }

    @Override
    public Void visitPushObjectInstruction(PushObjectInstruction instruction, DataOutput dataOutput) throws IOException {
        visitInstruction(instruction, dataOutput);
        dataOutput.writeInt(instruction.keys.size());
        for(String key : instruction.keys){
            dataOutput.writeUTF(key);
        }
        return null;
    }

    @Override
    public Void visitPutInstruction(PutInstruction instruction, DataOutput dataOutput) throws IOException {
        visitInstruction(instruction, dataOutput);
        dataOutput.writeUTF(instruction.name);
        dataOutput.writeBoolean(instruction.isLocal);
        return null;
    }

    @Override
    public Void visitPopInstruction(PopInstruction instruction, DataOutput dataOutput) throws IOException {
        visitInstruction(instruction, dataOutput);
        return null;
    }

    @Override
    public Void visitGetFieldInstruction(GetFieldInstruction instruction, DataOutput dataOutput) throws IOException {
        visitInstruction(instruction, dataOutput);
        return null;
    }

    @Override
    public Void visitPutFieldInstruction(PutFieldInstruction instruction, DataOutput dataOutput) throws IOException {
        visitInstruction(instruction, dataOutput);
        return null;
    }

    @Override
    public Void visitOpenScopeInstruction(OpenScopeInstruction instruction, DataOutput dataOutput) throws IOException {
        visitInstruction(instruction, dataOutput);
        return null;
    }

    @Override
    public Void visitCloseScopeInstruction(CloseScopeInstruction instruction, DataOutput dataOutput) throws IOException {
        visitInstruction(instruction, dataOutput);
        return null;
    }

    @Override
    public Void visitAddInstruction(AddInstruction instruction, DataOutput dataOutput) throws IOException {
        visitInstruction(instruction, dataOutput);
        return null;
    }

    @Override
    public Void visitSubInstruction(SubInstruction instruction, DataOutput dataOutput) throws IOException {
        visitInstruction(instruction, dataOutput);
        return null;
    }

    @Override
    public Void visitMulInstruction(MulInstruction instruction, DataOutput dataOutput) throws IOException {
        visitInstruction(instruction, dataOutput);
        return null;
    }

    @Override
    public Void visitDivInstruction(DivInstruction instruction, DataOutput dataOutput) throws IOException {
        visitInstruction(instruction, dataOutput);
        return null;
    }

    @Override
    public Void visitModInstruction(ModInstruction instruction, DataOutput dataOutput) throws IOException {
        visitInstruction(instruction, dataOutput);
        return null;
    }

    @Override
    public Void visitLTInstruction(LTInstruction instruction, DataOutput dataOutput) throws IOException {
        visitInstruction(instruction, dataOutput);
        return null;
    }

    @Override
    public Void visitLTEInstruction(LTEInstruction instruction, DataOutput dataOutput) throws IOException {
        visitInstruction(instruction, dataOutput);
        return null;
    }

    @Override
    public Void visitGTInstruction(GTInstruction instruction, DataOutput dataOutput) throws IOException {
        visitInstruction(instruction, dataOutput);
        return null;
    }

    @Override
    public Void visitGTEInstruction(GTEInstruction instruction, DataOutput dataOutput) throws IOException {
        visitInstruction(instruction, dataOutput);
        return null;
    }

    @Override
    public Void visitEQInstruction(EQInstruction instruction, DataOutput dataOutput) throws IOException {
        visitInstruction(instruction, dataOutput);
        return null;
    }

    @Override
    public Void visitNEQInstruction(NEQInstruction instruction, DataOutput dataOutput) throws IOException {
        visitInstruction(instruction, dataOutput);
        return null;
    }

    @Override
    public Void visitAndInstruction(AndInstruction instruction, DataOutput dataOutput) throws IOException {
        visitInstruction(instruction, dataOutput);
        return null;
    }

    @Override
    public Void visitOrInstruction(OrInstruction instruction, DataOutput dataOutput) throws IOException {
        visitInstruction(instruction, dataOutput);
        return null;
    }
}
