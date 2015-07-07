package com.besuikerd.autologistics.common.tile.logistic;

import com.besuikerd.autologistics.common.lib.dsl.vm.nativefunction.AbstractNativeFunction;
import com.besuikerd.autologistics.common.lib.dsl.vm.nativefunction.SingleArgTypedNativeFunction;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.BooleanValue;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.ObjectValue;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.StackValue;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.StackValues;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.visitor.CopyStackValueVisitor;

import java.util.List;

public class NativeFunctionInvertFilter extends SingleArgTypedNativeFunction<ObjectValue>{
    public static final NativeFunctionInvertFilter instance = new NativeFunctionInvertFilter();

    public NativeFunctionInvertFilter() {
        super(ObjectValue.class);
    }

    @Override
    public StackValue call(ObjectValue arg) {
        ObjectValue result = arg;
        BooleanValue inverted;
        if((inverted = StackValues.tryExtractField(BooleanValue.class, "inverted", arg)) != null){
            result = (ObjectValue) result.accept(CopyStackValueVisitor.instance, null);
            result.put("inverted", inverted.value ? BooleanValue.falseValue : BooleanValue.trueValue);
        } else{
            result.put("inverted", BooleanValue.trueValue);
        }
        return result;
    }

    @Override
    public boolean matchesSignature(List<StackValue> args) {
        return super.matchesSignature(args) && ((ObjectValue) args.get(0)).mapping.containsKey("filter");
    }
}
