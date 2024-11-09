package controller;
import model.adt.MyIStack;
import exceptions.MyException;
import model.state.PrgState;
import model.statements.IStmt;
import repository.IRepo;

public class Controller {
    private IRepo repo;

    public Controller(IRepo repo) {
        this.repo = repo;
    }

    public PrgState oneStep(PrgState state) throws MyException {
        MyIStack<IStmt> stack = state.getStack();
        if (stack.isEmpty()) {
            throw new MyException("Stack is empty");
        }
        IStmt currentStmt = stack.pop();
        return currentStmt.execute(state);
    }

    public void allStep() throws MyException {
        PrgState prg = repo.getCrtPrg(); // repo is the controller field of type MyIRepo
        /*
        // seminar 6
        if (prg.count > 1) {
            prg.init();
        }
        prg.count++;
         */
        this.displayPrgState();
        repo.logPrgStateExec();
        while (!prg.getStack().isEmpty()) {
            oneStep(prg);
            this.displayPrgState();
            repo.logPrgStateExec();
        }
    }

    public void displayPrgState() {
        System.out.println(repo.getCrtPrg());
    }
}
