package model.state;

import exceptions.MyException;
import javafx.util.Pair;
import model.adt.*;
import model.statements.IStmt;
import model.values.IValue;
import model.values.StringValue;

import java.io.BufferedReader;
import java.util.List;


public class PrgState {
    private MyIStack<IStmt> exeStack;
    //private MyIDictionary<String, IValue> symTable;
    private MyIStack<MyIDictionary <String, IValue>> symTables;
    private MyIList<IValue> outList;
    private MyIDictionary<StringValue, BufferedReader> fileTable;
    private MyIHeap<Integer, IValue> heap;
    //private MyIDictionary<String, IType> typeEnv;
    private MyIProc<String, Pair<List<String>, IStmt>> procTable;

    IStmt originalProgram;
    private int id;
    private static int globalId = 1;

    public static synchronized int generateId() {
        return globalId++;
    }

    public PrgState(MyIStack<IStmt> exeStack, MyIDictionary<String,IValue> symTable, MyIList<IValue> outList,
                    IStmt program, MyIDictionary<StringValue, BufferedReader> fileTable, MyIHeap<Integer, IValue> heap,
                    MyIProc<String, Pair<List<String>, IStmt>> procTable) {
        this.exeStack = exeStack;
        //this.symTable = symTable;
        this.symTables = new MyStack<MyIDictionary<String, IValue>>();
        this.symTables.push(symTable);
        this.outList = outList;
        this.fileTable = fileTable;
        this.heap = heap;
        this.procTable = procTable;

        originalProgram = program.deepcopy();   //recreate the entire original prg
        exeStack.push(program);
        this.id = generateId();
    }

    public PrgState(MyIStack<IStmt> exeStack, MyIStack<MyIDictionary<String,IValue>> symTables, MyIList<IValue> outList,
                    IStmt program, MyIDictionary<StringValue, BufferedReader> fileTable, MyIHeap<Integer, IValue> heap,
                    MyIProc<String, Pair<List<String>, IStmt>> procTable) {
        this.exeStack = exeStack;
        //this.symTable = symTable;
        this.symTables = symTables;
        this.outList = outList;
        this.fileTable = fileTable;
        this.heap = heap;
        this.procTable = procTable;

        originalProgram = program.deepcopy();   //recreate the entire original prg
        exeStack.push(program);
        this.id = generateId();
    }

    public MyIProc<String, Pair<List<String>, IStmt>> getProcTable() {
        return this.procTable;
    }

    public MyIStack<IStmt> getStack() {
        return exeStack;
    }

//    //public MyIDictionary<String, IValue> getSymTable() {
//        return symTable;
//    }

    public MyIStack<MyIDictionary <String, IValue>> getSymTables() {
        return symTables;
    }

    public MyIDictionary<String, IValue> getSymTable() {
        return symTables.peek();
    }

    public MyIList<IValue> getOut() {
        return outList;
    }

    public MyIDictionary<StringValue, BufferedReader> getFileTable(){
        return this.fileTable;
    }

    public MyIHeap<Integer, IValue> getHeap() {
        return this.heap;
    }

    public boolean isNotCompleted() {
        return !exeStack.isEmpty();
    }

    public int getId() {
        return this.id;
    }

    public PrgState oneStep() throws MyException {
        if(exeStack.isEmpty()) {
            throw new RuntimeException("Program state stack is empty");
        }
        IStmt currentStatement = exeStack.pop();
        return currentStatement.execute(this);
    }

    public String fileTableToString() {
        StringBuilder text = new StringBuilder();
        text.append("FileTable:\n");
        for(StringValue key : this.fileTable.getKeys()) {
            text.append(key).append("\n");
        }
        return text.toString();
    }

    public String procedureTableToString() throws MyException {
        StringBuilder procedureTableStringBuilder = new StringBuilder();
        for (String key: procTable.getAddresses()) {
            procedureTableStringBuilder.append(String.format("%s - %s: %s\n", key, procTable.getContent(key).getKey(), procTable.getContent(key).getValue()));
        }
        procedureTableStringBuilder.append("\n");
        return procedureTableStringBuilder.toString();
    }

    public String toString() {
        return "Program id: " + this.getId() + "\n\nExeStack:\n" + exeStack.toString() + "\n\nSymTable:\n" + symTables.toString() + "\nOut:\n" +
                outList.toString() + "\n\n" + fileTableToString() + "\nHeap:\n" + heap.toString() + "\n" + "Proc Table:\n" + procTable.toString() + "\n\n";
    }
}
