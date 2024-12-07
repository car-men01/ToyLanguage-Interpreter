package model.statements;

import exceptions.MyException;
import model.adt.MyIDictionary;
import model.state.PrgState;
import model.types.IType;

public class NopStmt implements IStmt{
    public NopStmt() {};
    @Override
    public PrgState execute(PrgState state) {
        return null;
    }
    @Override
    public String toString() {
        return "Nop";
    }
    @Override
    public MyIDictionary<String, model.types.IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        return typeEnv;
    }
    @Override
    public IStmt deepcopy() {
        return new NopStmt();
    }
}
