package model.statements;
import model.state.PrgState;
import exceptions.MyException;

public interface IStmt {
    PrgState execute(PrgState state) throws MyException;
    //which is the execution method for a statement.
    IStmt deepcopy();

}



