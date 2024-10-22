package model.statements;

import model.state.PrgState;
import model.exceptions.MyException;
import model.expressions.IExp;

public class PrintStmt implements IStmt{
    private IExp exp;

    public PrintStmt(IExp exp) {
        this.exp = exp;
    }

    public String toString() {
        return "print(" + exp.toString()+ ")";
    }

    public PrgState execute(PrgState state) throws MyException {
        //IValue val = expression.evaluate(state.getSymTable());
        //state.getOutputList().add(val.toString());

        return state;
    }
}
