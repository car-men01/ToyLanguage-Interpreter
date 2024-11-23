package model.expressions;

import model.adt.MyIDictionary;
import exceptions.MyException;
import model.adt.MyIHeap;
import model.values.IValue;

public class VarExp implements IExp{
    private String var;

    public VarExp(String var) {
        this.var = var;
    }

    @Override
    public IValue eval(MyIDictionary<String,IValue> tbl, MyIHeap<Integer, IValue> heap) throws MyException {
        return tbl.lookup(var);
    }
    @Override
    public String toString() {
        return var;
    }
    @Override
    public IExp deepcopy() {
        return new VarExp(new String(var));
    }
}
