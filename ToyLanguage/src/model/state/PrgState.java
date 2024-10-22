package model.state;


import model.adt.MyIStack;
import model.statements.IStmt;

public class PrgState {
    MyIStack<IStmt> exeStack;
    MyIDictionary<String, Value> symTable;
    MyIList<Value> out;
    IStmt originalProgram; //optional field, but good to have
    PrgState(MyIStack<IStmt> stk, MyIDictionary<String,Value> symtbl, MyIList<Value> ot, Istmt prg){
        exeStack=stk;
        symTable=symtbl;
        out = ot;
        originalProgram=deepCopy(prg);//recreate the entire original prg
        stk.push(prg);
    }

    public MyIStack<IStmt> getStack() {
    }
    //.....
}
