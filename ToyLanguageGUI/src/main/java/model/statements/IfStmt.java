package model.statements;

import exceptions.MyException;
import exceptions.StatementException;
import model.adt.MyIDictionary;
import model.expressions.IExp;
import model.state.PrgState;
import model.types.BoolType;
import model.types.IType;
import model.values.BoolValue;
import model.values.IValue;


public class IfStmt implements IStmt{
    private IExp exp;
    private IStmt thenS;
    private IStmt elseS;

    public IfStmt(IExp exp, IStmt thenS, IStmt elseS) {
        this.exp=exp;
        this.thenS=thenS;
        this.elseS=elseS;
    }
    @Override
    public String toString() {
        return "if (" + exp.toString() + ") then (" + thenS.toString() + ") else (" + elseS.toString() + ")";
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, IValue> symTable = state.getSymTable();
        IValue val = this.exp.eval(symTable, state.getHeap());

        if(!val.getType().equals(new BoolType())){
            throw new StatementException("The condition is not a boolean");
        }
        if(((BoolValue)val).getVal())
        {
            state.getStack().push(thenS);
        }
        else
            state.getStack().push(elseS);

        return null;
    }
    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType typeexp = exp.typecheck(typeEnv);
        if(typeexp.equals(new BoolType()))
        {
            thenS.typecheck(typeEnv);
            elseS.typecheck(typeEnv);
            return typeEnv;
        }
        else
            throw new MyException("The condition of IF has not the type bool");
    }
    @Override
    public IStmt deepcopy() {
        return new IfStmt(exp.deepcopy(), thenS.deepcopy(), elseS.deepcopy());
    }

}
