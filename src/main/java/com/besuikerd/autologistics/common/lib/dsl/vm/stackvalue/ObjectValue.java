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
        StringBuilder builder = new StringBuilder("{").append(first.getKey() + " = " + first.getValue());
        while(iterator.hasNext()){
            Map.Entry<String, StackValue> next = iterator.next();
            builder.append(", " + next.getKey() + " = " + next.getValue().stringRepresentation());
        }
        builder.append('}');
        return builder.toString();
    }

    public void put(String key, StackValue value){
        mapping.put(key, value);
    }

    @Override
    public <A, B, T extends Throwable> B accept(StackValueVisitor<A, B, T> visitor, A arg) throws T {
        return visitor.visitObjectValue(this, arg);
    }
}