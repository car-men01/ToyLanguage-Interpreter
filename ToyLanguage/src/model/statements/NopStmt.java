package model.statements;

import model.state.PrgState;

public class NopStmt implements IStmt{
    public NopStmt() {};

    public PrgState execute(PrgState state) {
        return null;
    }

    public String toString() {
        return "nop";
    }

    public IStmt deepcopy() {
        return new NopStmt();
    }
}
