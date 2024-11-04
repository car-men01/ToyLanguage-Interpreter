package model.state;

import model.values.IValue;
import model.adt.MyIDictionary;
import model.adt.MyIList;
import model.adt.MyIStack;
import model.statements.IStmt;

public class PrgState {
    MyIStack<IStmt> exeStack;
    MyIDictionary<String, IValue> symTable;
    MyIList<IValue> outList;

    //IStmt originalProgram; //optional field, but good to have

    public PrgState(MyIStack<IStmt> exeStack, MyIDictionary<String,IValue> symTable, MyIList<IValue> outList, IStmt program){
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.outList = outList;
        //originalProgram = program.deepCopy();//recreate the entire original prg
        exeStack.push(program);
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

    public String toString() {
        return "ExeStack: " + exeStack.toString() + "\nSymTable: " + symTable.toString() + "\nOut: " + outList.toString() + "\n";
    }
}
