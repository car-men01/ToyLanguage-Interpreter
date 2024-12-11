package model.adt;

import exceptions.KeyNotFoundException;

import java.util.Map;
import java.util.Set;

public interface MyIDictionary<K, V> {
    void insert(K key, V value);
    void remove(K key) throws KeyNotFoundException;
    V lookup(K key) throws KeyNotFoundException;
    boolean contains(K key);
    void update(K key, V value);
    Set<K> getKeys();
    Map<K, V> getContent();
    MyIDictionary<K, V> clone();
}
