package model.types;

import model.values.IValue;
import model.values.IntValue;

public class IntType implements IType {
    public IntType() {};
    @Override
    public boolean equals(Object another){
        if (another instanceof IntType)
            return true;
        else
            return false;
    }
    @Override
    public IValue getDefaultValue() {
        return new IntValue(0);
    }
    @Override
    public String toString() {
        return "int";
    }
    @Override
    public IType deepcopy() {
        return new IntType();
    }
}

