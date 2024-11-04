package model.state;

import exceptions.EmptyStackException;
import model.statements.IStmt;
import model.adt.MyIStack;


public interface IExecState {
    public void push(IStmt stmt);
    public IStmt pop() throws EmptyStackException;
    int size();
    boolean isEmpty();
    MyIStack<IStmt> getExecStack();
}
