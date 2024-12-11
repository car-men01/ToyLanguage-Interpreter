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

public class WhileStmt implements IStmt {
    private IStmt stmt;
    private IExp exp;

    public WhileStmt(IExp exp, IStmt stmt) {
        this.exp = exp;
        this.stmt = stmt;
    }

    @Override
    public String toString() {
        return "while (" + exp.toString() + ") {\n" + stmt.toString() + "\n}";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IValue val = exp.eval(state.getSymTable(), state.getHeap());
        if (val instanceof BoolValue boolCond) {
            if (boolCond.getVal()) {
                // if condition is true, push the statement and the while loop back onto the stack
                state.getStack().push(this); // the while loop itself
                state.getStack().push(stmt); // the statement to be executed
            }
        } else {
            throw new StatementException("The condition expression is not a boolean value!");
        }
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, model.types.IType> typeEnv) throws MyException {
        IType typeexp = exp.typecheck(typeEnv);
        if(typeexp.equals(new BoolType()))
        {
            stmt.typecheck(typeEnv);
            return typeEnv;
        }
        else
            throw new MyException("The condition of WHILE has not the type bool");
    }

    @Override
    public IStmt deepcopy() {
        return new WhileStmt(exp.deepcopy(), stmt.deepcopy());
    }
}
