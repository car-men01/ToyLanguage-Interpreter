package model.types;

import model.values.IValue;
import model.values.StringValue;

public class StringType implements IType{
    public StringType() {}
    @Override
    public String toString() {
        return "string";
    }
    @Override
    public boolean equals(Object obj) {
        return obj instanceof StringType;
    }
    @Override
    public IValue getDefaultValue() {
        return new StringValue("");
    }
    @Override
    public IType deepcopy() {
        return new StringType();
    }
}
