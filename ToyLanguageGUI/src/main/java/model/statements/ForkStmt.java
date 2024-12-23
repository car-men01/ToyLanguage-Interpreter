package model.statements;

import exceptions.MyException;
import model.adt.MyIDictionary;
import model.adt.MyIStack;
import model.adt.MyStack;
import model.state.PrgState;
import model.types.IType;
import model.values.IValue;

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
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        return stmt.typecheck(typeEnv);
    }

    @Override
    public IStmt deepcopy() {
        return new ForkStmt(stmt.deepcopy());
    }
}
