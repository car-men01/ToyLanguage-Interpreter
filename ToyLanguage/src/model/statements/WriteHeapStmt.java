package model.statements;

import exceptions.KeyNotFoundException;
import exceptions.MyException;
import exceptions.TypeException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.adt.MyIStack;
import model.expressions.IExp;
import model.state.PrgState;
import model.types.IType;
import model.values.IValue;
import model.values.RefValue;

public class WriteHeapStmt implements IStmt{
    private String varName;
    private IExp exp;

    public WriteHeapStmt(String varName, IExp exp) {
        this.varName = varName;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, IValue> symTbl = state.getSymTable();
        MyIHeap<Integer, IValue> heap = state.getHeap();

        if(!symTbl.contains(varName))
            throw new KeyNotFoundException("Variable not found in symbol table!");

        IValue val = symTbl.lookup(varName);
        if(!(val instanceof RefValue refVal))
            throw new TypeException("Value is not a ref value");

        if(!heap.contains(refVal.getHeapAddr()))
            throw new KeyNotFoundException("Address not found in heap");

        IValue value = exp.eval(symTbl, heap);
        if(!value.getType().equals(refVal.getLocationType()))
            throw new TypeException("The type of the expression does not match the location type");

        heap.update(refVal.getHeapAddr(), value);

        return null;
    }

    @Override
    public IStmt deepcopy() {
        return new WriteHeapStmt(varName, exp.deepcopy());
    }

    @Override
    public String toString() {
        return "writeHeap(" + varName + ", " + exp.toString() + ")";
    }
}
