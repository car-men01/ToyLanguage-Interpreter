package model.statements;

import model.state.PrgState;

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
    public IStmt deepcopy() {
        return new NopStmt();
    }
}
