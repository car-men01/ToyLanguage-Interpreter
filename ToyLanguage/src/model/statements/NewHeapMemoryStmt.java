package model.statements;

import exceptions.KeyNotFoundException;
import exceptions.MyException;
import exceptions.StatementException;
import exceptions.TypeException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.expressions.IExp;
import model.state.PrgState;
import model.values.IValue;
import model.values.RefValue;

import java.util.HashMap;

public class NewHeapMemoryStmt implements IStmt{
    private String varName;
    private IExp exp;

    public NewHeapMemoryStmt(String varName, IExp exp) {
        this.varName = varName;
        this.exp = exp;
    }
    @Override
    public String toString() {
        return "new(" + varName + ", " + exp.toString() + ")";
    }
    @Override
    public IStmt deepcopy() {
        return new NewHeapMemoryStmt(new String(this.varName), this.exp.deepcopy());
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, IValue> symTable = state.getSymTable();
        IValue val = exp.eval(symTable, state.getHeap());

        if (!symTable.contains(varName)) {
            throw new KeyNotFoundException("The variable " + varName + " was not found in the symTable");
        }

        IValue varValue = symTable.lookup(varName);

        if(!(varValue instanceof RefValue)) {
            throw new TypeException("The variable " + varName + " is not a reference");
        }

        RefValue refValue = (RefValue) varValue;
        if (!refValue.getLocationType().equals(val.getType())) {
            throw new TypeException("The type of the variable and the type of the expression do not match");
        }

//        if (!symTable.lookup(varName).getType().equals(val.getType())) {
//            throw new TypeException("The type of the variable and the type of the expression do not match");
//        }
//        if (!val.getType().equals(symTable.lookup(varName).getType())) {
//            throw new TypeException("The type of the variable and the type of the expression do not match");
//        }

        MyIHeap<Integer, IValue> heap = state.getHeap();
        int address = heap.getNextFreeAddr();

        heap.insert(address, val);
        RefValue refVal = new RefValue(address, val.getType());
        symTable.update(varName, refVal);

        return state;
    }
}
