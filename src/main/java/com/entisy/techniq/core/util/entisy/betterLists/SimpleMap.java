package com.entisy.techniq.core.util.entisy.betterLists;

import java.util.List;

public class SimpleMap<K, V> {
    SimpleList<K> keys = new SimpleList<>();
    SimpleList<V> values = new SimpleList<>();

    public K keyFromInt(int index) {
        return keys.get(index);
    }

    public V valueFromInt(int index) {
        return values.get(index);
    }

    public K getKey(V value) {
        for (int i = 0; i < values.size(); i++) {
            if (values.get(i) == value) {
                return keys.get(i);
            }
        }
        return null;
    }

    public V getValue(K key) {
        for (int i = 0; i < keys.size(); i++) {
            if (keys.get(i) == key) {
                return values.get(i);
            }
        }
        return null;
    }

    public SimpleList<K> getKeys() {
        return keys;
    }

    public K[] getKeyArray() {
        return keys.get();
    }

    public V[] getValueArray() {
        return values.get();
    }

    public SimpleList<V> getValues() {
        return values;
    }

    public void append(K key, V value) {
        keys.append(key);
        values.append(value);
    }

    public void remove (K key) {
        for (int i = 0; i < keys.size(); i++) {
            if (keys.get(i).equals(key)) {
                keys.remove(i);
                values.remove(i);
            }
        }
    }

    public int size() {
        return keys.size() == values.size() ? keys.size() : 0;
    }
}
