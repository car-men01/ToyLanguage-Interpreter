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


        // FOR STATEMENT

        // example 3
        //Ref int a; new(a,20);
        //(for(v=0;v<3;v=v+1) fork(print(v);v=v*rh(a)));
        //print(rh(a))
        //The final Out should be {0,1,2,20}

        VarDeclStmt varDeclStmt6 = new VarDeclStmt("a", new RefType(new IntType()));
        NewHeapMemoryStmt memoryStmt3 = new NewHeapMemoryStmt("a", new ValueExp(new IntValue(20)));
        PrintStmt printStmt7 = new PrintStmt(new VarExp("v"));
        ReadHeapExp readHeapExp1 = new ReadHeapExp(new VarExp("a"));
        ArithExp arithExp1 = new ArithExp(new VarExp("v"), readHeapExp1, 3);
        AssignStmt assignStmt5 = new AssignStmt("v", arithExp1);
        ForkStmt forkStmt2 = new ForkStmt(new CompStmt(printStmt7, assignStmt5));
        ForStmt forStmt1 = new ForStmt("v", new ValueExp(new IntValue(0)), new ValueExp(new IntValue(3)), new ArithExp(new VarExp("v"), new ValueExp(new IntValue(1)), 1), forkStmt2);
        PrintStmt printStmt8 = new PrintStmt(new ReadHeapExp(new VarExp("a")));

        IStmt ex3 = new CompStmt(varDeclStmt6, new CompStmt(memoryStmt3, new CompStmt(forStmt1, printStmt8)));

        try {
            ex3.typecheck(new MyDictionary<>());
            System.out.println("Typecheck passed for example 3");
        } catch (Exception e) {
            System.out.println("Typecheck failed for example 3: " + e.getMessage());
        }
        programExamples.add(ex3);

        //LOCK UNLOCK STATEMENT

        // example 4
        //Ref int v1; Ref int v2; int x; int q;
        //new(v1,20);new(v2,30);newLock(x);
        //fork(
        //fork(
        //lock(x);wh(v1,rh(v1)-1);unlock(x)
        //);
        //lock(x);wh(v1,rh(v1)*10);unlock(x)
        //);newLock(q);
        //fork(
        //fork(lock(q);wh(v2,rh(v2)+5);unlock(q));
        //lock(q);wh(v2,rh(v2)*10);unlock(q)
        //
        //);
        //nop;nop;nop;nop;
        //lock(x); print(rh(v1)); unlock(x);
        //lock(q); print(rh(v2)); unlock(q);
        //The final Out should be {190 or 199,350 or 305}

        IStmt ex4 = new CompStmt(new VarDeclStmt("v1", new RefType(new IntType())),
                new CompStmt(new VarDeclStmt("v2", new RefType(new IntType())),
                        new CompStmt(new VarDeclStmt("x", new IntType()),
                                new CompStmt(new VarDeclStmt("q", new IntType()),
                                        new CompStmt(new NewHeapMemoryStmt("v1", new ValueExp(new IntValue(20))),
                                                new CompStmt(new NewHeapMemoryStmt("v2", new ValueExp(new IntValue(30))),
                                                        new CompStmt(new NewLockStmt("x"),
                                                                new CompStmt(new ForkStmt(
                                                                        new CompStmt(new ForkStmt(
                                                                                new CompStmt(new LockStmt("x"),
                                                                                        new CompStmt(new WriteHeapStmt("v1", new ArithExp( new ReadHeapExp(new VarExp("v1")), new ValueExp(new IntValue(1)), 2)),
                                                                                                new UnlockStmt("x")))
                                                                        ),
                                                                                new CompStmt(new LockStmt("x"),
                                                                                        new CompStmt(new WriteHeapStmt("v1", new ArithExp( new ReadHeapExp(new VarExp("v1")), new ValueExp(new IntValue(10)), 3)),
                                                                                                new UnlockStmt("x"))))
                                                                ),
                                                                        new CompStmt( new NewLockStmt("q"),
                                                                                new CompStmt(new ForkStmt(
                                                                                        new CompStmt( new ForkStmt(
                                                                                                new CompStmt(new LockStmt("q"),
                                                                                                        new CompStmt(new WriteHeapStmt("v2", new ArithExp( new ReadHeapExp(new VarExp("v2")), new ValueExp(new IntValue(5)), 1)),
                                                                                                                new UnlockStmt("q")))
                                                                                        ),
                                                                                                new CompStmt(new LockStmt("q"),
                                                                                                        new CompStmt(new WriteHeapStmt("v2", new ArithExp( new ReadHeapExp(new VarExp("v2")), new ValueExp(new IntValue(10)), 3)),
                                                                                                                new UnlockStmt("q"))))
                                                                                ),
                                                                                        new CompStmt(new NopStmt(),
                                                                                                new CompStmt(new NopStmt(),
                                                                                                        new CompStmt(new NopStmt(),
                                                                                                                new CompStmt(new NopStmt(),
                                                                                                                        new CompStmt(new LockStmt("x"),
                                                                                                                                new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v1"))),
                                                                                                                                        new CompStmt(new UnlockStmt("x"),
                                                                                                                                                new CompStmt(new LockStmt("q"),
                                                                                                                                                        new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v2"))),
                                                                                                                                                                new UnlockStmt("q"))))))))))))))))))));

        try {
            ex4.typecheck(new MyDictionary<>());
            System.out.println("Typecheck passed for example 4");
        } catch (Exception e) {
            System.out.println("Typecheck failed for example 4: " + e.getMessage());
        }

        programExamples.add(ex4);

        // CONDITIONAL STATEMENT
        // example 5
        //Ref int a; Ref int b; int v;
        //new(a,0); new(b,0);
        //wh(a,1); wh(b,2);
        //v=(rh(a)<rh(b))?100:200;
        //print(v);
        //v= ((rh(b)-2)>rh(a))?100:200;
        //print(v);
        //The final Out should be {100,200}

        VarDeclStmt varDeclStmt5 = new VarDeclStmt("a", new RefType(new IntType()));
        VarDeclStmt varDeclStmt7 = new VarDeclStmt("b", new RefType(new IntType()));
        VarDeclStmt varDeclStmt8 = new VarDeclStmt("v", new IntType());
        NewHeapMemoryStmt memoryStmt4 = new NewHeapMemoryStmt("a", new ValueExp(new IntValue(0)));
        NewHeapMemoryStmt memoryStmt5 = new NewHeapMemoryStmt("b", new ValueExp(new IntValue(0)));
        WriteHeapStmt writeHeapStmt2 = new WriteHeapStmt("a", new ValueExp(new IntValue(1)));
        WriteHeapStmt writeHeapStmt3 = new WriteHeapStmt("b", new ValueExp(new IntValue(2)));
        ReadHeapExp readHeapExp2 = new ReadHeapExp(new VarExp("a"));
        ReadHeapExp readHeapExp3 = new ReadHeapExp(new VarExp("b"));
        RelationalExp relationalExp1 = new RelationalExp(readHeapExp2, readHeapExp3, "<");
        CondAssignStmt conditionalAssignStmt1 = new CondAssignStmt("v", relationalExp1, new ValueExp(new IntValue(100)), new ValueExp(new IntValue(200)));
        PrintStmt printStmt9 = new PrintStmt(new VarExp("v"));
        ArithExp arithExp2 = new ArithExp(new ArithExp(readHeapExp3, new ValueExp(new IntValue(2)), 2), readHeapExp2, 1);
        RelationalExp relationalExp2 = new RelationalExp(arithExp2, readHeapExp2, ">");
        CondAssignStmt conditionalAssignStmt2 = new CondAssignStmt("v", relationalExp2, new ValueExp(new IntValue(100)), new ValueExp(new IntValue(200)));
        PrintStmt printStmt10 = new PrintStmt(new VarExp("v"));

        IStmt ex5 = new CompStmt(varDeclStmt5, new CompStmt(varDeclStmt7, new CompStmt(varDeclStmt8, new CompStmt(memoryStmt4, new CompStmt(memoryStmt5, new CompStmt(writeHeapStmt2, new CompStmt(writeHeapStmt3, new CompStmt(conditionalAssignStmt1, new CompStmt(printStmt9, new CompStmt(conditionalAssignStmt2, printStmt10))))))))));

        try {
            ex5.typecheck(new MyDictionary<>());
            System.out.println("Typecheck passed for example 5");
        } catch (Exception e) {
            System.out.println("Typecheck failed for example 5: " + e.getMessage());
        }

        programExamples.add(ex5);


        //SWITCH STATEMENT
        // example 6
        //int a; int b; int c;
        //a=1;b=2;c=5;
        //(switch(a*10)
        //(case (b*c) : print(a);print(b))
        //(case (10) : print(100);print(200))
        //(default : print(300)));
        //print(300)
        //The final Out should be {1,2,300}

        VarDeclStmt varDeclStmt9 = new VarDeclStmt("a", new IntType());
        VarDeclStmt varDeclStmt10 = new VarDeclStmt("b", new IntType());
        VarDeclStmt varDeclStmt11 = new VarDeclStmt("c", new IntType());
        AssignStmt assignStmt6 = new AssignStmt("a", new ValueExp(new IntValue(1)));
        AssignStmt assignStmt7 = new AssignStmt("b", new ValueExp(new IntValue(2)));
        AssignStmt assignStmt8 = new AssignStmt("c", new ValueExp(new IntValue(5)));
        ArithExp arithExp3 = new ArithExp(new VarExp("a"), new ValueExp(new IntValue(10)), 3);
        ArithExp arithExp4 = new ArithExp(new VarExp("b"), new VarExp("c"), 3);
        PrintStmt printStmt11 = new PrintStmt(new VarExp("a"));
        PrintStmt printStmt12 = new PrintStmt(new VarExp("b"));
        PrintStmt printStmt13 = new PrintStmt(new ValueExp(new IntValue(100)));
        PrintStmt printStmt14 = new PrintStmt(new ValueExp(new IntValue(200)));
        PrintStmt printStmt15 = new PrintStmt(new ValueExp(new IntValue(300)));
        SwitchStmt switchStmt1 = new SwitchStmt(arithExp3, arithExp4, new CompStmt(printStmt11, printStmt12), new ValueExp(new IntValue(10)), new CompStmt(printStmt13, printStmt14), printStmt15);

        IStmt ex6 = new CompStmt(varDeclStmt9, new CompStmt(varDeclStmt10, new CompStmt(varDeclStmt11, new CompStmt(assignStmt6, new CompStmt(assignStmt7, new CompStmt(assignStmt8, new CompStmt(switchStmt1, printStmt15)))))));

        try {
            ex6.typecheck(new MyDictionary<>());
            System.out.println("Typecheck passed for example 6");
        } catch (Exception e) {
            System.out.println("Typecheck failed for example 6: " + e.getMessage());
        }

        programExamples.add(ex6);

        //SLEEP STATEMENT
        // example 7
        //v=0;
        //(while(v<3) (fork(print(v);v=v+1);v=v+1);
        //sleep(5);
        //print(v*10)
        //The final Out should be {0,1,2,30}

        VarDeclStmt varDeclStmt12 = new VarDeclStmt("v", new IntType());
        AssignStmt assignStmt9 = new AssignStmt("v", new ValueExp(new IntValue(0)));
        PrintStmt printStmt16 = new PrintStmt(new VarExp("v"));
        ArithExp arithExp5 = new ArithExp(new VarExp("v"), new ValueExp(new IntValue(1)), 1);
        AssignStmt assignStmt10 = new AssignStmt("v", arithExp5);
        ForkStmt forkStmt3 = new ForkStmt(new CompStmt(printStmt16, assignStmt10));
        ArithExp arithExp6 = new ArithExp(new VarExp("v"), new ValueExp(new IntValue(1)), 1);
        AssignStmt assignStmt11 = new AssignStmt("v", arithExp6);
        WhileStmt whileStmt1 = new WhileStmt(new RelationalExp(new VarExp("v"), new ValueExp(new IntValue(3)), "<"), new CompStmt(forkStmt3, assignStmt11));
        SleepStmt sleepStmt1 = new SleepStmt(5);
        ArithExp arithExp7 = new ArithExp(new VarExp("v"), new ValueExp(new IntValue(10)), 3);
        PrintStmt printStmt17 = new PrintStmt(arithExp7);

        IStmt ex7 = new CompStmt(varDeclStmt12, new CompStmt(assignStmt9, new CompStmt(whileStmt1, new CompStmt(sleepStmt1, printStmt17))));

        try {
            ex7.typecheck(new MyDictionary<>());
            System.out.println("Typecheck passed for example 7");
        } catch (Exception e) {
            System.out.println("Typecheck failed for example 7: " + e.getMessage());
        }

        programExamples.add(ex7);

        // REPEAT UNTIL STATEMENT
        // example 8
        //v=0;
        //(repeat (fork(print(v);v=v-1);v=v+1) until v==3);
        //x=1;y=2;z=3;w=4;
        //print(v*10)
        //The final Out should be {0,1,2,30}

        VarDeclStmt varDeclStmt13 = new VarDeclStmt("v", new IntType());
        AssignStmt assignStmt12 = new AssignStmt("v", new ValueExp(new IntValue(0)));
        PrintStmt printStmt18 = new PrintStmt(new VarExp("v"));
        ArithExp arithExp8 = new ArithExp(new VarExp("v"), new ValueExp(new IntValue(1)), 2);
        AssignStmt assignStmt13 = new AssignStmt("v", arithExp8);
        ForkStmt forkStmt4 = new ForkStmt(new CompStmt(printStmt18, assignStmt13));
        ArithExp arithExp9 = new ArithExp(new VarExp("v"), new ValueExp(new IntValue(1)), 1);
        AssignStmt assignStmt14 = new AssignStmt("v", arithExp9);
        RepeatUntilStmt repeatUntilStmt1 = new RepeatUntilStmt(new CompStmt(forkStmt4, assignStmt14), new RelationalExp(new VarExp("v"), new ValueExp(new IntValue(3)), "=="));
        VarDeclStmt varDeclStmt14 = new VarDeclStmt("x", new IntType());
        VarDeclStmt varDeclStmt15 = new VarDeclStmt("y", new IntType());
        VarDeclStmt varDeclStmt16 = new VarDeclStmt("z", new IntType());
        VarDeclStmt varDeclStmt17 = new VarDeclStmt("w", new IntType());
        AssignStmt assignStmt15 = new AssignStmt("x", new ValueExp(new IntValue(1)));
        AssignStmt assignStmt16 = new AssignStmt("y", new ValueExp(new IntValue(2)));
        AssignStmt assignStmt17 = new AssignStmt("z", new ValueExp(new IntValue(3)));
        AssignStmt assignStmt18 = new AssignStmt("w", new ValueExp(new IntValue(4)));
        ArithExp arithExp10 = new ArithExp(new VarExp("v"), new ValueExp(new IntValue(10)), 3);
        PrintStmt printStmt19 = new PrintStmt(arithExp10);

        IStmt ex8 = new CompStmt(varDeclStmt13, new CompStmt(assignStmt12, new CompStmt(repeatUntilStmt1, new CompStmt(varDeclStmt14, new CompStmt(varDeclStmt15, new CompStmt(varDeclStmt16, new CompStmt(varDeclStmt17, new CompStmt(assignStmt15, new CompStmt(assignStmt16, new CompStmt(assignStmt17, new CompStmt(assignStmt18, printStmt19)))))))))));

        try {
            ex8.typecheck(new MyDictionary<>());
            System.out.println("Typecheck passed for example 8");
        } catch (Exception e) {
            System.out.println("Typecheck failed for example 8: " + e.getMessage());
        }

        programExamples.add(ex8);

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
