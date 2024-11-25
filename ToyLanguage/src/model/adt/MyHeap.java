package model.adt;

import exceptions.KeyNotFoundException;
import exceptions.MyException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyHeap<A, C> implements MyIHeap<A, C>{
    private Map<A, C> heap;
    private int nextFreeAddr;

    public MyHeap() {
        this.heap = new HashMap<A, C>();
        this.nextFreeAddr = 1;
    }
    public int getNextFreeAddr() {
        return this.nextFreeAddr;
    }

    @Override
    public C getContent(A address) throws KeyNotFoundException {
        if (!this.heap.containsKey(address)) {
            throw new KeyNotFoundException("Address not found in heap");
        }
        return this.heap.get(address);
    }
    @Override
    public void setContent(Map<A, C> newHeap) {
        this.heap = newHeap;
    }
    @Override
    public Set<A> getAddresses() {
        return this.heap.keySet();
    }
    @Override
    public Map<A, C> getContent() {
        return this.heap;
    }
    @Override
    public void insert(A address, C content) {
        this.heap.put(address, content);
        this.nextFreeAddr++;
    }
    @Override
    public void update(A address, C content) {
        this.heap.put(address, content);
    }
    @Override
    public void remove(A address) throws KeyNotFoundException {
        if (!this.heap.containsKey(address)) {
            throw new KeyNotFoundException("Address not found in heap");
        }
        this.heap.remove(address);
    }
    @Override
    public boolean contains(A address) {
        return this.heap.containsKey(address);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (A key : this.heap.keySet()) {
            s.append(key).append(" -> ").append(this.heap.get(key)).append("\n");
        }
        return s.toString();
    }
}
