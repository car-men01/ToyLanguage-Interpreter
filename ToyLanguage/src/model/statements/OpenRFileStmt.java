package model.statements;

import exceptions.StatementException;
import model.adt.MyIDictionary;
import model.expressions.IExp;
import model.state.PrgState;
import exceptions.MyException;
import model.types.StringType;
import model.values.IValue;
import model.values.StringValue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OpenRFileStmt implements IStmt{
    private IExp exp;

    public OpenRFileStmt(IExp exp) {
        this.exp = exp;
    }
    @Override
    public String toString() {
        return "openRFile(" + exp.toString() + ")";
    }
    @Override
    public IStmt deepcopy() {
        return new OpenRFileStmt(exp.deepcopy());
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, IValue> table = state.getSymTable();
        IValue value = this.exp.eval(table, state.getHeap());

        if (!value.getType().equals(new StringType())) {
            throw new StatementException("The type is incorrect, should be string.");
        }

        if (state.getFileTable().contains((StringValue) value)) {
            throw new StatementException("The value already exists in the file table");
        }

        try {
            FileReader fileReader = new FileReader(((StringValue) value).getValue());
            BufferedReader reader = new BufferedReader(fileReader);
            state.getFileTable().insert((StringValue) value, reader);
        } catch (IOException e) {
            System.out.print(e.getMessage());
            throw new StatementException("The file does not exist or could not be opened");
        }

        return null;
    }
}
