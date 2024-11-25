package model.statements;

import exceptions.MyException;
import exceptions.StatementException;
import model.expressions.IExp;
import model.state.PrgState;
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
        if (val instanceof BoolValue) {
            BoolValue boolCond = (BoolValue) val;
            if (boolCond.getVal()) {
                // if condition is true, push the statement and the while loop back onto the stack
                state.getStack().push(this); // the while loop itself
                state.getStack().push(stmt); // the statement to be executed
            }
        } else {
            throw new StatementException("The condition expression is not a boolean value!");
        }
        return state;
    }

    @Override
    public IStmt deepcopy() {
        return new WhileStmt(exp.deepcopy(), stmt.deepcopy());
    }
}
