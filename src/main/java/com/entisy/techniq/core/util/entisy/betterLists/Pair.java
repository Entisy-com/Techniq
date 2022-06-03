package com.entisy.techniq.core.util.entisy.betterLists;

public class Pair<K, V> {

    private final K k;
    private final V v;

    public Pair(K key, V value) {
        this.k = key;
        this.v = value;
    }

    public K getKey() {
        return k;
    }

    public V getValue() {
        return v;
    }
}
