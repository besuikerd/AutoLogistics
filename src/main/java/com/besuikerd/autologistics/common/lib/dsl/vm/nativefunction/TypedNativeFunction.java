package com.besuikerd.autologistics.common.lib.dsl.vm.nativefunction;

import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.StackValue;

import java.util.List;

public abstract class TypedNativeFunction extends AbstractNativeFunction{
    protected Class<? extends StackValue>[] expectedTypes;

    public TypedNativeFunction(Class<? extends StackValue>... expectedTypes) {
        this.expectedTypes = expectedTypes;
    }

    @Override
    public boolean matchesSignature(List<StackValue> args) {
        if(args.size() != expectedTypes.length){
            return false;
        }
        for(int i = 0 ; i < args.size() ; i++){
            if(!expectedTypes[i].isInstance(args.get(i))){
                return false;
            }
        }
        return true;
    }
}