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
    public String toString() {
        return Integer.toString(val);
    }
    public IType getType() {
        return new IntType();
    }
    public IValue deepcopy() {
        return new IntValue(val);
    }
}