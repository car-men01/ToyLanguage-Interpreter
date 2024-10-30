package model.state;

import exceptions.EmptyStackException;
import model.statements.IStmt;
import model.adt.MyIStack;
import model.adt.MyStack;

public class ExecState implements IExecState{
    private MyIStack<IStmt> exeStack;

    public ExecState() {
        this.exeStack = new MyStack<IStmt>();
    }

    public void push(IStmt stmt) {
        this.exeStack.push(stmt);
    }

    public IStmt pop() throws EmptyStackException {
        return this.exeStack.pop();
    }

    public int size() {
        return this.exeStack.size();
    }

    public boolean isEmpty() {
        return this.exeStack.isEmpty();
    }

    public MyIStack<IStmt> getExecStack() {
        return this.exeStack;
    }

    public String toString() {
        return this.exeStack.toString();
    }
}
