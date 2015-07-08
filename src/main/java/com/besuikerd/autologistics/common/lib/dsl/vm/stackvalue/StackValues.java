package com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue;

import com.besuikerd.autologistics.common.lib.dsl.vm.VirtualMachine;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.visitor.TypeStackValueVisitor;

public enum StackValues {
    BOOLEAN("boolean", BooleanValue.class),
    CLOSURE("closure", ClosureValue.class),
    DECIMAL("decimal", DecimalValue.class),
    INT("int", IntegerValue.class),
    LIST("list", ListValue.class),
    NATIVE("native", NativeFunctionValue.class),
    NIL("null", NilValue.class),
    OBJECT("object", ObjectValue.class),
    STRING("string", StringValue.class),
    RECURSE("recurse", Recurse.class)
    ;

    public final String type;
    public final Class<? extends StackValue> cls;
    StackValues(String type, Class<? extends StackValue> cls){
        this.type = type;
        this.cls = cls;
    }

    public static StackValues findOfClass(Class<? extends StackValue> cls){
        for(StackValues value : values()){
            if(value.cls.isAssignableFrom(cls)){
                return value;
            }
        }
        return null;
    }

    public static String findOfClassString(Class<? extends StackValue> cls){
        StackValues value = findOfClass(cls);
        return value == null ? "null" : value.type;
    }

    public static <A extends StackValue> A expectType(VirtualMachine vm, Class<A> cls, StackValue value) throws IllegalArgumentException{
        if(cls.isInstance(value)){
            return cls.cast(value);
        } else{
            vm.crash("expected type: " + StackValues.findOfClassString(cls) + ", got: " + value.accept(TypeStackValueVisitor.instance, null));
            return null;
        }
    }

    public static  <A extends StackValue> A extractField(VirtualMachine vm, Class<A> cls, String field, ObjectValue obj) throws IllegalArgumentException{
        StackValue value = obj.mapping.get(field);
        if(value == null){
            vm.crash("field not found: " + field);
            return null;
        } else {
            return expectType(vm, cls, value);
        }
    }

    public static <A extends StackValue> A tryExpectType(Class<A> cls, StackValue value) throws IllegalArgumentException{
        return cls.isInstance(value) ? cls.cast(value) : null;
    }

    public static  <A extends StackValue> A tryExtractField(Class<A> cls, String field, ObjectValue obj) throws IllegalArgumentException{
        StackValue value = obj.mapping.get(field);
        return value != null ? tryExpectType(cls, value) : null;
    }

    public static ObjectValue tryGetObjectOfType(String type, StackValue value){
        ObjectValue obj;
        StringValue typeString;
        return
               (obj = tryExpectType(ObjectValue.class, value)) != null
            && (typeString = tryExtractField(StringValue.class, "type", obj)) != null
            && typeString.value.equals(type)
        ? obj : null;
    }

    public static boolean isOfType(String type, StackValue value){
        return tryGetObjectOfType(type, value) != null;
    }
}
