package model.types;

public class BoolType implements IType{
    public boolean equals(Object another) {
        if (another instanceof BoolType)
            return true;
        else
            return false;
    }
    public String toString() {
        return "bool";
    }
}
