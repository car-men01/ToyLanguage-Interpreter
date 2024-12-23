package model.statements;

import exceptions.MyException;
import model.adt.MyIDictionary;
import model.state.PrgState;
import model.types.IType;

public interface IStmt {
    PrgState execute(PrgState state) throws MyException;
    MyIDictionary<String, IType> typecheck(MyIDictionary<String,IType> typeEnv) throws MyException;
    IStmt deepcopy();

}



