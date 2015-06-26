package com.besuikerd.autologistics.common.tile.logistic;

import com.besuikerd.autologistics.common.lib.dsl.vm.VirtualMachine;
import com.besuikerd.autologistics.common.lib.dsl.vm.nativefunction.AbstractNativeFunction;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.*;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.visitor.CopyStackValueVisitor;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.visitor.SafeBaseStackValueVisitor;

import java.util.List;

public class NativeFunctionItemFilter extends AbstractNativeFunction{
    public static final NativeFunctionItemFilter instance = new NativeFunctionItemFilter();

    @Override
    public StackValue call(VirtualMachine vm, List<StackValue> args) {
        if(ensureLength(vm, args, 2)){
            ObjectValue item;
            ListValue filters;
            if((item = expectType(vm, ObjectValue.class, args.get(0))) != null && (filters = expectType(vm, ListValue.class, args.get(1))) != null) {
                item = (ObjectValue) item.accept(CopyStackValueVisitor.instance, null);
                if(!item.mapping.containsKey("filter")){
                    item.mapping.put("filter", createEmptyFilter());
                }

                ObjectValue filter;
                ListValue itemFilter;
                ListValue sideFilter;
                if((filter = extractField(vm, ObjectValue.class, "filter", item)) != null &&(itemFilter = expectType(vm, ListValue.class, filter.mapping.get("items"))) != null && (sideFilter = expectType(vm, ListValue.class, filter.mapping.get("sides"))) != null){

                    AddItemFilterStackValueVisitor visitor = new AddItemFilterStackValueVisitor(vm, filter, itemFilter, sideFilter);
                    for(StackValue filterElement : filters.value){
                        filterElement.accept(visitor, null);
                    }
                }
                return item;
            }
        }
        return NilValue.instance;
    }

    private ObjectValue createEmptyFilter(){
        ObjectValue filter = new ObjectValue();
        filter.mapping.put("sides", new ListValue());
        filter.mapping.put("items", new ListValue());
        return filter;
    }

    public class AddItemFilterStackValueVisitor extends SafeBaseStackValueVisitor<Void, Void> {
        private VirtualMachine vm;
        private ObjectValue filter;
        private ListValue itemFilter;
        private ListValue sideFilter;

        public AddItemFilterStackValueVisitor(VirtualMachine vm, ObjectValue filter, ListValue itemFilter, ListValue sideFilter) {
            this.vm = vm;
            this.filter = filter;
            this.itemFilter = itemFilter;
            this.sideFilter = sideFilter;
        }

        @Override
        public Void visitIntegerValue(IntegerValue value, Void aVoid) {
            filter.mapping.put("amount", value);
            return null;
        }

        @Override
        public Void visitStringValue(StringValue value, Void aVoid) {
            StringFacing facing;
            if((facing = StringFacing.fromString(value.value)) != null){
                sideFilter.append(new StringValue(facing.name));
            }
            return null;
        }

        @Override
        public Void visitObjectValue(ObjectValue value, Void aVoid) {
            StringValue type;
            if((type = extractField(vm, StringValue.class, "type", value)) != null && type.value.equals("item")){
                itemFilter.append(value);
            }
            return null;
        }
    }
}
