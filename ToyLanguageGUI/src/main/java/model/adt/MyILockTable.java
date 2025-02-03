package model.adt;
import exceptions.KeyNotFoundException;

import java.util.HashMap;
import java.util.Set;

public interface MyILockTable<A, C> {
    C getContent(A address) throws KeyNotFoundException;
    Set<A> getAddresses();
    void insert(A address, C content);
    void update(A address, C content) throws KeyNotFoundException;
    void remove(A address) throws KeyNotFoundException;
    boolean contains(A address);
    int getNextFreeAddr();
    void setContent(HashMap<A, C> newLockTable);
    HashMap<A, C> getContent();
}
