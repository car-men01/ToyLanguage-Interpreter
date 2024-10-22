package model.statements;
import model.state.PrgState;
import model.exceptions.MyException;
import model.expressions.IExp;

public class IfStmt implements IStmt{
    private IExp exp;
    private IStmt thenS;
    private IStmt elseS;

    public IfStmt(IExp exp, IStmt thenS, IStmt elseS) {
        this.exp=exp;
        this.thenS=thenS;
        this.elseS=elseS;
    }
    public String toString() {
        return "(IF("+ exp.toString()+") THEN(" +thenS.toString() +")ELSE("+elseS.toString()+"))";
    }
    public PrgState execute(PrgState state) throws MyException {
        /*
        IValue val = condition.evaluate(state.getSymTable());
        if(!val.getType().equals(new BoolType())){
            throw new StatementException("The condition is not a boolean");
        }
        if(((BoolValue)val).getValue())
        {
            state.getExecStack().push(thenStatement);
        }
        else
            state.getExecStack().push(elseStatement);
         */
        return state;
    }

}
