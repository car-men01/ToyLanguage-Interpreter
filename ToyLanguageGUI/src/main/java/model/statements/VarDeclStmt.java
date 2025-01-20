package model.statements;

import exceptions.StatementException;
import model.adt.MyIDictionary;
import model.state.PrgState;
import model.types.IType;

public class VarDeclStmt implements IStmt {
    private String name;
    private IType type;

    public VarDeclStmt(String name, IType type) {
        this.name = name;
        this.type = type;
    }
    @Override
    public PrgState execute(PrgState state) throws StatementException {
        if(state.getSymTable().contains(this.name))
            throw new StatementException("A variable with the same name already exists!");
        state.getSymTable().insert(this.name, this.type.getDefaultValue());
        return null;
    }
    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws StatementException {
        typeEnv.insert(this.name, this.type);
        return typeEnv;
    }
    @Override
    public String toString() {
        return this.type.toString() + " " + this.name;
    }
    @Override
    public IStmt deepcopy() {
        return new VarDeclStmt(new String(name), type.deepcopy());
    }
}

