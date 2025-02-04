package model.adt;

import exceptions.KeyNotFoundException;
import javafx.util.Pair;
import model.statements.IStmt;

import java.util.List;
import java.util.Map;
import java.util.Set;

//<String, Pair<List<String>, IStmt>>

public interface MyIProc<A, C> {
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
