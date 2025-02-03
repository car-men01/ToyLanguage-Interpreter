package model.statements;

import exceptions.MyException;
import model.adt.MyIDictionary;
import model.state.PrgState;
import model.types.IType;

public class SleepStmt implements IStmt{
    //sleep(number)
    //Its execution on the ExeStack is the following:
    //- pop the statement
    //- if number== 0 then do nothing
    //else push sleep(number-1) on the stack

    private int number;

    public SleepStmt(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "sleep(" + number + ")";
    }

    @Override
    public PrgState execute(PrgState state) {
        if (number == 0)
            return null;
        else {
            IStmt newStmt = new SleepStmt(number - 1);
            state.getStack().push(newStmt);
            return null;
        }
    }

    @Override
    public IStmt deepcopy() {
        return new SleepStmt(number);
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        return typeEnv;
    }
}
