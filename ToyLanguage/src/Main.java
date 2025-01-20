import java.io.BufferedReader;
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

public class Main {
    public static void main(String[] args) {

        // D:\UBB\github_projects\2nd-Year-Semester-1\MAP\ToyLanguage\write.out
        // read the log file path from the keyboard
        // Scanner scanner = new Scanner(System.in);
        // System.out.print("Enter the log file path: ");
        // String logFilePath = scanner.nextLine();

        String logFilePath = "D:\\UBB\\github_projects\\2nd-Year-Semester-1\\MAP\\ToyLanguage\\write.out";

        /*
        // A4

        // example 1 - test new heap memory
        // Ref int v; new(v,20); Ref Ref int a; new(a,v); print(v); print(a);

        VarDeclStmt varDeclStmt1 = new VarDeclStmt("v", new RefType(new IntType()));
        NewHeapMemoryStmt memoryStmt1 = new NewHeapMemoryStmt("v", new ValueExp(new IntValue(20)));
        VarDeclStmt varDeclStmt2 = new VarDeclStmt("a", new RefType(new RefType(new IntType())));
        NewHeapMemoryStmt memoryStmt2 = new NewHeapMemoryStmt("a", new VarExp("v"));
        PrintStmt printStmt1 = new PrintStmt(new VarExp("v"));
        PrintStmt printStmt2 = new PrintStmt(new VarExp("a"));

        IStmt ex1 = new CompStmt(varDeclStmt1, new CompStmt(memoryStmt1, new CompStmt(varDeclStmt2, new CompStmt(memoryStmt2, new CompStmt(printStmt1, printStmt2)))));

        PrgState prgState1 = new PrgState(new MyStack<IStmt>(), new MyDictionary<String, IValue>(), new MyList<IValue>(), ex1, new MyDictionary<StringValue, BufferedReader>(), new MyHeap<Integer, IValue>());
        IRepo repository1 = new Repository(prgState1, logFilePath);
        Controller controller1 = new Controller(repository1);

        // example 2 - test read heap
        // Ref int v; new(v,20); Ref Ref int a; new(a,v); print(rH(v)); print(rH(rH(a)) + 5);

        VarDeclStmt varDeclStmt3 = new VarDeclStmt("v", new RefType(new IntType()));
        NewHeapMemoryStmt memoryStmt3 = new NewHeapMemoryStmt("v", new ValueExp(new IntValue(20)));
        VarDeclStmt varDeclStmt4 = new VarDeclStmt("a", new RefType(new RefType(new IntType())));
        NewHeapMemoryStmt memoryStmt4 = new NewHeapMemoryStmt("a", new VarExp("v"));
        PrintStmt printStmt3 = new PrintStmt(new ReadHeapExp(new VarExp("v")));
        PrintStmt printStmt4 = new PrintStmt(
                new ArithExp(new ReadHeapExp(new ReadHeapExp(new VarExp("a"))), new ValueExp(new IntValue(5)), 1));

        IStmt ex2 = new CompStmt(varDeclStmt3, new CompStmt(memoryStmt3, new CompStmt(varDeclStmt4, new CompStmt(memoryStmt4, new CompStmt(printStmt3, printStmt4)))));

        PrgState prgState2 = new PrgState(new MyStack<IStmt>(), new MyDictionary<String, IValue>(), new MyList<IValue>(), ex2, new MyDictionary<StringValue, BufferedReader>(), new MyHeap<Integer, IValue>());
        IRepo repository2 = new Repository(prgState2, logFilePath);
        Controller controller2 = new Controller(repository2);

        // example 3 - test write heap
        // Ref int v; new(v,20); print(rH(v)); wH(v,30); print(rH(v) + 5);

        VarDeclStmt varDeclStmt5 = new VarDeclStmt("v", new RefType(new IntType()));
        NewHeapMemoryStmt memoryStmt5 = new NewHeapMemoryStmt("v", new ValueExp(new IntValue(20)));
        PrintStmt printStmt5 = new PrintStmt(new ReadHeapExp(new VarExp("v")));
        WriteHeapStmt writeHeap1 = new WriteHeapStmt("v", new ValueExp(new IntValue(30)));
        PrintStmt printStmt6 = new PrintStmt(
                new ArithExp(new ReadHeapExp(new VarExp("v")), new ValueExp(new IntValue(5)), 1));

        IStmt ex3 = new CompStmt(varDeclStmt5, new CompStmt(memoryStmt5, new CompStmt(printStmt5, new CompStmt(writeHeap1, printStmt6))));

        PrgState prgState3 = new PrgState(new MyStack<IStmt>(), new MyDictionary<String, IValue>(), new MyList<IValue>(), ex3, new MyDictionary<StringValue, BufferedReader>(), new MyHeap<Integer, IValue>());
        IRepo repository3 = new Repository(prgState3, logFilePath);
        Controller controller3 = new Controller(repository3);

        // example 4 - test garbage collector
        // Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)));

        VarDeclStmt varDeclStmt6 = new VarDeclStmt("v", new RefType(new IntType()));

        NewHeapMemoryStmt memoryStmt6 = new NewHeapMemoryStmt("v", new ValueExp(new IntValue(20)));
        VarDeclStmt varDeclStmt7 = new VarDeclStmt("a", new RefType(new RefType(new IntType())));
        NewHeapMemoryStmt memoryStmt7 = new NewHeapMemoryStmt("a", new VarExp("v"));
        NewHeapMemoryStmt memoryStmt8 = new NewHeapMemoryStmt("v", new ValueExp(new IntValue(30)));
        PrintStmt printStmt7 = new PrintStmt(new ReadHeapExp(new ReadHeapExp(new VarExp("a"))));

        IStmt ex4 = new CompStmt(varDeclStmt6, new CompStmt(memoryStmt6, new CompStmt(varDeclStmt7, new CompStmt(memoryStmt7, new CompStmt(memoryStmt8, printStmt7)))));

        PrgState prgState4 = new PrgState(new MyStack<IStmt>(), new MyDictionary<String, IValue>(), new MyList<IValue>(), ex4, new MyDictionary<StringValue, BufferedReader>(), new MyHeap<Integer, IValue>());
        IRepo repository4 = new Repository(prgState4, logFilePath);
        Controller controller4 = new Controller(repository4);

        // example 5 - test while statement
        // int v; v=4; (while (v>0) print(v);v=v-1);print(v)

        VarDeclStmt varDeclStmt8 = new VarDeclStmt("v", new IntType());
        AssignStmt assignStmt1 = new AssignStmt("v", new ValueExp(new IntValue(4)));
        PrintStmt printStmt8 = new PrintStmt(new VarExp("v"));
        PrintStmt printStmt9 = new PrintStmt(new VarExp("v"));
        RelationalExp relationalExp1 = new RelationalExp(new VarExp("v"), new ValueExp(new IntValue(0)), ">=");
        ArithExp arithExp1 = new ArithExp(new VarExp("v"), new ValueExp(new IntValue(1)), 2);
        AssignStmt assignStmt2 = new AssignStmt("v", arithExp1);
        WhileStmt whileStmt1 = new WhileStmt(relationalExp1, new CompStmt(printStmt8, assignStmt2));

        IStmt ex5 = new CompStmt(varDeclStmt8, new CompStmt(assignStmt1, new CompStmt(whileStmt1, printStmt9)));

        PrgState prgState5 = new PrgState(new MyStack<IStmt>(), new MyDictionary<String, IValue>(), new MyList<IValue>(), ex5, new MyDictionary<StringValue, BufferedReader>(), new MyHeap<Integer, IValue>());
        IRepo repository5 = new Repository(prgState5, logFilePath);
        Controller controller5 = new Controller(repository5);

        // example 6 - test garbage collector delete from heap v - 10
        // Ref int v;new(v,10);new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)));

        VarDeclStmt varDeclStmt10 = new VarDeclStmt("v", new RefType(new IntType()));
        NewHeapMemoryStmt memoryStmt10 = new NewHeapMemoryStmt("v", new ValueExp(new IntValue(10)));
        NewHeapMemoryStmt memoryStmt11 = new NewHeapMemoryStmt("v", new ValueExp(new IntValue(20)));
        VarDeclStmt varDeclStmt11 = new VarDeclStmt("a", new RefType(new RefType(new IntType())));
        NewHeapMemoryStmt memoryStmt12 = new NewHeapMemoryStmt("a", new VarExp("v"));
        NewHeapMemoryStmt memoryStmt13 = new NewHeapMemoryStmt("v", new ValueExp(new IntValue(30)));
        PrintStmt printStmt10 = new PrintStmt(new ReadHeapExp(new ReadHeapExp(new VarExp("a"))));

        IStmt ex6 = new CompStmt(varDeclStmt6, new CompStmt(memoryStmt10, new CompStmt(memoryStmt6, new CompStmt(varDeclStmt7, new CompStmt(memoryStmt7, new CompStmt(memoryStmt8, printStmt7))))));

        PrgState prgState6 = new PrgState(new MyStack<IStmt>(), new MyDictionary<String, IValue>(), new MyList<IValue>(), ex6, new MyDictionary<StringValue, BufferedReader>(), new MyHeap<Integer, IValue>());
        IRepo repository6 = new Repository(prgState6, logFilePath);
        Controller controller6 = new Controller(repository6);


        // MENU
        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit\n"));
        menu.addCommand(new RunExample("1", "example 1 - test new heap memory\n" + ex1.toString() + "\n",controller1));
        menu.addCommand(new RunExample("2", "example 2 - test read heap\n" + ex2.toString() + "\n",controller2));
        menu.addCommand(new RunExample("3", "example 3 - test write heap\n" + ex3.toString() + "\n",controller3));
        menu.addCommand(new RunExample("4", "example 4 - test garbage collector\n" + ex4.toString() + "\n",controller4));
        menu.addCommand(new RunExample("5", "example 5 - test while statement\n" + ex5.toString() + "\n",controller5));
        menu.addCommand(new RunExample("6", "example 6 - test garbage collector delete from heap v = 10\n" + ex6.toString() + "\n",controller6));

        menu.show();


         */

        // A5

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
        ForkStmt forkStmt1 = new ForkStmt(new CompStmt(writeHeapStmt1, new CompStmt(assignStmt2, new CompStmt(printStmt1, printStmt2))));
        PrintStmt printStmt3 = new PrintStmt(new VarExp("v"));
        PrintStmt printStmt4 = new PrintStmt(new ReadHeapExp(new VarExp("a")));

        IStmt ex1 = new CompStmt(varDeclStmt1, new CompStmt(varDeclStmt2, new CompStmt(assignStmt1, new CompStmt(memoryStmt1, new CompStmt(forkStmt1, new CompStmt(printStmt3, printStmt4))))));

        // call the typecheck before creating the PrgState
        MyIDictionary<String, IType> typeEnv = new MyDictionary<String, IType>();
        try {
            ex1.typecheck(typeEnv);
            System.out.println("\n\n --------------Typecheck for example 1 passed---------------\n\n");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        PrgState prgState1 = new PrgState(new MyStack<IStmt>(), new MyDictionary<String, IValue>(), new MyList<IValue>(), ex1, new MyDictionary<StringValue, BufferedReader>(), new MyHeap<Integer, IValue>());
        IRepo repository1 = new Repository(prgState1, logFilePath);
        Controller controller1 = new Controller(repository1);

        // MENU
        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "Exit\n"));
        menu.addCommand(new RunExample("1", "Example 1\n" + ex1.toString() + "\n", controller1));

        menu.show();

    }
}
