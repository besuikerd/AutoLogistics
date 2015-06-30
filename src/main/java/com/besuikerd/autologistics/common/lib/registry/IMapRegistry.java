package com.besuikerd.autologistics.common.lib.registry;

import java.util.Collection;
import java.util.Set;

public interface IMapRegistry<K, V> {
    void register(K key, V value);
    int size();
    V get(K key);
    Set<K> keySet();
    Collection<V> values();
}
