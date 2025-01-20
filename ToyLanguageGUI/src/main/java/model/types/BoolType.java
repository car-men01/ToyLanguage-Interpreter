package model.types;

import model.values.BoolValue;
import model.values.IValue;

public class BoolType implements IType{
    public BoolType() {};
    @Override
    public boolean equals(Object another) {
        if (another instanceof BoolType)
            return true;
        else
            return false;
    }
    @Override
    public IValue getDefaultValue() {
        return new BoolValue(false);
    }
    @Override
    public String toString() {
        return "bool";
    }
    @Override
    public IType deepcopy() {
        return new BoolType();
    }
}
