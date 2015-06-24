package com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue;

public enum StackValues {
    BOOLEAN("boolean"),
    CLOSURE("closure"),
    DOUBLE("double"),
    INT("int"),
    LIST("list"),
    NATIVE("native"),
    NIL("null"),
    OBJECT("object"),
    STRING("string"),
    RECURSE("recurse")
    ;

    public final String type;
    StackValues(String type){
        this.type = type;
    }
}
