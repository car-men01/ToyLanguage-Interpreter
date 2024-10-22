package model.adt;

import model.exceptions.EmptyStackException;

public interface MyIStack<T> {
    T pop() throws EmptyStackException;
    void push(T t);
    boolean isEmpty();
    int size();
    //MyIStack<T> getStack();
}
