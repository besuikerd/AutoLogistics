package com.besuikerd.autologistics.common.lib.dsl.vm.instruction;

import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.visitor.InstructionVisitor;
import com.besuikerd.autologistics.common.lib.dsl.vm.nativefunction.NativeFunction;
import com.besuikerd.autologistics.common.lib.dsl.vm.VirtualMachine;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.ClosureValue;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.NativeFunctionValue;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.StackValue;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.visitor.SafeBaseStackValueVisitor;
import scala.Tuple2;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CallInstruction implements Instruction{

    public final int argCount;

    private transient final CallInstructionVisitor visitor;

    public CallInstruction(int argCount) {
        this.argCount = argCount;
        visitor = new CallInstructionVisitor();
    }

    @Override
    public void execute(VirtualMachine machine) {
        List<StackValue> args = machine.pop(argCount);
        machine.pop().accept(visitor, Tuple2.apply(machine, args));
    }

    @Override
    public <ARG, RES, THROWS extends Throwable> RES accept(InstructionVisitor<ARG, RES, THROWS> visitor, ARG arg) throws THROWS {
        return visitor.visitCallInstruction(this, arg);
    }

    @Override
    public String toString() {
        return "CallInstruction(" + argCount + ")";
    }

    private class CallInstructionVisitor extends SafeBaseStackValueVisitor<Tuple2<VirtualMachine, List<StackValue>>, Void> {
        @Override
        public Void visitNativeFunctionValue(NativeFunctionValue value, Tuple2<VirtualMachine, List<StackValue>> arg) {
            VirtualMachine machine = arg._1();
            List<StackValue> args = arg._2();
            List<NativeFunction> natives = machine.getNatives(value.name);
            if(natives == null || natives.isEmpty()){
                machine.crash("Could not find " + value.stringRepresentation());
            } else{
                boolean found = false;
                for(int i = 0 ; i < natives.size() && !found ; i++){
                    NativeFunction f = natives.get(i);
                    if(f.matchesSignature(args)){
                        machine.push(f.call(machine, args));
                        found = true;
                    }
                }
                if(!found){
                    machine.crash("Could not find native function with matching signature for " + value.stringRepresentation());
                }
            }
            return null;
        }

        @Override
        public Void visitClosureValue(ClosureValue value, Tuple2<VirtualMachine, List<StackValue>> arg) {
            VirtualMachine machine = arg._1();
            machine.pushInstruction(CloseScopeInstruction.instance);
            machine.openScope();
            machine.pushInstructions(value.body);
            for(Map.Entry<String, StackValue> entry : value.free.entrySet()){
                machine.put(entry.getKey(), entry.getValue(), true);
            }
            Iterator<StackValue> argIt = arg._2().iterator();
            Iterator<String> bindIt = value.bindings.iterator();
            while(argIt.hasNext() && bindIt.hasNext()){
                machine.put(bindIt.next(), argIt.next(), true);
            }
            return null;
        }

        @Override
        public Void visitStackValue(StackValue value, Tuple2<VirtualMachine, List<StackValue>> arg) {
            arg._1().crash("Can not apply " + value.stringRepresentation());
            return null;
        }
    }
}