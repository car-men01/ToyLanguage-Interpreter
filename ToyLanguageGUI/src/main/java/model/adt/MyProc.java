package model.adt;

import exceptions.KeyNotFoundException;
import javafx.util.Pair;
import model.statements.IStmt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MyProc<A, C> implements MyIProc<A, C>{
    private Map<A, C> procTable;
    private int nextFreeAddr;

    public MyProc() {
        this.procTable = new HashMap<A, C>();
        this.nextFreeAddr = 1;
    }
    public int getNextFreeAddr() {
        return this.nextFreeAddr;
    }

    @Override
    public C getContent(A address) throws KeyNotFoundException {
        if (!this.procTable.containsKey(address)) {
            throw new KeyNotFoundException("Address not found in heap");
        }
        return this.procTable.get(address);
    }
    @Override
    public void setContent(Map<A, C> newHeap) {
        this.procTable = newHeap;
    }
    @Override
    public Set<A> getAddresses() {
        return this.procTable.keySet();
    }
    @Override
    public Map<A, C> getContent() {
        return this.procTable;
    }
    @Override
    public void insert(A address, C content) {
        this.procTable.put(address, content);
        this.nextFreeAddr++;
    }
    @Override
    public void update(A address, C content) {
        this.procTable.put(address, content);
    }
    @Override
    public void remove(A address) throws KeyNotFoundException {
        if (!this.procTable.containsKey(address)) {
            throw new KeyNotFoundException("Address not found in heap");
        }
        this.procTable.remove(address);
    }
    @Override
    public boolean contains(A address) {
        return this.procTable.containsKey(address);
    }

}
