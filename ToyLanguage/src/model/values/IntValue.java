package model.values;
import model.types.IntType;
import model.types.IType;

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