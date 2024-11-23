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

    //TODO: implement the garbage Collector

    Map<Integer, IValue> garbageCollector(List<Integer> symTableAddr, Map<Integer,IValue> heap){
        List <Integer> heapAddr = getAddrFromHeap(heap, symTableAddr);
        return heap.entrySet().stream()
                .filter(e->heapAddr.contains(e.getKey()))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
    List<Integer> getAddrFromSymTable(Collection<IValue> symTableValues){
        return symTableValues.stream()
                .filter(v-> v instanceof RefValue)
                .map(v-> {
                    RefValue v1 = (RefValue)v;
                    return v1.getHeapAddr();
                })
                .collect(Collectors.toList());
    }
    // Recursively collects addresses referenced in the heap
    List<Integer> getAddrFromHeap(Map<Integer, IValue> heap, List<Integer> symTableAddr) {
        List<Integer> addresses = symTableAddr;
        boolean added;

        do {
            added = false;
            // Collect new addresses referenced by RefValues in the current heap
            List<Integer> newAddresses = heap.entrySet().stream()
                    .filter(e -> addresses.contains(e.getKey())) // Only check reachable addresses
                    .map(Map.Entry::getValue)
                    .filter(v -> v instanceof RefValue)
                    .map(v -> ((RefValue) v).getHeapAddr())
                    .filter(addr -> !addresses.contains(addr)) // Avoid duplicates
                    .collect(Collectors.toList());

            if (!newAddresses.isEmpty()) {
                addresses.addAll(newAddresses);
                added = true; // Continue if new addresses are added
            }
        } while (added);

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
            prg.getHeap().setContent(
                    garbageCollector(
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
