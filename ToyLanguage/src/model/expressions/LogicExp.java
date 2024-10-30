package model.expressions;

import exceptions.TypeException;
import model.adt.MyIDictionary;
import exceptions.MyException;
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

    public IValue eval(MyIDictionary<String,IValue> tbl) throws MyException {
        IValue v1,v2;
        v1 = e1.eval(tbl);
        if (v1.getType().equals(new BoolType())) {
            v2 = e2.eval(tbl);
            if (v2.getType().equals(new BoolType())) {
                BoolValue i1 = (BoolValue)v1;
                BoolValue i2 = (BoolValue)v2;
                boolean n1,n2;
                n1 = i1.getVal();
                n2 = i2.getVal();
                if (op==1) return new BoolValue(n1&&n2);
                if (op==2) return new BoolValue(n1||n2);
            } else throw new TypeException("second operand is not a boolean");
        } else throw new TypeException("first operand is not a boolean");
        return null;
    }

    public IExp deepcopy() {
        return new LogicExp(e1.deepcopy(), e2.deepcopy(), op);
    }
}
