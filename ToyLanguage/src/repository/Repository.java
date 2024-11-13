package repository;

import exceptions.FileDoesNotExistException;
import exceptions.MyException;
import model.state.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


public class Repository implements IRepo {
    private List<PrgState> prgStates;
    private String logFilePath;
    private int countStatePos;

    public Repository(PrgState prgState, String logFilePath) {
        this.prgStates = new ArrayList<>();
        this.prgStates.add(prgState);
        this.countStatePos = 0;
        this.logFilePath = logFilePath;

        //PrintWriter
       // this.initializeLogFilePath();
    }

    /*
    private void initializeLogFilePath() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the log file path: ");
        this.logFilePath = scanner.nextLine();
    }

     */

    @Override
    public PrgState getCrtPrg() {
        return prgStates.get(this.getCountStatePos()); // this.countStatePos;
    }

    @Override
    public void addPrgState(PrgState state) {
        prgStates.add(state);
        this.setCountStatePos(this.getCountStatePos() + 1);
    }
    @Override
    public void logPrgStateExec() throws MyException {
        try {
            PrgState prgState = this.getCrtPrg();
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(this.logFilePath, true)));
            logFile.println(prgState.toString());
            logFile.close();
        }
        catch (IOException e) {
            throw new FileDoesNotExistException("The log file does not exist!");
        }
    }

    public int getCountStatePos() {
        return countStatePos;
    }

    public void setCountStatePos(int countStatePos) {
        this.countStatePos = countStatePos;
    }
}
