package gui;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.state.PrgState;
import model.statements.IStmt;
import model.statements.VarDeclStmt;
import repository.Repository;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Scanner;

import model.adt.*;
import model.state.PrgState;
import model.values.*;
import model.statements.*;
import model.expressions.*;
import model.types.*;
import repository.*;
import controller.*;
import view.*;

import java.util.List;
public class ProgramSelectionController {
    @FXML
    private ListView<String> programListView;
    @FXML
    private Button selectProgramButton;

    private List<IStmt> programs; // List of IStmt instances

    public void initialize() {
        programs = loadPrograms();
        programListView.setItems(FXCollections.observableArrayList(
                programs.stream().map(IStmt::toString).toList()
        ));
    }

    // Write the program examples
    private List<IStmt> loadPrograms() {
        List<IStmt> programExamples = new ArrayList<>();

        // example 1
        // int v; Ref int a; v=10;new(a,22);
        // fork(wH(a,30);v=32;print(v);print(rH(a)));
        // print(v);print(rH(a))
        VarDeclStmt varDeclStmt1 = new VarDeclStmt("v", new IntType());
        VarDeclStmt varDeclStmt2 = new VarDeclStmt("a", new RefType(new IntType()));
        AssignStmt assignStmt1 = new AssignStmt("v", new ValueExp(new IntValue(10)));
        NewHeapMemoryStmt memoryStmt1 = new NewHeapMemoryStmt("a", new ValueExp(new IntValue(22)));
        WriteHeapStmt writeHeapStmt1 = new WriteHeapStmt("a", new ValueExp(new IntValue(30)));
        AssignStmt assignStmt2 = new AssignStmt("v", new ValueExp(new IntValue(32)));
        PrintStmt printStmt1 = new PrintStmt(new VarExp("v"));
        PrintStmt printStmt2 = new PrintStmt(new ReadHeapExp(new VarExp("a")));
        //ForkStmt forkStmt1 = new ForkStmt(new CompStmt(writeHeapStmt1, new CompStmt(assignStmt2, new CompStmt(printStmt1, printStmt2))));
        VarDeclStmt varDeclStmt222 = new VarDeclStmt("x", new IntType());
        ForkStmt forkStmt1 = new ForkStmt(new CompStmt(varDeclStmt222, new CompStmt(writeHeapStmt1, new CompStmt(assignStmt2, new CompStmt(printStmt1, printStmt2)))));

        PrintStmt printStmt3 = new PrintStmt(new VarExp("v"));
        PrintStmt printStmt4 = new PrintStmt(new ReadHeapExp(new VarExp("a")));

        IStmt ex1 = new CompStmt(varDeclStmt1, new CompStmt(varDeclStmt2, new CompStmt(assignStmt1, new CompStmt(memoryStmt1, new CompStmt(forkStmt1, new CompStmt(printStmt3, printStmt4))))));

        programExamples.add(ex1);

        // example 2
        // int v; Ref int a; v=10;new(a,22);print(v);print(rH(a));a=0
        //write this program
        VarDeclStmt varDeclStmt3 = new VarDeclStmt("v", new IntType());
        VarDeclStmt varDeclStmt4 = new VarDeclStmt("a", new RefType(new IntType()));
        AssignStmt assignStmt3 = new AssignStmt("v", new ValueExp(new IntValue(10)));
        NewHeapMemoryStmt memoryStmt2 = new NewHeapMemoryStmt("a", new ValueExp(new IntValue(22)));
        PrintStmt printStmt5 = new PrintStmt(new VarExp("v"));
        PrintStmt printStmt6 = new PrintStmt(new ReadHeapExp(new VarExp("a")));
        AssignStmt assignStmt4 = new AssignStmt("a", new ValueExp(new IntValue(0)));

        IStmt ex2 = new CompStmt(varDeclStmt3, new CompStmt(varDeclStmt4, new CompStmt(assignStmt3, new CompStmt(memoryStmt2, new CompStmt(printStmt5, new CompStmt(printStmt6, assignStmt4))))));

        programExamples.add(ex2);

        return programExamples;
    }

    @FXML
    private void handleSelectProgram() {
        int selectedIndex = programListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            IStmt selectedProgram = programs.get(selectedIndex);
            Stage currentStage = (Stage) selectProgramButton.getScene().getWindow();
            currentStage.close();

            MainExecutionWindow.launch(selectedProgram);
        }
    }
}
