package model.statements;

import exceptions.MyException;
import exceptions.StatementException;
import model.adt.MyIDictionary;
import model.expressions.IExp;
import model.state.PrgState;
import model.types.IType;
import model.types.IntType;
import model.types.StringType;
import model.values.IValue;
import model.values.IntValue;
import model.values.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStmt implements IStmt{
    private IExp exp;
    private String varName;

    public ReadFileStmt(IExp exp, String varName) {
        this.exp = exp;
        this.varName = varName;
    }
    @Override
    public String toString() {
        return "readFile(" + exp.toString() + ", " + varName + ")";
    }
    @Override
    public IStmt deepcopy() {
        return new ReadFileStmt(exp.deepcopy(), varName);
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, IValue> table = state.getSymTable();

        if (!table.contains(varName)) {
            throw new StatementException("The variable was not defined");
        }

        if (!table.lookup(varName).getType().equals(new IntType())) {
            throw new StatementException("The variable type should be int");
        }

        IValue value = this.exp.eval(table, state.getHeap());
        if (!value.getType().equals(new StringType())) {
            throw new StatementException("The value should be a string");
        }

        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        BufferedReader reader = fileTable.lookup((StringValue) value);

        if (reader == null) {
            throw new StatementException("The value has no entry associated in the table");
        }

        try {
            String line = reader.readLine();
            if (line == null) {
                line = "0";
            }
            else {
                int parser = Integer.parseInt(line);
                table.insert(varName, new IntValue(parser));
            }

        } catch (IOException e) {
            throw new StatementException("Could not read line");
        }

        return null;
    }
    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType typeexp = exp.typecheck(typeEnv);
        if (typeexp.equals(new StringType())) {
            return typeEnv;
        }
        else {
            throw new StatementException("The expression should be a string");
        }
    }
}
