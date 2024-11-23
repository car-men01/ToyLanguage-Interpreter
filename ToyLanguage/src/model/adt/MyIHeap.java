package model.adt;

import exceptions.KeyNotFoundException;
import exceptions.MyException;

import java.util.Map;
import java.util.Set;

public interface MyIHeap<A, C> {
    C getContent(A address) throws KeyNotFoundException;
    Set<A> getAddresses();
    void insert(A address, C content);
    void update(A address, C content);
    void remove(A address) throws KeyNotFoundException;
    boolean contains(A address);
    int getNextFreeAddr();
    void setContent(Map<A, C> newHeap);
    Map<A, C> getContent();
}
