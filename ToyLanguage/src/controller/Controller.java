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
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {
    private IRepo repo;
    ExecutorService executor;

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

    Map<Integer, IValue> conservativeGarbageCollector(List<PrgState> prgStates) {
        // Collect all addresses from the symbol tables of all program states
        List<Integer> symTableAddresses = prgStates.stream()
                .flatMap(prg -> getAddrFromSymTable(prg.getSymTable().getContent().values()).stream())
                .collect(Collectors.toList());

        // Get a reference to the shared heap
        Map<Integer, IValue> heap = prgStates.getFirst().getHeap().getContent();

        // Collect reachable addresses from the heap
        List<Integer> reachableHeapAddresses = getAddrFromHeap(heap, symTableAddresses);

        // Filter the heap to only include reachable addresses
        return heap.entrySet().stream()
                .filter(entry -> reachableHeapAddresses.contains(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }


    List<PrgState> removeCompletedPrg(List<PrgState> inPrgList){
        return inPrgList.stream()
                .filter(PrgState::isNotCompleted)
                .collect(Collectors.toList());
    }

    void oneStepForAllPrg(List<PrgState> prgList) throws InterruptedException {
        //before the execution, print the PrgState List into the log file
        prgList.forEach(prg -> {
            try {
                repo.logPrgStateExec(prg);
                displayPrgState();

            } catch (MyException e) {
                throw new RuntimeException(e);
            }
        });
        //RUN concurrently one step for each of the existing PrgStates
        //-----------------------------------------------------------------------
        //prepare the list of callables
        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>)(p::oneStep))
                .collect(Collectors.toList());

        //start the execution of the callables
        //it returns the list of new created PrgStates (namely threads)
        List<PrgState> newPrgList = executor.invokeAll(callList).stream()
                .map(future -> {
                    try {
                        return future.get();
                    }
                    catch(InterruptedException | ExecutionException e) {
                        //here you can treat the possible exceptions thrown by statements execution
                        System.out.println(e.getMessage());
                        return null;
                    }
                })
                .filter(p -> p != null)
                .collect(Collectors.toList());

        //add the new created threads to the list of existing threads
        prgList.addAll(newPrgList);
        //------------------------------------------------------------------------------

        //after the execution, print the PrgState List into the log file
        prgList.forEach(prg -> {
            try {
                repo.logPrgStateExec(prg);
                displayPrgState();
            } catch (MyException e) {
                throw new RuntimeException(e);
            }
        });

        //Save the current programs in the repository
        repo.setPrgList(prgList);
    }

    public void allStep() throws MyException, InterruptedException {
        executor = Executors.newFixedThreadPool(2);

        // remove the completed programs
        List<PrgState> prgList = removeCompletedPrg(repo.getPrgList());
        while (!prgList.isEmpty()) {
            //HERE you can call conservativeGarbageCollector

            List<PrgState> finalPrgList = prgList; // copy the list of program states for the garbage collector
            prgList.forEach(prg -> prg.getHeap().setContent(conservativeGarbageCollector(finalPrgList)));

            oneStepForAllPrg(prgList);
            // remove the completed programs
            prgList = removeCompletedPrg(repo.getPrgList());
        }
        executor.shutdownNow();
        //HERE the repository still contains at least one Completed Prg
        // and its List<PrgState> is not empty. Note that oneStepForAllPrg calls the method
        //setPrgList of repository in order to change the repository

        // update the repository state
        repo.setPrgList(prgList);
    }

    public void displayPrgState() {
        List<PrgState> prgList = repo.getPrgList(); // Get all program states from the repository

        if (prgList.isEmpty()) {
            System.out.println("No active program states to display.");
        } else {
            prgList.forEach(System.out::println);
        }
    }
}
