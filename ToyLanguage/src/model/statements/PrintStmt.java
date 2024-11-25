package model.statements;

import model.state.PrgState;
import exceptions.MyException;
import model.expressions.IExp;
import model.values.IValue;

public class PrintStmt implements IStmt{
    private IExp exp;

    public PrintStmt(IExp exp) {
        this.exp = exp;
    }
    @Override
    public String toString() {
        return "print(" + exp.toString()+ ")";
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        IValue val = exp.eval(state.getSymTable(), state.getHeap());
        state.getOut().add(val);

        return state;
    }
    @Override
    public IStmt deepcopy() {
        return new PrintStmt(exp.deepcopy());
    }
}
