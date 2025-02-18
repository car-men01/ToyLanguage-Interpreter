package model.statements;

import exceptions.MyException;
import exceptions.StatementException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.expressions.IExp;
import model.state.PrgState;
import model.types.BoolType;
import model.types.IType;
import model.values.BoolValue;
import model.values.IValue;

public class CondAssignStmt implements IStmt{
    private String var;
    private IExp exp1;
    private IExp exp2;
    private IExp exp3;

    public CondAssignStmt(String var, IExp exp1, IExp exp2, IExp exp3) {
        this.var = var;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.exp3 = exp3;
    }

    //v=exp1?exp2:exp3
    @Override
    public String toString() {
        return var + " = " + exp1.toString() + " ? " + exp2.toString() + " : " + exp3.toString();
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        //- create the following statement:
        //if (exp1) then v=exp2 else v=exp3
        //- push the new statement on the stack
        //The typecheck method of conditional assignment statement verifies if exp1 has
        //the type bool, and also the fact that v, exp2, and exp3 have the same type

        IStmt newStmt = new IfStmt(exp1, new AssignStmt(var, exp2), new AssignStmt(var, exp3));
        state.getStack().push(newStmt);
        return null;

    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType val1 = exp1.typecheck(typeEnv);
        if(!val1.equals(new BoolType())) {
            throw new StatementException("The condition exp1 is not a boolean");
        }
        IType val2 = exp2.typecheck(typeEnv);
        IType val3 = exp3.typecheck(typeEnv);
        if(!val2.equals(val3)) {
            throw new StatementException("The types of the expressions do not match");
        }
        return typeEnv;
    }

    @Override
    public IStmt deepcopy() {
        return new CondAssignStmt(new String(this.var), this.exp1.deepcopy(), this.exp2.deepcopy(), this.exp3.deepcopy());
    }
}
