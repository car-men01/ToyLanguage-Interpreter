package model.statements;

import exceptions.MyException;
import model.adt.MyIDictionary;
import model.adt.MyILockTable;
import model.adt.MyLockTable;
import model.state.PrgState;
import model.types.IType;
import model.types.IntType;
import model.values.IValue;
import model.values.IntValue;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NewLockStmt implements IStmt {
    private String var;
    private static final Lock lock = new ReentrantLock();

    public NewLockStmt(String var) {
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        lock.lock();
        MyILockTable<Integer, Integer> lockTable = state.getLockTable();
        MyIDictionary<String, IValue> symTable = state.getSymTable();
        int addr = lockTable.getNextFreeAddr();
        lockTable.insert(addr, -1);
        if (symTable.contains(var) && symTable.lookup(var).getType().equals(new IntType())) {
            symTable.update(var, new IntValue(addr));
        } else {
            throw new MyException("Variable " + var + " is not of type int or does not exist");
        }
        lock.unlock();
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        if (typeEnv.lookup(var).equals(new IntType())) {
            return typeEnv;
        } else {
            throw new MyException("Variable " + var + " is not of type int");
        }
    }

    @Override
    public IStmt deepcopy() {
        return new NewLockStmt(var);
    }

    @Override
    public String toString() {
        return "newLock(" + var + ")";
    }
}
