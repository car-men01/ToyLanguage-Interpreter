package repository;

import exceptions.FileDoesNotExistException;
import exceptions.MyException;
import model.state.PrgState;

import java.io.IOException;

public interface IRepo {
    PrgState getCrtPrg();
    void addPrgState(PrgState state);
    void logPrgStateExec() throws MyException;
}
