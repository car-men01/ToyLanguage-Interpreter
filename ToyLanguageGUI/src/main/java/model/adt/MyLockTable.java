package model.adt;

import exceptions.KeyNotFoundException;

import java.util.HashMap;
import java.util.Set;

public class MyLockTable<A, C> implements MyILockTable<A, C>{
    private HashMap<A, C> lockTable;
    private int nextFreeAddr = 0;
    //private int nextFreeAddr;

    public MyLockTable() {
        this.lockTable = new HashMap<A, C>();
        //this.nextFreeAddr = 1;
    }
    @Override
    public int getNextFreeAddr() {
        synchronized (this) {
            nextFreeAddr++;
            return nextFreeAddr;
        }
    }

    @Override
    public C getContent(A address) throws KeyNotFoundException {
        synchronized (this) {
            if (!this.lockTable.containsKey(address)) {
                throw new KeyNotFoundException("Address not found in lock table");
            }
            return this.lockTable.get(address);
        }
    }
    @Override
    public void setContent(HashMap<A, C> newLockTable) {
        this.lockTable = newLockTable;
    }
    @Override
    public Set<A> getAddresses() {
        return this.lockTable.keySet();
    }
    @Override
    public HashMap<A, C> getContent() {
        return this.lockTable;
    }
    @Override
    public void insert(A address, C content) {
        this.lockTable.put(address, content);
    }
    @Override
    public void update(A address, C content) throws KeyNotFoundException {
        synchronized (this) {
            if (!this.lockTable.containsKey(address)) {
                throw new KeyNotFoundException("Address not found in lock table");
            }
            else
                this.lockTable.put(address, content);
            // this.lockTable.replace(address, content);
        }
    }
    @Override
    public void remove(A address) throws KeyNotFoundException {
        if (!this.lockTable.containsKey(address)) {
            throw new KeyNotFoundException("Address not found in lock table");
        }
        this.lockTable.remove(address);
    }
    @Override
    public boolean contains(A address) {
        return this.lockTable.containsKey(address);
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        for (A key : this.lockTable.keySet()) {
            s.append(key).append(" -> ").append(this.lockTable.get(key)).append("\n");
        }
        return s.toString();
    }
}
