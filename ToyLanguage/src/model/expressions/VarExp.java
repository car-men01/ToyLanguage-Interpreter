package model.expressions;

import model.adt.MyIDictionary;
import exceptions.MyException;
import model.values.IValue;

public class VarExp implements IExp{
    private String id;

    public VarExp(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public IValue eval(MyIDictionary<String,IValue> tbl) throws MyException {
        return tbl.lookup(id);
    }

    public String toString() {
        return id;
    }

    public IExp deepcopy() {
        return new VarExp(new String(id));
    }
}
