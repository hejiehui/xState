package com.xrosstools.xstate;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class MapEvent<K, V> extends Event implements Map<K, V> {
    private Map<K, V> context = new LinkedHashMap<>();
    
    public MapEvent() {}
    
    public MapEvent(String id) {
        super(id);
    }
    
    @Override
    public Set<K> keySet() {
        return context.keySet();
    }
    
    @Override
    public V get(Object key) {
        return context.get(key);
    }
    
    @Override
    public V remove(Object key) {
        return context.remove(key);
    }
    
    @Override
    public void clear() {
        context.clear();
    }
    
    @Override
    public int size() {
        return context.size();
    }
    
    @Override
    public boolean isEmpty() {
        return context.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return context.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return context.containsValue(value);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        context.putAll(m);
    }

    @Override
    public Collection<V> values() {
        return context.values();
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return context.entrySet();
    }

    @Override
    public V put(K key, V value) {
        return context.put(key, value);
    }
}
