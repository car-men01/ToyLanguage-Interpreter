package model.statements;

import exceptions.MyException;
import model.adt.MyIDictionary;
import model.adt.MyILockTable;
import model.adt.MyIStack;
import model.state.PrgState;
import model.types.IType;
import model.types.IntType;
import model.values.IValue;
import model.values.IntValue;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockStmt implements IStmt {
    private String var;
    private static Lock lock = new ReentrantLock();

    public LockStmt(String var) {
        this.var = var;
    }


    @Override
    public PrgState execute(PrgState state) throws MyException {
        lock.lock();
        MyIDictionary<String, IValue> symTable = state.getSymTable();
        MyILockTable<Integer, Integer> lockTable = state.getLockTable();
        MyIStack<IStmt> stack = state.getStack();

        if (symTable.contains(var) && symTable.lookup(var).getType().equals(new IntType())) {
            IntValue foundIndexS = ((IntValue) symTable.lookup(var));
            int foundIndex = foundIndexS.getVal();

            if (lockTable.contains(foundIndex)) {
                if (lockTable.getContent(foundIndex) == -1) {
                    lockTable.update(foundIndex, state.getId());
                } else {
                    stack.push(this);
                }
            } else {
                throw new MyException("Index " + foundIndex + " does not exist in the lock table");
            }
        } else {
            throw new MyException("Variable " + var + " is not of type int or does not exist");
        }

        lock.unlock();
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType type = typeEnv.lookup(var);
        if (type.equals(new IntType())) {
            return typeEnv;
        } else {
            throw new MyException("Variable " + var + " is not of type int");
        }
    }

    @Override
    public IStmt deepcopy() {
        return new LockStmt(var);
    }

    @Override
    public String toString() {
        return "lock(" + var + ")";
    }


}
