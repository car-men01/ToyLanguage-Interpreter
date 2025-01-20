package model.values;

import model.types.IType;
import model.types.RefType;

public class RefValue implements IValue{
    private int heapAddr;
    private IType locationType;

    public RefValue(int heapAddr, IType locationType) {
        this.heapAddr = heapAddr;
        this.locationType = locationType;
    }

    public int getHeapAddr() {
        return this.heapAddr;
    }
    public IType getLocationType() {
        return this.locationType;
    }
    @Override
    public IType getType() {
        return new RefType(this.locationType);
    }
    public boolean equals(IValue val) {
        return val.getType().equals(this.locationType) && ((RefValue)val).getHeapAddr() == this.heapAddr;
    }
    @Override
    public String toString() {
        return "(" + Integer.toString(this.heapAddr) + ", " + this.locationType.toString() + ")";
    }
    @Override
    public IValue deepcopy() {
        return new RefValue(new IntValue(this.heapAddr).getVal(), this.locationType.deepcopy());
    }
}
