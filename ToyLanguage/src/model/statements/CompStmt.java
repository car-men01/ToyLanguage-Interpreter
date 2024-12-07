package model.statements;
import model.adt.MyIDictionary;
import model.state.PrgState;
import model.adt.MyIStack;
import exceptions.MyException;
import model.types.IType;

public class CompStmt implements IStmt {
    private IStmt first;
    private IStmt second;

    public CompStmt(IStmt first, IStmt second) {
        this.first = first;
        this.second = second;
    }
    @Override
    public String toString() {
        return first.toString() + "\n" + second.toString();
    }
    @Override
    public PrgState execute(PrgState state) {
        MyIStack<IStmt> stack = state.getStack();
        stack.push(second);
        stack.push(first);
        return null;
    }
    @Override
    public MyIDictionary<String, model.types.IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        return second.typecheck(first.typecheck(typeEnv));
    }
    @Override
    public IStmt deepcopy() {
        return new CompStmt(first.deepcopy(), second.deepcopy());
    }
}