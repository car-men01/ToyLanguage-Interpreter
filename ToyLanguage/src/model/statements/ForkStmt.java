package model.statements;

import model.adt.*;
import model.state.PrgState;
import model.values.IValue;
import model.values.StringValue;

import java.io.BufferedReader;

public class ForkStmt implements IStmt{
    IStmt stmt;

    public ForkStmt(IStmt stmt) {
        this.stmt = stmt;
    }

    @Override
    public String toString() {
        return "fork {\n" + stmt.toString() + "\n}";
    }

    @Override
    public PrgState execute(PrgState state) {
        MyIStack<IStmt> newStack = new MyStack<IStmt>();
        //newStack.push(stmt);
        MyIDictionary<String, IValue> newSymTable = state.getSymTable().clone();

        return new PrgState(newStack, newSymTable, state.getOut(), stmt, state.getFileTable(), state.getHeap());
    }

    @Override
    public IStmt deepcopy() {
        return new ForkStmt(stmt.deepcopy());
    }
}
