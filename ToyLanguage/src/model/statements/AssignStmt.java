package model.statements;
import exceptions.KeyNotFoundException;
import exceptions.MyException;
import model.state.PrgState;
import model.expressions.IExp;
import model.adt.*;
import model.values.IValue;
import model.types.IType;
import exceptions.StatementException;

public class AssignStmt implements IStmt{
    private String id;
    private IExp exp;

    public AssignStmt(String id, IExp exp) {
        this.id = id;
        this.exp = exp;
    }
    @Override
    public String toString() {
        return id + " = " + exp.toString();
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String,IValue> symTable= state.getSymTable();

        if (symTable.contains(id)) {
            IValue val = exp.eval(symTable, state.getHeap());
            IType typeId = (symTable.lookup(id)).getType();
            if (val.getType().equals(typeId)) {
                symTable.update(id, val);
            } else throw new StatementException("The type of variable " + id + " and type of the assigned expression do not match");
        }
        else throw new StatementException("The used variable " + id + " was not declared before");

        return null;
    }
    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType typevar = typeEnv.lookup(id);
        IType typexp = exp.typecheck(typeEnv);
        if (typevar.equals(typexp))
            return typeEnv;
        else
            throw new MyException("Assignment: right hand side and left hand side have different types ");
    }
    @Override
    public IStmt deepcopy() {
        return new AssignStmt(new String(id), exp.deepcopy());
    }
}

