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
    @Override
    public String toString() {
        return Boolean.toString(val);
    }
    @Override
    public IType getType() {
        return new BoolType();
    }
    @Override
    public IValue deepcopy() { return new BoolValue(val); }
    public boolean equals(IValue val) {
        return val.getType().equals(new BoolType()) && ((BoolValue)val).getVal() == this.val;
    }
}
