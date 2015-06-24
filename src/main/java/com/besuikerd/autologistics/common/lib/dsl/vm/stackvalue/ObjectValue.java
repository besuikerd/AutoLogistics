package com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue;

import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.visitor.StackValueVisitor;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ObjectValue implements StackValue{
    public final Map<String, StackValue> mapping;

    public ObjectValue(Map<String, StackValue> mapping) {
        this.mapping = mapping;
    }

    public ObjectValue(){
        this(new HashMap<String, StackValue>());
    }

    @Override
    public String stringRepresentation() {
        int size = mapping.size();
        if(size == 0){
            return "{}";
        }
        Iterator<Map.Entry<String, StackValue>> iterator = mapping.entrySet().iterator();
        Map.Entry<String, StackValue> first = iterator.next();
        StringBuilder builder = new StringBuilder("[").append(first.getKey() + " = " + first.getValue());
        while(iterator.hasNext()){
            Map.Entry<String, StackValue> next = iterator.next();
            builder.append(", " + next.getKey() + " = " + next.getValue());
        }
        return builder.toString();
    }

    @Override
    public <A, B> B accept(StackValueVisitor<A, B> visitor, A arg) {
        return visitor.visitObjectValue(this, arg);
    }
}