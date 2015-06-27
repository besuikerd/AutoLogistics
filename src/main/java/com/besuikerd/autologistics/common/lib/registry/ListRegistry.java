package com.besuikerd.autologistics.common.lib.registry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ListRegistry<A> implements IListRegistry<A> {

    protected List<A> registry;
    public final List<A> readOnlyRegistry;

    protected ListRegistry(){
        registry = new ArrayList<A>();
        readOnlyRegistry = Collections.unmodifiableList(registry);
    }

    @Override
    public void register(A a) {
        registry.add(a);
    }

    @Override
    public int size() {
        return registry.size();
    }

    @Override
    public A get(int index) {
        return registry.get(index);
    }

    @Override
    public Iterator<A> iterator() {
        return readOnlyRegistry.iterator();
    }
}
