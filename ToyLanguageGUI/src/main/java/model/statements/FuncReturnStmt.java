package model.statements;

import exceptions.MyException;
import javafx.util.Pair;
import model.adt.MyDictionary;
import model.adt.MyIDictionary;
import model.adt.MyList;
import model.expressions.IExp;
import model.state.PrgState;
import model.types.IType;
import model.values.IValue;

import java.util.List;
import java.util.Vector;

public class FuncReturnStmt implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws MyException {
        try {
            state.getSymTables().pop();
        } catch (MyException e) {
            throw new MyException(e.getMessage());
        }

        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public IStmt deepcopy() {
        return new FuncReturnStmt();
    }

    @Override
    public String toString() {
        return "return";
    }
}
