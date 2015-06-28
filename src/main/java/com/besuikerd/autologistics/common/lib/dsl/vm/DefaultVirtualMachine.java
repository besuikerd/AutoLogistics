package com.besuikerd.autologistics.common.lib.dsl.vm;

import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.CrashInstruction;
import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.Instruction;
import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.visitor.DataInputInstructionParser;
import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.visitor.DataOutputInstructionVisitor;
import com.besuikerd.autologistics.common.lib.dsl.vm.nativefunction.NativeFunction;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.ClosureValue;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.NativeFunctionValue;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.Recurse;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.StackValue;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.visitor.DataInputStackValueParser;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.visitor.DataOutputStackValueVisitor;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.*;

public class DefaultVirtualMachine implements VirtualMachine{
    protected Stack<StackValue> stack;
    protected Stack<Instruction> instructions;
    protected Stack<ClosureValue> scopes;

    protected Map<String, StackValue> globals;
    protected Map<String, NativeFunction> natives;

    public DefaultVirtualMachine(){
        this.stack = new Stack<StackValue>();
        this.instructions = new Stack<Instruction>();
        this.scopes = new Stack<ClosureValue>();
        this.globals = new HashMap<String, StackValue>();
        this.natives = new HashMap<String, NativeFunction>();
    }

    @Override
    public void push(StackValue value) {
        stack.push(value);
    }

    @Override
    public StackValue pop() {
        return stack.pop();
    }

    @Override
    public List<StackValue> pop(int n) {
        List<StackValue> list = new ArrayList<StackValue>(n);

        for(int i = 0 ; i < n ; i++){
            list.add(0, stack.pop());
        }
        return list;
    }

    @Override
    public void reset() {
        stack.clear();
        instructions.clear();
        scopes.clear();

        ClosureValue globalScope = openScope();
        for(Map.Entry<String, StackValue> entry: globals.entrySet()){
            globalScope.free.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void load(Collection<Instruction> instructions) {
        reset();
        pushInstructions(instructions);
    }

    @Override
    public void run(int n) {
        while(!isTerminated() && n > 0){
            cycle();
            n--;
        }
    }

    @Override
    public void cycle() {
        popInstruction().execute(this);
    }

    @Override
    public void pushInstruction(Instruction instruction) {
        instructions.push(instruction);
    }

    @Override
    public void pushInstructions(Collection<Instruction> instructions) {
        int insertPos = this.instructions.size();
        for(Instruction instruction: instructions){
            this.instructions.add(insertPos, instruction);
        }
    }

    @Override
    public Instruction popInstruction() {
        return instructions.pop();
    }

    @Override
    public ClosureValue openScope() {
        List<String> bindings = Collections.emptyList();
        List<Instruction> body = Collections.emptyList();
        ClosureValue scope = new ClosureValue(bindings, new HashMap<String, StackValue>(), body);
        scopes.push(scope);
        return scope;
    }

    @Override
    public ClosureValue closeScope() {
        return scopes.pop();
    }

    @Override
    public void crash(String message) {
        instructions.push(new CrashInstruction(message));
    }

    @Override
    public StackValue get(String name) {
        StackValue found = null;
        Iterator<ClosureValue> scopeIt = this.scopes.iterator();
        for(int i = scopes.size() - 1 ; i >= 0 && found == null ; i--){
            ClosureValue scope = scopes.get(i);
            found = scope.free.get(name);
            if(found != null && found instanceof Recurse) {
                found = scope;
            }
        }
        return found;
    }

    @Override
    public void put(String name, StackValue value, boolean isLocal) {
        if(isLocal){
            scopes.peek().free.put(name, value);
        } else{
            ClosureValue validScope = null;
            for(int i = scopes.size() - 1; i >= 0 && validScope == null; i--){
                ClosureValue currentScope = scopes.get(i);
                if(currentScope.free.containsKey(name)){
                    validScope = currentScope;
                }
            }
            if(validScope == null){
                validScope = scopes.peek();
            }
            validScope.free.put(name, value);
        }
    }

    @Override
    public void addNative(String name, NativeFunction f) {
        natives.put(name, f);
        addGlobal(name, new NativeFunctionValue(name));
    }

    @Override
    public NativeFunction getNative(String name) {
        return natives.get(name);
    }

    @Override
    public void addGlobal(String name, StackValue value) {
        globals.put(name, value);
    }

    @Override
    public boolean isErrorState() {
        return !instructions.isEmpty() && instructions.peek() instanceof CrashInstruction;
    }

    @Override
    public boolean isTerminated() {
        return instructions.isEmpty() || isErrorState();
    }

    @Override
    public String getErrorMessage() {
        if(isErrorState()){
            Instruction top = instructions.peek();
            if(top instanceof CrashInstruction){
                return ((CrashInstruction) top).message;
            }
        }
        return null;
    }

    @Override
    public void serialize(DataOutput output) throws IOException {
        serializeStack(output);
        serializeInstructions(output);
        serializeScopes(output);
    }

    @Override
    public void deserialize(DataInput input) throws IOException{
        reset();
        deserializeStack(input);
        deserializeInstructions(input);
        deserializeScopes(input);
    }

    protected void serializeStack(DataOutput output) throws IOException{
        output.writeInt(stack.size());
        for(StackValue value : stack){
            value.accept(DataOutputStackValueVisitor.instance, output);
        }
    }

    protected void serializeInstructions(DataOutput output) throws IOException{
        output.writeInt(instructions.size());
        for(Instruction instruction : instructions){
            instruction.accept(DataOutputInstructionVisitor.instance, output);
        }
    }


    protected void serializeScopes(DataOutput output) throws IOException{
        int size = scopes.size() - 1; //we do not need to serialize the global scope
        output.writeInt(size);
        for(int i = 1 ; i < size ; i++){
            scopes.get(i).accept(DataOutputStackValueVisitor.instance, output);
        }
    }

    protected void deserializeStack(DataInput input) throws IOException{
        int length = input.readInt();
        for(int i = 0 ; i < length ; i++){
            stack.add(DataInputStackValueParser.parse(input));
        }
    }

    protected void deserializeInstructions(DataInput input) throws IOException{
        int length = input.readInt();
        for(int i = 0 ; i < length ; i++){
            instructions.add(DataInputInstructionParser.parse(input));
        }
    }

    protected void deserializeScopes(DataInput input) throws IOException{
        int length = input.readInt();
        for(int i = 0 ; i < length ; i++){
            scopes.add((ClosureValue) DataInputStackValueParser.parse(input));
        }
    }

    public Stack<StackValue> getStack() {
        return stack;
    }

    public Stack<Instruction> getInstructions() {
        return instructions;
    }

    public Stack<ClosureValue> getScopes() {
        return scopes;
    }
}
