package model.adt;

import java.util.List;

public interface MyIList<T> {
    void add(T e) ;
    List<T> getAll();
    int size();
    T get(int index);
}
