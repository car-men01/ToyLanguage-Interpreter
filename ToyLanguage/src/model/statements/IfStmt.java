package model.statements;
import model.adt.MyIDictionary;
import model.state.PrgState;
import exceptions.MyException;
import model.expressions.IExp;
import model.values.IValue;
import model.values.BoolValue;
import model.types.BoolType;
import exceptions.StatementException;


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
        return "(IF(" + exp.toString() + ") THEN (" + thenS.toString() + ")ELSE(" + elseS.toString() + "))";
    }

    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, IValue> symTable = state.getSymTable();
        IValue val = this.exp.eval(symTable);

        if(!val.getType().equals(new BoolType())){
            throw new StatementException("The condition is not a boolean");
        }
        if(((BoolValue)val).getVal())
        {
            state.getStack().push(thenS);
        }
        else
            state.getStack().push(elseS);

        return state;
    }

    public IStmt deepcopy() {
        return new IfStmt(exp.deepcopy(), thenS.deepcopy(), elseS.deepcopy());
    }

}
