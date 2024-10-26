package repository;

import model.state.PrgState;

import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepo {
    private List<PrgState> prgStates;

    public Repository() {
        this.prgStates = new ArrayList<>();
    }

    public PrgState getCrtPrg() {
        return prgStates.get(0); // returning the first PrgState for now
    }

    /*
    public void addPrgState(PrgState state) {
        prgStates.add(state);
    }
    */


}
