package model.statements;

import exceptions.MyException;
import model.adt.MyIDictionary;
import model.expressions.IExp;
import model.expressions.RelationalExp;
import model.state.PrgState;
import model.types.IType;

public class SwitchStmt implements IStmt {
    //switch(exp) (case exp1: stmt1) (case exp2: stmt2) (default: stmt3)

    private IStmt stmt1;
    private IStmt stmt2;
    private IStmt stmt3;
    private IExp exp;
    private IExp exp1;
    private IExp exp2;

    public SwitchStmt(IExp exp, IExp exp1, IStmt stmt1, IExp exp2, IStmt stmt2, IStmt stmt3) {
        this.exp = exp;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.stmt1 = stmt1;
        this.stmt2 = stmt2;
        this.stmt3 = stmt3;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        //if(exp==exp1) then stmt1 else (if (exp==exp2) then stmt2 else stmt3)

        IStmt newStmt = new IfStmt(new RelationalExp(exp, exp1, "=="), stmt1, new IfStmt(new RelationalExp(exp, exp2, "=="), stmt2, stmt3));

        state.getStack().push(newStmt);
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        //The typecheck method of switch statement verifies if exp, exp1 and exp2 have
        //the same type and also typecheck the statements stmt1, stmt2 and stmt3.

        IType typeexp = exp.typecheck(typeEnv);
        IType typeexp1 = exp1.typecheck(typeEnv);
        IType typeexp2 = exp2.typecheck(typeEnv);
        if(typeexp.equals(typeexp1) && typeexp.equals(typeexp2))
        {
            stmt1.typecheck(typeEnv);
            stmt2.typecheck(typeEnv);
            stmt3.typecheck(typeEnv);
            return typeEnv;
        }
        else
            throw new MyException("The expressions of the switch statement have different types");
    }

    @Override
    public IStmt deepcopy() {
        return new SwitchStmt(exp.deepcopy(), exp1.deepcopy(), stmt1.deepcopy(), exp2.deepcopy(), stmt2.deepcopy(), stmt3.deepcopy());
    }

    //switch(exp) (case exp1: stmt1) (case exp2: stmt2) (default: stmt3)
    @Override
    public String toString() {
        return "switch(" + exp.toString() + ")\n" + "(case " + exp1.toString() + ": " + stmt1.toString() + ")\n(case " + exp2.toString() + ": " + stmt2.toString() + ")\n(default: " + stmt3.toString() + ")";
    }
}
