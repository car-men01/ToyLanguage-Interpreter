package repository;

import model.state.PrgState;

public class Repository implements IRepo {
    private PrgState crtPrg;

    public Repository(PrgState crtPrg) {
        this.crtPrg = crtPrg;
    }

    public PrgState getCrtPrg() {
        return crtPrg;
    }
}
