package controller;
import model.adt.MyIStack;
import model.exceptions.MyException;
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
        this.displayPrgState();
        while (!prg.getStack().isEmpty()) {
            oneStep(prg);
            this.displayPrgState();
        }
    }

    public void displayPrgState() {
        System.out.println(repo.getCrtPrg());
    }
}
