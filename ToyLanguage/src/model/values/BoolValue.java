package model.values;
import model.types.BoolType;
import model.types.IType;

public class BoolValue implements IValue {
    boolean val;

    public BoolValue(boolean v) {
        val = v;
    }
    public boolean getVal() {
        return val;
    }
    public String toString() {
        return Boolean.toString(val);
    }
    public IType getType() {
        return new BoolType();
    }
}
