package com.besuikerd.autologistics.common.lib.dsl.vm.instruction;

public enum Instructions {
    EQ("eq"),
    GTE("gte"),
    GT("gt"),
    LTE("lte"),
    LT("lt"),
    NEQ("neq"),
    AND("and"),
    OR("or"),
    ADD("add"),
    SUB("sub"),
    DIV("div"),
    MUL("mul"),
    MOD("mod"),
    BRANCH("branch"),
    REPEATED_BRANCH("repeated_branch"),
    CALL("call"),
    CLOSE_SCOPE("close_scope"),
    CRASH("crash"),
    GET_FIELD("get_field"),
    GET("get"),
    LOAD("load"),
    OPEN_SCOPE("open_scope"),
    POP("pop"),
    PUSH_CLOSURE("push_closure"),
    PUSH("push"),
    PUSH_LIST("push_list"),
    PUSH_OBJECT("push_object"),
    PUT_FIELD("put_field"),
    PUT("put")
    ;

    public final String type;
    Instructions(String type){
        this.type = type;
    }
}
