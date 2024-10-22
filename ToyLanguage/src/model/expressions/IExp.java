package model.expressions;

import model.adt.MyIDictionary;
import model.exceptions.MyException;
import model.values.IValue;

public interface IExp {
    IValue eval(MyIDictionary<String,IValue> tbl) throws MyException;
}

