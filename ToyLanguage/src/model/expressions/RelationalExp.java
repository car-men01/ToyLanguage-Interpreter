package model.expressions;

import exceptions.MyException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.types.IType;
import model.types.IntType;
import model.values.BoolValue;
import model.values.IValue;
import model.values.IntValue;

public class RelationalExp implements IExp{
    private IExp e1;
    private IExp e2;
    private String op;

    public RelationalExp(IExp e1, IExp e2, String op) {
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> tbl, MyIHeap<Integer, IValue> heap) throws MyException {
        IValue v1, v2;
        v1 = e1.eval(tbl, heap);
        if (v1.getType().equals(new IntType())) {
            v2 = e2.eval(tbl, heap);
            if (v2.getType().equals(new IntType())) {
                IntValue i1 = (IntValue)v1;
                IntValue i2 = (IntValue)v2;
                int val1, val2;
                val1 = i1.getVal();
                val2 = i2.getVal();
                if (op.equals("<")) {
                    return new BoolValue(val1 < val2);
                } else if (op.equals("<=")) {
                    return new BoolValue(val1 <= val2);
                } else if (op.equals("==")) {
                    return new BoolValue(val1 == val2);
                } else if (op.equals("!=")) {
                    return new BoolValue(val1 != val2);
                } else if (op.equals(">")) {
                    return new BoolValue(val1 > val2);
                } else if (op.equals(">=")) {
                    return new BoolValue(val1 >= val2);
                } else {
                    throw new MyException("Invalid operator");
                }
            } else throw new MyException("Invalid type, second operand is not an integer");
        } else throw new MyException("Invalid type, first operand is not an integer");
    }

    @Override
    public IType typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType t1, t2;
        t1 = e1.typecheck(typeEnv);
        t2 = e2.typecheck(typeEnv);
        if (t1.equals(new IntType())) {
            if (t2.equals(new IntType())) {
                return new BoolValue(true).getType();
            } else {
                throw new MyException("Second operand is not an integer");
            }
        } else {
            throw new MyException("First operand is not an integer");
        }
    }

    @Override
    public String toString() {
        return e1.toString() + " " + op + " " + e2.toString();
    }
    @Override
    public IExp deepcopy() {
        return new RelationalExp(e1.deepcopy(), e2.deepcopy(), op);
    }
}
