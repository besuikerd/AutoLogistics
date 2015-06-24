package com.besuikerd.autologistics.common.lib.collection;

import java.util.Collection;
import java.util.List;

public interface Stack<A> extends List<A>{
    A pop();
    Stack<A> pop(int n);

    A peek();
    Stack<A> peek(int n);

    void push(A value);
    void pushAll(Collection<A> values);
}
