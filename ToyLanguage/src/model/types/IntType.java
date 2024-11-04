package model.types;

import model.values.IValue;
import model.values.IntValue;

public class IntType implements IType {
    public IntType() {};

    public boolean equals(Object another){
        if (another instanceof IntType)
            return true;
        else
            return false;
    }
    public IValue getDefaultValue() {
        return new IntValue(0);
    }
    public String toString() {
        return "int";
    }
    public IType deepcopy() {
        return new IntType();
    }
}

