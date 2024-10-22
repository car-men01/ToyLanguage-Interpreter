package model.adt;

import model.exceptions.KeyNotFoundException;

public interface MyIDictionary<K, V> {
    void insert(K key, V value);
    void remove(K key) throws KeyNotFoundException;
    public V getValue(K key) throws KeyNotFoundException;
}
