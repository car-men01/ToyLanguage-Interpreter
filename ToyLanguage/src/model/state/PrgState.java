package model.state;

import exceptions.MyException;
import model.adt.*;
import model.types.IType;
import model.values.IValue;
import model.statements.IStmt;
import model.values.StringValue;
import java.io.BufferedReader;


public class PrgState {
    private MyIStack<IStmt> exeStack;
    private MyIDictionary<String, IValue> symTable;
    private MyIList<IValue> outList;
    private MyIDictionary<StringValue, BufferedReader> fileTable;
    private MyIHeap<Integer, IValue> heap;
    //private MyIDictionary<String, IType> typeEnv;

    IStmt originalProgram;
    private int id;
    private static int globalId = 1;

    public static synchronized int generateId() {
        return globalId++;
    }

    public PrgState(MyIStack<IStmt> exeStack, MyIDictionary<String,IValue> symTable, MyIList<IValue> outList,
                    IStmt program, MyIDictionary<StringValue, BufferedReader> fileTable, MyIHeap<Integer, IValue> heap) {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.outList = outList;
        this.fileTable = fileTable;
        this.heap = heap;

        originalProgram = program.deepcopy();   //recreate the entire original prg
        exeStack.push(program);
        this.id = generateId();
    }

    public MyIStack<IStmt> getStack() {
        return exeStack;
    }

    public MyIDictionary<String, IValue> getSymTable() {
        return symTable;
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

    public String toString() {
        return "Program id: " + this.getId() + "\n\nExeStack:\n" + exeStack.toString() + "\n\nSymTable:\n" + symTable.toString() + "\nOut:\n" +
                outList.toString() + "\n\n" + fileTableToString() + "\nHeap:\n" + heap.toString() + "\n\n";
    }
}
