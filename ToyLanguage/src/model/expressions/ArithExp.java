package model.expressions;

import model.values.IValue;
import model.values.IntValue;
import model.types.IntType;
import model.adt.MyIDictionary;
import model.exceptions.MyException;

public class ArithExp implements IExp{
    private IExp e1;
    private IExp e2;
    int op; //1-plus, 2-minus, 3-star, 4-divide

    public ArithExp(IExp e1, IExp e2, int op) {
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }

    public IValue eval(MyIDictionary<String,IValue> tbl) throws MyException {
        IValue v1,v2;
        v1 = e1.eval(tbl);
        if (v1.getType().equals(new IntType())) {
            v2 = e2.eval(tbl);
            if (v2.getType().equals(new IntType())) {
                IntValue i1 = (IntValue)v1;
                IntValue i2 = (IntValue)v2;
                int n1,n2;
                n1 = i1.getVal();
                n2 = i2.getVal();
                if (op==1) return new IntValue(n1+n2);
                if (op==2) return new IntValue(n1-n2);
                if (op==3) return new IntValue(n1*n2);
                if (op==4) {
                    if (n2==0) throw new MyException("division by zero");
                    return new IntValue(n1/n2);
                }
            } else throw new MyException("second operand is not an integer");
        } else throw new MyException("first operand is not an integer");
        return null;
    }
}
