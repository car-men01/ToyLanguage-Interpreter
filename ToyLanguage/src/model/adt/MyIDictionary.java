package model.adt;

import exceptions.KeyNotFoundException;

public interface MyIDictionary<K, V> {
    void insert(K key, V value);
    void remove(K key) throws KeyNotFoundException;
    public V lookup(K key) throws KeyNotFoundException;
    public boolean contains(K key);
    public void update(K key, V value);
}
