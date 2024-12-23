package repository;

import exceptions.MyException;
import model.state.PrgState;

import java.util.List;

public interface IRepo {
    //PrgState getCrtPrg();
    void addPrgState(PrgState state);
    void setPrgList(List<PrgState> prgStates);
    List<PrgState> getPrgList();
    void logPrgStateExec(PrgState prgState) throws MyException;
}
