package com.besuikerd.autologistics.common.lib.registry;

public interface IListRegistry<A> extends Iterable<A>{
    void register(A a);
    int size();
    A get(int index);
}