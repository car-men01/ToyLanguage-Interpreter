package model.statements;
import model.state.PrgState;
import model.exceptions.MyException;
import model.expressions.IExp;
import model.adt.*;
import model.values.IValue;
import model.types.IType;

public class AssignStmt implements IStmt{
    private String id;
    private IExp exp;

    public AssignStmt(String id, IExp exp) {
        this.id = id;
        this.exp = exp;
    }
    public String toString() {
        return id + "=" + exp.toString();
    }
    public PrgState execute(PrgState state) throws MyException{
        MyIStack<IStmt> stack = state.getStack();
        MyIDictionary<String,IValue> symTable= state.getSymTable();

        if (symTable.contains(id)) {
            IValue val = exp.eval(symTable);
            IType typId = (symTable.lookup(id)).getType();
            if (val.getType().equals(typId)) {
                symTable.update(id, val);
            } else
                throw new MyException("declared type of variable" + id + " and type of the assigned expression do not match");
        }
        else throw new MyException("the used variable" + id + " was not declared before");

        return state;
    }
}

