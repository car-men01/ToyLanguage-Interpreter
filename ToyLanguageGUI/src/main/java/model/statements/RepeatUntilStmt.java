package model.statements;

import exceptions.MyException;
import model.adt.MyIDictionary;
import model.expressions.IExp;
import model.expressions.NotExp;
import model.state.PrgState;
import model.types.BoolType;
import model.types.IType;

public class RepeatUntilStmt implements IStmt{
    //repeat stmt1 until exp2
    //- create the following statement: stmt1;(while(!exp2) stmt1)
    //- push the new statement on the stack

    private IStmt stmt1;
    private IExp exp2;

    public RepeatUntilStmt(IStmt stmt1, IExp exp2) {
        this.stmt1 = stmt1;
        this.exp2 = exp2;
    }

    @Override
    public String toString() {
        return "repeat " + stmt1.toString() + " until " + exp2.toString();
    }

    @Override
    public PrgState execute(PrgState state) {
        //stmt1;(while(!exp2) stmt1)

        state.getStack().push(new CompStmt(stmt1, new WhileStmt(new NotExp(exp2), stmt1)));
        return null;
    }

    @Override
    public IStmt deepcopy() {
        return new RepeatUntilStmt(stmt1.deepcopy(), exp2.deepcopy());
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        stmt1.typecheck(typeEnv);
        exp2.typecheck(typeEnv);
        return typeEnv;
    }
}
