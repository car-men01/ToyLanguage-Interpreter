package model.expressions;

import model.adt.MyIDictionary;
import exceptions.MyException;
import model.adt.MyIHeap;
import model.values.IValue;

public interface IExp {
    IValue eval(MyIDictionary<String,IValue> tbl, MyIHeap<Integer,IValue> heap) throws MyException;
    IExp deepcopy();
}

