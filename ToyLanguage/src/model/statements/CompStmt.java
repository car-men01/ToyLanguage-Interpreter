package model.statements;
import model.state.PrgState;
import model.adt.MyIStack;
import exceptions.MyException;

public class CompStmt implements IStmt {
    private IStmt first;
    private IStmt second;

    public CompStmt(IStmt first, IStmt second) {
        this.first = first;
        this.second = second;
    }
    @Override
    public String toString() {
        return "(" + first.toString() + ";" + second.toString() + ")";
    }
    @Override
    public PrgState execute(PrgState state) {
        MyIStack<IStmt> stack = state.getStack();
        stack.push(second);
        stack.push(first);
        return state;
    }
    @Override
    public IStmt deepcopy() {
        return new CompStmt(first.deepcopy(), second.deepcopy());
    }
}