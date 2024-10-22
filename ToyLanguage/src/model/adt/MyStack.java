package model.adt;
import java.util.Stack;

import model.exceptions.EmptyStackException;

public class MyStack<T> implements MyIStack<T> {
    Stack<T> stack;

    public MyStack() {
        stack = new Stack<>();
    }

    public T pop() throws EmptyStackException {
        if (this.stack.isEmpty()) {
            throw new EmptyStackException("The stack is empty!");
        }
        return this.stack.pop();
    }

    public void push(T t) {
        this.stack.push(t);
    }

    public boolean isEmpty() {
        return this.stack.isEmpty();
    }

    public int size() {
        return this.stack.size();
    }

    /*public MyIStack<T> getStack() {
        MyStack<T> copy = new MyStack<>();
        copy.stack = (Stack<T>) this.stack.clone();
        return copy;
    }*/

}
