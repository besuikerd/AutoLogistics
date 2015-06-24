package com.besuikerd.autologistics.common.lib.dsl.vm.instruction.visitor;
import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.*;
import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.logical.*;
import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.numeric.*;
import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.compare.*;
import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.Instruction;
import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.Instructions;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.visitor.DataInputStackValueParser;

import java.io.DataInput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataInputInstructionParser {
    public static Instruction parse(DataInput input) throws IOException{
        int ordinal = input.readByte();
        if(ordinal >= 0 && ordinal < Instructions.values().length) {
            int length;
            switch (Instructions.values()[ordinal]) {
                case BRANCH:
                    length = input.readInt();
                    List<Instruction> branchTrue = new ArrayList<Instruction>(length);
                    for (int i = 0; i < length; i++) {
                        branchTrue.add(parse(input));
                    }

                    length = input.readInt();
                    List<Instruction> branchFalse = new ArrayList<Instruction>(length);
                    for (int i = 0; i < length; i++) {
                        branchFalse.add(parse(input));
                    }
                    return new BranchInstruction(branchTrue, branchFalse);
                case REPEATED_BRANCH:
                    length = input.readInt();
                    List<Instruction> branchWhile = new ArrayList<Instruction>(length);
                    for (int i = 0; i < length; i++) {
                        branchWhile.add(parse(input));
                    }
                    return new RepeatedBranchInstruction(branchWhile);
                case CALL:
                    return new CallInstruction(input.readInt());
                case CRASH:
                    return new CrashInstruction(input.readUTF());
                case GET:
                    return new GetInstruction(input.readUTF());
                case LOAD:
                    length = input.readInt();
                    List<Instruction> loadInstructions = new ArrayList<Instruction>(length);
                    for (int i = 0; i < length; i++) {
                        loadInstructions.add(parse(input));
                    }
                    return new LoadInstruction(loadInstructions);
                case PUSH_CLOSURE:
                    String name = input.readUTF();

                    length = input.readInt();
                    List<String> bindings = new ArrayList<String>(length);
                    for (int i = 0; i < length; i++) {
                        bindings.add(input.readUTF());
                    }

                    length = input.readInt();
                    List<String> free = new ArrayList<String>(length);
                    for (int i = 0; i < length; i++) {
                        free.add(input.readUTF());
                    }

                    length = input.readInt();
                    List<Instruction> body = new ArrayList<Instruction>(length);
                    for (int i = 0; i < length; i++) {
                        body.add(parse(input));
                    }
                    return new PushClosureInstruction(name.equals("_") ? null : name, bindings, free, body);
                case PUSH:
                    return new PushInstruction(DataInputStackValueParser.parse(input));
                case PUSH_LIST:
                    return new PushListInstruction(input.readInt());
                case PUSH_OBJECT:
                    length = input.readInt();
                    List<String> keys = new ArrayList<String>(length);
                    for (int i = 0; i < length; i++) {
                        keys.add(input.readUTF());
                    }
                    return new PushObjectInstruction(keys);
                case PUT:
                    return new PutInstruction(input.readUTF(), input.readBoolean());
                case POP:
                    return PopInstruction.instance;
                case GET_FIELD:
                    return GetFieldInstruction.instance;
                case PUT_FIELD:
                    return PutFieldInstruction.instance;
                case OPEN_SCOPE:
                    return OpenScopeInstruction.instance;
                case CLOSE_SCOPE:
                    return CloseScopeInstruction.instance;
                case ADD:
                    return AddInstruction.instance;
                case SUB:
                    return SubInstruction.instance;
                case MUL:
                    return MulInstruction.instance;
                case DIV:
                    return DivInstruction.instance;
                case MOD:
                    return ModInstruction.instance;
                case LT:
                    return LTInstruction.instance;
                case LTE:
                    return LTEInstruction.instance;
                case GT:
                    return GTInstruction.instance;
                case GTE:
                    return GTEInstruction.instance;
                case EQ:
                    return EQInstruction.instance;
                case NEQ:
                    return NEQInstruction.instance;
                case AND:
                    return AndInstruction.instance;
                case OR:
                    return OrInstruction.instance;
            }
        }
        return null;
    }
}
