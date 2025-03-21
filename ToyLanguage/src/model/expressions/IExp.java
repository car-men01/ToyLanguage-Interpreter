package model.expressions;

import model.adt.MyIDictionary;
import exceptions.MyException;
import model.adt.MyIHeap;
import model.types.IType;
import model.values.IValue;

public interface IExp {
    IValue eval(MyIDictionary<String,IValue> tbl, MyIHeap<Integer,IValue> heap) throws MyException;
    IType typecheck(MyIDictionary<String,IType> typeEnv) throws MyException;
    IExp deepcopy();
}

