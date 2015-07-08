package com.besuikerd.autologistics.common.lib.dsl.vm.codegen;

import cofh.lib.util.ArrayHashList;
import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.GetInstruction;
import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.Instruction;
import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.PutInstruction;
import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.visitor.SafeBaseInstructionVisitor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FreeVariableInstructionVisitor extends SafeBaseInstructionVisitor<List<String>, List<String>>{
    private Set<String> puts;

    private FreeVariableInstructionVisitor() {
        this.puts = new HashSet<String>();
    }

    public static List<String> visit(List<Instruction> instructions){
        List<String> frees = new ArrayHashList<String>();
        FreeVariableInstructionVisitor visitor = new FreeVariableInstructionVisitor();
        for(Instruction instruction : instructions){
            instruction.accept(visitor, frees);
        }
        return frees;
    }

    @Override
    public List<String> visitGetInstruction(GetInstruction instruction, List<String> frees) {
        if(!puts.contains(instruction.name) && !frees.contains(instruction.name)){
            frees.add(instruction.name);
        }
        return frees;
    }

    @Override
    public List<String> visitPutInstruction(PutInstruction instruction, List<String> frees) {
        puts.add(instruction.name);
        return frees;
    }
}
