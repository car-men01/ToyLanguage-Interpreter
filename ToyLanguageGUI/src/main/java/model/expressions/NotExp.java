package model.expressions;

import exceptions.MyException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.types.BoolType;
import model.types.IType;
import model.values.BoolValue;
import model.values.IValue;

public class NotExp implements IExp{
    private IExp exp;

    public NotExp(IExp exp) {
        this.exp = exp;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> tbl, MyIHeap<Integer,IValue> heap) throws MyException {
        IValue v = exp.eval(tbl, heap);
        if (v.getType().equals(new BoolType())) {
            return new BoolValue(!((BoolValue) v).getVal());
        }
        throw new MyException("The expression is not a boolean");
    }

    @Override
    public String toString() {
        return "!" + exp.toString();
    }

    @Override
    public IExp deepcopy() {
        return new NotExp(exp.deepcopy());
    }

    @Override
    public IType typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType t = exp.typecheck(typeEnv);
        if (t.equals(new BoolType())) {
            return new BoolType();
        }
        throw new MyException("The expression is not a boolean");
    }
}
