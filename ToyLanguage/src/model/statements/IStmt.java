package model.statements;
import model.state.PrgState;
import model.exceptions.MyException;

public interface IStmt {
    PrgState execute(PrgState state) throws MyException;
    //which is the execution method for a statement.

}



