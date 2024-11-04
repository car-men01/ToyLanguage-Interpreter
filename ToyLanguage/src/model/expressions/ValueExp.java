package model.expressions;

import model.adt.MyIDictionary;
import model.values.IValue;

public class ValueExp implements IExp {
    private IValue value;

    public ValueExp(IValue value) {
        this.value = value;
    }

    public IValue eval(MyIDictionary<String,IValue> tbl) {
        return value;
    }

    public String toString() {
        return value.toString();
    }

    public IExp deepcopy() {
        return new ValueExp(value.deepcopy());
    }
}
