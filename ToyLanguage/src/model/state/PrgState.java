package model.state;

import model.values.IValue;
import model.adt.MyIDictionary;
import model.adt.MyIList;
import model.adt.MyIStack;
import model.adt.MyDictionary;
import model.adt.MyList;
import model.adt.MyStack;
import model.statements.IStmt;
import model.values.StringValue;
import java.io.BufferedReader;


public class PrgState {
    private MyIStack<IStmt> exeStack;
    private MyIDictionary<String, IValue> symTable;
    private MyIList<IValue> outList;
    private MyIDictionary<StringValue, BufferedReader> fileTable;

    IStmt originalProgram;
    //public int count = 0;

    public PrgState(MyIStack<IStmt> exeStack, MyIDictionary<String,IValue> symTable, MyIList<IValue> outList,
                    IStmt program, MyIDictionary<StringValue, BufferedReader> fileTable){
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.outList = outList;
        this.fileTable = fileTable;

        originalProgram = program.deepcopy();   //recreate the entire original prg
        exeStack.push(program);

        /*
        // sem 6
        init();
        count = 1;
         */
    }

    /*
    // sem 6
    // maybe should be private
    public void init() {
        this.exeStack = new MyStack<IStmt>();       //MyIStack<IStmt>
        this.symTable = new MyDictionary<String, IValue>();  //MyIDictionary<String, IValue>
        this.outList = new MyList<IValue>();      //MyIList<IValue>

        exeStack.push(this.originalProgram);
        this.originalProgram = this.originalProgram.deepcopy();
    }
    */

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

    public String fileTableToString() {
        StringBuilder text = new StringBuilder();
        text.append("FileTable: \n");
        for(StringValue key : this.fileTable.getKeys()) {
            text.append(key).append("\n");
        }
        return text.toString();
    }

    public String toString() {
        return "ExeStack: " + exeStack.toString() + "\nSymTable: " + symTable.toString() + "\nOut: " +
                outList.toString() + "\n" + fileTableToString() + "\n";
    }
}
