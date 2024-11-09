package model.values;

import model.types.IType;
import model.types.StringType;

public class StringValue implements IValue {
    private String value;

    public StringValue(String value) {
        this.value = value;
    }
    @Override
    public IType getType() {
        return new StringType();
    }

    public String getValue() {
        return this.value;
    }
    @Override
    public String toString() {
        return this.value;
    }

    public boolean equals(IValue val) {
        return val.getType().equals(new StringType()) && ((StringValue)val).getValue().equals(this.value);
    }
    @Override
    public IValue deepcopy() {
        return new StringValue(this.value);
    }
}
