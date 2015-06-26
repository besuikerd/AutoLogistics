package com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.visitor;

import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.ListValue;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.ObjectValue;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.StackValue;

import java.util.Map;

public class CopyStackValueVisitor extends SafeBaseStackValueVisitor<Void, StackValue>{
    public static final CopyStackValueVisitor instance = new CopyStackValueVisitor();

    @Override
    public StackValue visitStackValue(StackValue value, Void aVoid) {
        return value;
    }

    @Override
    public StackValue visitListValue(ListValue value, Void aVoid) {
        ListValue copy = new ListValue();
        for(StackValue elem : value.value){
            copy.append(elem);
        }
        return copy;
    }

    @Override
    public StackValue visitObjectValue(ObjectValue value, Void aVoid) {
        ObjectValue copy = new ObjectValue();
        for(Map.Entry<String, StackValue> entry : value.mapping.entrySet()){
            copy.put(entry.getKey(), entry.getValue());
        }
        return copy;
    }
}
