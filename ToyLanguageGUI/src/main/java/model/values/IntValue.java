package model.values;

import model.types.IType;
import model.types.IntType;

public class IntValue implements IValue{
    private int val;

    public IntValue(int v) {
        val = v;
    }
    public int getVal() {
        return val;
    }
    @Override
    public String toString() {
        return Integer.toString(val);
    }
    @Override
    public IType getType() {
        return new IntType();
    }
    @Override
    public IValue deepcopy() {
        return new IntValue(val);
    }
    public boolean equals(IValue val) {
        return val.getType().equals(new IntType()) && ((IntValue)val).getVal() == this.val;
    }
}