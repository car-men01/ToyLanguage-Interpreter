package model.adt;

import exceptions.KeyNotFoundException;

import java.util.HashMap;
import java.util.Map;

public class MyDictionary<K, V> implements MyIDictionary<K, V> {
    Map<K, V> dict;

    public MyDictionary() {
        dict = new HashMap<K, V>();
    }

    public V lookup(K key) throws KeyNotFoundException {
        if (!dict.containsKey(key)) {
            throw new KeyNotFoundException("Key not found in dictionary");
        }
        return dict.get(key);
    }

    public void insert(K key, V value) {
        dict.put(key, value);
    }

    public boolean contains(K key) {
        return dict.containsKey(key);
    }

    public void remove(K key) throws KeyNotFoundException {
        if (!dict.containsKey(key)) {
            throw new KeyNotFoundException("Key not found in dictionary");
        }
        dict.remove(key);
    }

    public void update(K key, V value) {
        dict.put(key, value);
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        for (K key : this.dict.keySet()) {
            s.append(key).append(" -> ").append(dict.get(key)).append("\n");
        }
        return s.toString();
    }
}
