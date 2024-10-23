package model.types;

import model.values.BoolValue;
import model.values.IValue;

public class BoolType implements IType{
    public BoolType() {};

    public boolean equals(Object another) {
        if (another instanceof BoolType)
            return true;
        else
            return false;
    }
    public IValue getDefaultValue() {
        return new BoolValue(false);
    }
    public String toString() {
        return "bool";
    }
}
