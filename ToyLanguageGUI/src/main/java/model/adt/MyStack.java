package model.adt;

import exceptions.EmptyStackException;

import java.util.Stack;

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

    public String toString() {
        StringBuilder s = new StringBuilder();
        for (T elem : this.stack) {
            s.append(elem).append(" ; ");
        }
        return s.toString();
    }

    public Stack<T> getStack() {
        return this.stack;
    }

    @Override
    public T peek() {
        return this.stack.peek();
    }

    @Override
    public MyStack<T> deepCopy() {
        MyStack<T> newStack = new MyStack<>();
        Stack<T> tempStack = new Stack<>();

        while (!this.stack.empty())
            tempStack.push(this.stack.pop());

        while (!tempStack.isEmpty()) {
            this.stack.push(tempStack.peek());
            newStack.push(tempStack.pop());
        }

        return newStack;
    }

}
