package com.besuikerd.autologistics.common.lib.registry;

import java.util.*;

public class MapRegistry<K, V> implements IMapRegistry<K, V>{
    protected Map<K, V> registry;

    public final Map<K, V> readOnlyRegisty;

    protected MapRegistry(){
        this.registry = new HashMap<K, V>();
        this.readOnlyRegisty = Collections.unmodifiableMap(registry);
    }

    @Override
    public void register(K key, V value) {
        registry.put(key, value);
    }

    @Override
    public int size() {
        return registry.size();
    }

    @Override
    public V get(K key) {
        return registry.get(key);
    }

    @Override
    public Set<K> keySet() {
        return readOnlyRegisty.keySet();
    }

    @Override
    public Collection<V> values() {
        return readOnlyRegisty.values();
    }
}
