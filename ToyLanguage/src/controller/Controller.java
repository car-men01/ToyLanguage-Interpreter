package controller;
import model.adt.MyIStack;
import exceptions.MyException;
import model.state.PrgState;
import model.statements.IStmt;
import model.values.IValue;
import model.values.RefValue;
import repository.IRepo;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Controller {
    private IRepo repo;

    public Controller(IRepo repo) {
        this.repo = repo;
    }

    Map<Integer, IValue> garbageCollector(List<Integer> symTableAddr, Map<Integer,IValue> heap){
        List <Integer> heapAddr = getAddrFromHeap(heap, symTableAddr);
        return heap.entrySet().stream()
                .filter(e->heapAddr.contains(e.getKey()))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
    List<Integer> getAddrFromSymTable(Collection<IValue> symTableValues){
        // get the addresses from the SymTable
        return symTableValues.stream()
                .filter(v-> v instanceof RefValue)
                .map(v-> {
                    RefValue v1 = (RefValue)v;
                    return v1.getHeapAddr();
                })
                .collect(Collectors.toList());
    }
    List<Integer> getAddrFromHeap(Map<Integer, IValue> heap, List<Integer> symTableAddr) {
        // get the addresses from the heap that are not referenced by the SymTable
        List<Integer> addresses = symTableAddr;
        boolean added = true;
        while (added) {
            added = false;
            // collect new addresses referenced by RefValues in the current heap
            List<Integer> newAddresses = heap.entrySet().stream()
                    .filter(e -> addresses.contains(e.getKey())) // only check reachable addresses
                    .map(Map.Entry::getValue)
                    .filter(v -> v instanceof RefValue)
                    .map(v -> ((RefValue) v).getHeapAddr())
                    .filter(addr -> !addresses.contains(addr)) // avoid duplicates in the list
                    .toList();
            // add the new addresses to the list of addresses
            if (!newAddresses.isEmpty()) {
                addresses.addAll(newAddresses);
                // repeat the process if new addresses were added
                added = true;
            }
        }

        return addresses;
    }

    public PrgState oneStep(PrgState state) throws MyException {
        MyIStack<IStmt> stack = state.getStack();
        if (stack.isEmpty()) {
            throw new MyException("Stack is empty");
        }
        IStmt currentStmt = stack.pop();
        return currentStmt.execute(state);
    }

    public void allStep() throws MyException {
        PrgState prg = repo.getCrtPrg(); // repo is the controller field of type MyIRepo

        this.displayPrgState();
        repo.logPrgStateExec();
        while (!prg.getStack().isEmpty()) {
            oneStep(prg);
            this.displayPrgState();
            repo.logPrgStateExec();
            // Garbage Collector
            prg.getHeap().setContent(garbageCollector(
                    getAddrFromSymTable(prg.getSymTable().getContent().values()), // get addresses from SymTable
                    prg.getHeap().getContent()) // get the heap
            );
            //this.displayPrgState();
            repo.logPrgStateExec();
        }
    }

    public void displayPrgState() {
        System.out.println(repo.getCrtPrg());
    }
}
