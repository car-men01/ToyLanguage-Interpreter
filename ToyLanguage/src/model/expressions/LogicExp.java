package model.expressions;

import exceptions.TypeException;
import model.adt.MyIDictionary;
import exceptions.MyException;
import model.adt.MyIHeap;
import model.types.IType;
import model.values.BoolValue;
import model.values.IValue;
import model.types.BoolType;

public class LogicExp implements IExp{
    private IExp e1;
    private IExp e2;
    private int op;

    public LogicExp(IExp e1, IExp e2, int op) {
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }
    @Override
    public IValue eval(MyIDictionary<String,IValue> tbl, MyIHeap<Integer, IValue> heap) throws MyException {
        IValue v1,v2;
        v1 = e1.eval(tbl, heap);
        if (v1.getType().equals(new BoolType())) {
            v2 = e2.eval(tbl, heap);
            if (v2.getType().equals(new BoolType())) {
                BoolValue i1 = (BoolValue)v1;
                BoolValue i2 = (BoolValue)v2;
                boolean n1,n2;
                n1 = i1.getVal();
                n2 = i2.getVal();
                if (op==1) return new BoolValue(n1&&n2);
                if (op==2) return new BoolValue(n1||n2);
            } else throw new TypeException("Second operand is not a boolean");
        } else throw new TypeException("First operand is not a boolean");
        return null;
    }

    @Override
    public IType typecheck(MyIDictionary<String,IType> typeEnv) throws MyException {
        IType t1, t2;
        t1 = e1.typecheck(typeEnv);
        t2 = e2.typecheck(typeEnv);
        if (t1.equals(new BoolType())) {
            if (t2.equals(new BoolType())) {
                return new BoolType();
            } else throw new TypeException("Second operand is not a boolean");
        } else throw new TypeException("First operand is not a boolean");
    }

    @Override
    public IExp deepcopy() {
        return new LogicExp(e1.deepcopy(), e2.deepcopy(), op);
    }

    @Override
    public String toString() {
        String opStr = "";
        if (op == 1) {
            opStr = "&&";
        } else if (op == 2) {
            opStr = "||";
        }
        return e1.toString() + " " + opStr + " " + e2.toString();
    }
}
