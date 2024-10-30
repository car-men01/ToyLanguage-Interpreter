package repository;

import model.state.PrgState;

public interface IRepo {
    public PrgState getCrtPrg();
    public void addPrgState(PrgState prg);
}
