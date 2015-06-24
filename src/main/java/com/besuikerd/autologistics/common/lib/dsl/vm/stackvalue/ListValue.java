package com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue;

import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.visitor.StackValueVisitor;

import java.util.ArrayList;
import java.util.List;

public class ListValue implements StackValue{
    public final List<StackValue> value;

    public ListValue(List<StackValue> value) {
        this.value = value;
    }

    public ListValue(){
        this(new ArrayList<StackValue>());
    }

    @Override
    public String stringRepresentation() {
        int size = value.size();
        if(size == 0){
            return "[]";
        }
        StringBuilder builder = new StringBuilder("[").append(value.get(0).stringRepresentation());
        for(int i = 1 ; i < size ; i++){
            builder.append(", ").append(value.get(i).stringRepresentation());
        }
        builder.append("]");
        return builder.toString();
    }

    @Override
    public <A, B> B accept(StackValueVisitor<A, B> visitor, A arg) {
        return visitor.visitListValue(this, arg);
    }
}
