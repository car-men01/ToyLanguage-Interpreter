package gui;

import controller.Controller;
import exceptions.KeyNotFoundException;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.util.Pair;
import model.adt.*;
import model.expressions.ArithExp;
import model.expressions.VarExp;
import model.state.PrgState;
import model.statements.*;
import model.types.IntType;
import repository.IRepo;
import repository.Repository;
import java.util.Arrays;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class GUIController{
    @FXML
    private TextField numPrgStates;
    @FXML
    private TableView<Pair<Integer, String>> heapTableView;
    @FXML
    private ListView<String> outListView;
    @FXML
    private ListView<String> fileTableListView;
    @FXML
    private ListView<Integer> prgStateListView;
    @FXML
    private TableView<Pair<String, String>> symTableView;
    @FXML
    private ListView<String> exeStackListView;
    @FXML
    private TableColumn<Pair<String, String>, String> symVarNameCol;
    @FXML
    private TableColumn<Pair<String, String>, String> symValueCol;
    @FXML
    private TableColumn<Pair<Integer, String>, String> heapAddressCol;
    @FXML
    private TableColumn<Pair<Integer, String>, String> heapValueCol;
    @FXML
    private TableView<Pair<String, String>> procTableView;
    @FXML
    private TableColumn<Pair<String, String>, String> procFunctionNameCol;
    @FXML
    private TableColumn<Pair<String, String>, String> procBodyCol;

    private Controller controller;

    private MyIProc<String, Pair<List<String>, IStmt>> procTable = new MyProc<String, Pair<List<String>, IStmt>>();

    public void initializeExecution(IStmt program) {

        IStmt f1 = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new AssignStmt("v", new ArithExp(new VarExp("a"), new VarExp("b"), 1)),
                        new PrintStmt(new VarExp("v"))
                )
        );
        IStmt f2 = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new AssignStmt("v", new ArithExp(new VarExp("a"), new VarExp("b"), 3)),
                        new PrintStmt(new VarExp("v"))
                )
        );

        procTable.insert("sum", new Pair<>(Arrays.asList("a", "b"), f1));
        procTable.insert("product", new Pair<>(Arrays.asList("a", "b"), f2));

        PrgState initialPrgState = new PrgState(
                new MyStack<>(), new MyDictionary<>(), new MyList<>(), program, new MyDictionary<>(), new MyHeap<>(), procTable
        );

        String logFilePath = "D:\\UBB\\github_projects\\2nd-Year-Semester-1\\MAP\\ToyLanguageGUI\\write.out";
        IRepo repository = new Repository(initialPrgState, logFilePath);
        controller = new Controller(repository);

        prgStateListView.getSelectionModel().selectedItemProperty().addListener((_, _, newValue) -> {
            if (newValue != null) {
                PrgState selectedPrgState = getCurrentPrgState();
                if (selectedPrgState != null) {
                    updateSymTable(selectedPrgState);
                    updateExeStack(selectedPrgState);
                }
            }
        });

        updateAllViews();
    }


    @FXML
    private void handleRunOneStep() {
        try {
            controller.executor = Executors.newFixedThreadPool(2);

            // Remove the completed programs
            List<PrgState> prgList = controller.removeCompletedPrg(controller.getRepo().getPrgList());

            if (prgList.isEmpty()) {
                showError("No more steps to execute.");
                prgStateListView.getItems().clear();
                exeStackListView.getItems().clear();
                controller.executor.shutdownNow();
                return;
            }

            // Garbage collection
            prgList.forEach(prg -> prg.getHeap().setContent(
                    controller.conservativeGarbageCollector(prgList)
            ));

            // Execute one step for all programs
            controller.oneStepForAllPrg(prgList);

            // Update the program list and views
            updateAllViews();
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }


    private void updateAllViews() {
        try {
            List<PrgState> prgStates = controller.getRepo().getPrgList();

            // Update number of program states
            numPrgStates.setText(String.valueOf(prgStates.size()));

            if (prgStates.isEmpty()) {
                // Clear all views if no program states exist
                exeStackListView.getItems().clear();
                prgStateListView.getItems().clear();

//                symTableView.getItems().clear();
//                heapTableView.getItems().clear();
//                outListView.getItems().clear();
//                fileTableListView.getItems().clear();
                return; // No need to update further
            }

            // Update program state identifiers
            ObservableList<Integer> prgStateIds = FXCollections.observableArrayList(
                    prgStates.stream().map(PrgState::getId).collect(Collectors.toList())
            );
            prgStateListView.setItems(prgStateIds);

            // Preserve the current selection
            Integer currentSelection = prgStateListView.getSelectionModel().getSelectedItem();
            if (currentSelection == null && !prgStateIds.isEmpty()) {
                prgStateListView.getSelectionModel().select(0);
            }

            PrgState selectedPrgState = getCurrentPrgState();
            if (selectedPrgState != null) {
                updateHeapTable(selectedPrgState);
                updateOutputList(selectedPrgState);
                updateFileTable(selectedPrgState);
                updateSymTable(selectedPrgState);
                updateExeStack(selectedPrgState);
                updateProcTable(selectedPrgState);
            }
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    private void updateProcTable(PrgState prgState) {
        //        ObservableList<Pair<String, String>> procedureTableItems = FXCollections.observableArrayList(
        //                procedureTable.getContent().entrySet().stream()
        //                        .map(entry -> {
        //                            String functionName = entry.getKey();
        //                            List<String> parameters = entry.getValue().getKey();
        //                            IStmt functionBody = entry.getValue().getValue();
        //
        //                            // Format: functionName(param1, param2, ...)
        //                            String signature = functionName + "(" + String.join(", ", parameters) + ")";
        //
        //                            // Convert function body to string representation
        //                            String body = functionBody.toString();
        //
        //                            return new Pair<>(signature, body);
        //                        })
        //                        .collect(Collectors.toList())
        //        );
        //
        //        // Bind Procedure Table columns
        //        SignatureCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getKey()));
        //        BodyCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getValue()));
        //
        //        ProcedureTableView.setItems(procedureTableItems);
        //
        // update th proc table view like in the above code but for it to work on my code

        ObservableList<Pair<String, String>> procTableItems = FXCollections.observableArrayList(
                prgState.getProcTable().getAddresses().stream()
                        .map(address -> {
                            Pair<List<String>, IStmt> proc = null;
                            try {
                                proc = procTable.getContent(address);
                            } catch (KeyNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                            String functionName = address;
                            List<String> parameters = proc.getKey();
                            IStmt functionBody = proc.getValue();

                            // Format: functionName(param1, param2, ...)
                            String signature = functionName + "(" + String.join(", ", parameters) + ")";

                            // Convert function body to string representation
                            String body = functionBody.toString();

                            return new Pair<>(signature, body);
                        })
                        .collect(Collectors.toList())
        );

        // Bind Procedure Table columns
        procFunctionNameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getKey()));
        procBodyCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getValue()));

        procTableView.setItems(procTableItems);
    }

    private void updateHeapTable(PrgState prgState) {
        ObservableList<Pair<Integer, String>> heapTableItems = FXCollections.observableArrayList(
                prgState.getHeap().getContent().entrySet().stream()
                        .map(entry -> new Pair<>(entry.getKey(), entry.getValue().toString()))
                        .collect(Collectors.toList())
        );
        // Bind Heap Table columns
        heapAddressCol.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getKey())));
        heapValueCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getValue()));
        heapTableView.setItems(heapTableItems);
    }

    private void updateSymTable(PrgState prgState) {
        ObservableList<Pair<String, String>> symTableItems = FXCollections.observableArrayList(
                prgState.getSymTable().getContent().entrySet().stream()
                        .map(entry -> new Pair<>(entry.getKey(), entry.getValue().toString()))
                        .collect(Collectors.toList())
        );
        // Bind Sym Table columns
        symVarNameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getKey()));
        symValueCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getValue()));

        symTableView.setItems(symTableItems);
    }

    private void updateOutputList(PrgState prgState) {
        ObservableList<String> outputItems = FXCollections.observableArrayList(
                prgState.getOut().getAll().stream().map(Object::toString).collect(Collectors.toList())
        );
        outListView.setItems(outputItems);
    }

    private void updateFileTable(PrgState prgState) {
        ObservableList<String> fileTableItems = FXCollections.observableArrayList(
                prgState.getFileTable().getContent().keySet().stream().map(Object::toString).collect(Collectors.toList())
        );
        fileTableListView.setItems(fileTableItems);
    }


    private void updateExeStack(PrgState prgState) {
        ObservableList<String> exeStackItems = FXCollections.observableArrayList(
                prgState.getStack().getStack().stream().map(Object::toString).collect(Collectors.toList())
        );
        exeStackListView.setItems(exeStackItems);
    }

    private PrgState getCurrentPrgState() {
        if (prgStateListView.getSelectionModel().isEmpty()) {
            return null;
        }
        int currentId = prgStateListView.getSelectionModel().getSelectedItem();
        return controller.getRepo().getPrgList().stream()
                .filter(prgState -> prgState.getId() == currentId)
                .findFirst()
                .orElse(null);
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.showAndWait();
    }
}
