package com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue;

public enum StackValues {
    BOOLEAN("boolean", BooleanValue.class),
    CLOSURE("closure", ClosureValue.class),
    DOUBLE("double", DoubleValue.class),
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
}
