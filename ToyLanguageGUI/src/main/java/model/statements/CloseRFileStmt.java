package model.statements;

import exceptions.MyException;
import exceptions.StatementException;
import model.adt.MyIDictionary;
import model.expressions.IExp;
import model.state.PrgState;
import model.types.StringType;
import model.values.IValue;
import model.values.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class    CloseRFileStmt implements IStmt{
    private IExp exp;

    public CloseRFileStmt(IExp exp) {
        this.exp = exp;
    }
    @Override
    public String toString() {
        return "closeFile(" + exp.toString() + ")";
    }
    @Override
    public IStmt deepcopy() {
        return new CloseRFileStmt(exp.deepcopy());
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, IValue> table = state.getSymTable();
        IValue value = this.exp.eval(table, state.getHeap());

        if (!value.getType().equals(new StringType())) {
            throw new StatementException("The type of the value must be a string");
        }

        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        BufferedReader reader = fileTable.lookup((StringValue) value);
        if (reader == null) {
            throw new StatementException("The value has no entry in the table");
        }

        try {
            reader.close();
        } catch (IOException e) {
            throw new StatementException("File could not be closed");
        }
        fileTable.remove((StringValue) value);

        return null;
    }
    @Override
    public MyIDictionary<String, model.types.IType> typecheck(MyIDictionary<String, model.types.IType> typeEnv) throws MyException {
        exp.typecheck(typeEnv);
        return typeEnv;
    }
}
