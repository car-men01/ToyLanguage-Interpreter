package model.expressions;

import exceptions.KeyNotFoundException;
import exceptions.MyException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.values.IValue;
import model.values.RefValue;

public class ReadHeapExp implements IExp{
    private IExp exp;

    public ReadHeapExp(IExp exp) {
        this.exp = exp;
    }
    @Override
    public String toString() {
        return "readHeap(" + exp.toString() + ")";
    }
    @Override
    public IValue eval(MyIDictionary<String, IValue> tbl, MyIHeap<Integer, IValue> heap) throws MyException {
        IValue val = exp.eval(tbl, heap);
        if (val instanceof RefValue refValue) {
            int address = refValue.getHeapAddr();
            IValue value = heap.getContent(address);
            if (value == null) {
                throw new KeyNotFoundException("The address is not a key in the heap table");
            }
            return value;
        } else {
            throw new MyException("Value is not a reference");
        }
    }
    @Override
    public IExp deepcopy() {
        return new ReadHeapExp(exp.deepcopy());
    }
}
