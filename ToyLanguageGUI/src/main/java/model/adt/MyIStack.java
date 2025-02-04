package model.adt;

import exceptions.EmptyStackException;

import java.util.Stack;

public interface MyIStack<T> {
    T pop() throws EmptyStackException;
    void push(T t);
    boolean isEmpty();
    int size();
    Stack<T> getStack();
    T peek();
    MyStack<T> deepCopy();
}
