package model.adt;

import model.exceptions.KeyNotFoundException;

public class MyDictionary<K, V> implements MyIDictionary<K, V> {


    public MyDictionary() {

    }

    public void insert(K key, V value) {

    }

    public void remove(K key) throws KeyNotFoundException {

    }

    public V getValue(K key) throws KeyNotFoundException {
        return null;
    }
}
