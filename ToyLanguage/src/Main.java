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

        // A3
        // test file example 1
        /*
        String varf;
        varf="test.in";
        OpenRFileStmt(varf);
        int varc;
        ReadFileStmt(varf,varc);
        print(varc);
        ReadFileStmt(varf,varc);
        print(varc);
        CloseRFile(varf);
         */
        /*
        CloseRFileStmt closeRF = new CloseRFileStmt(new VarExp("varf"));
        CompStmt compStmt1 = new CompStmt(new PrintStmt(new VarExp("varc")), closeRF);
        ReadFileStmt readF = new ReadFileStmt(new VarExp("varf"), "varc");
        CompStmt compStmt2 = new CompStmt(readF, compStmt1);
        PrintStmt printStmt = new PrintStmt(new VarExp("varc"));
        CompStmt compStmt3 = new CompStmt(printStmt, compStmt2);
        CompStmt compStmt4 = new CompStmt(new ReadFileStmt(new VarExp("varf"), "varc"), compStmt3);
        VarDeclStmt varDeclStmt = new VarDeclStmt("varc", new IntType());
        CompStmt compStmt5 = new CompStmt(varDeclStmt, compStmt4);
        OpenRFileStmt openRF = new OpenRFileStmt(new VarExp("varf"));
        CompStmt compStmt6 = new CompStmt(openRF, compStmt5);
        AssignStmt assignStmt = new AssignStmt("varf", new ValueExp(new StringValue("test.in")));
        CompStmt compStmt7 = new CompStmt(assignStmt, compStmt6);
        VarDeclStmt varDeclStmt2 = new VarDeclStmt("varf", new StringType());
        IStmt ex1 = new CompStmt(varDeclStmt2, compStmt7);

        PrgState prgState1 = new PrgState(new MyStack<IStmt>(), new MyDictionary<String, IValue>(), new MyList<IValue>(), ex1, new MyDictionary<StringValue, BufferedReader>());
        IRepo repository1 = new Repository(prgState1, logFilePath);
        Controller controller1 = new Controller(repository1);
        */
        // example 2
        /*
        String f;
        f="f1.in";
        int a;
        openRFile(f);
        readFile(f,a);
        print(a);
        if (a>0) then print(a) else print("Value is not positive");
        closeRFile(f);
         */
        /*
        CloseRFileStmt closeRF2 = new CloseRFileStmt(new VarExp("f"));
        PrintStmt printStmt21 = new PrintStmt(new VarExp("a"));
        PrintStmt printStmt22 = new PrintStmt(new ValueExp(new StringValue("Value is not positive")));
        RelationalExp relationalExp = new RelationalExp(new VarExp("a"), new ValueExp(new IntValue(0)), ">");
        IfStmt ifStmt = new IfStmt(relationalExp, printStmt21, printStmt22);
        CompStmt compStmt21 = new CompStmt(ifStmt, closeRF2);
        ReadFileStmt readF2 = new ReadFileStmt(new VarExp("f"), "a");
        CompStmt compStmt22 = new CompStmt(readF2, compStmt21);
        VarDeclStmt varDeclStmt21 = new VarDeclStmt("a", new IntType());
        CompStmt compStmt23 = new CompStmt(varDeclStmt21, compStmt22);
        OpenRFileStmt openRF2 = new OpenRFileStmt(new VarExp("f"));
        CompStmt compStmt24 = new CompStmt(openRF2, compStmt23);
        AssignStmt assignStmt2 = new AssignStmt("f", new ValueExp(new StringValue("f1.in")));
        CompStmt compStmt25 = new CompStmt(assignStmt2, compStmt24);
        VarDeclStmt varDeclStmt22 = new VarDeclStmt("f", new StringType());
        IStmt ex2 = new CompStmt(varDeclStmt22, compStmt25);

        PrgState prgState2 = new PrgState(new MyStack<IStmt>(), new MyDictionary<String, IValue>(), new MyList<IValue>(), ex2, new MyDictionary<StringValue, BufferedReader>());
        IRepo repository2 = new Repository(prgState2, logFilePath);
        Controller controller2 = new Controller(repository2);
        */
        // example 3
        /*
        String f;
        f = "f2.in";
        bool a;
        int x;
        int y;
        int v;
        openRFile(f);
        readFile(f,x);
        readFile(f,y);
        readFile(f,v);
        if (x + y = v) then a = true else a = false;
        print(a);
        if (a = true) then v = v - 1 else v = v + 1;
        print(v);
        closeRFile(f);
         */
        /*
        CloseRFileStmt closeRF3 = new CloseRFileStmt(new VarExp("f"));
        PrintStmt printStmt31 = new PrintStmt(new VarExp("v"));
        CompStmt compStmt31 = new CompStmt(printStmt31, closeRF3);
        AssignStmt assignStmt31 = new AssignStmt("v", new ArithExp(new VarExp("v"), new ValueExp(new IntValue(1)), 2)); // v = v - 1
        AssignStmt assignStmt32 = new AssignStmt("v", new ArithExp(new VarExp("v"), new ValueExp(new IntValue(1)), 1)); // v = v + 1
        VarExp varExp3 = new VarExp("a"); // a = true
        IfStmt ifStmt3 = new IfStmt(varExp3, assignStmt31, assignStmt32);
        CompStmt compStmt32 = new CompStmt(ifStmt3, compStmt31);
        PrintStmt printStmt32 = new PrintStmt(new VarExp("a"));
        CompStmt compStmt33 = new CompStmt(printStmt32, compStmt32);
        AssignStmt assignStmt33 = new AssignStmt("a", new ValueExp(new BoolValue(true)));
        AssignStmt assignStmt34 = new AssignStmt("a", new ValueExp(new BoolValue(false)));
        RelationalExp relationalExp3 = new RelationalExp(new ArithExp(new VarExp("x"), new VarExp("y"), 1), new VarExp("v"), "==");
        IfStmt ifStmt4 = new IfStmt(relationalExp3, assignStmt33, assignStmt34);
        CompStmt compStmt34 = new CompStmt(ifStmt4, compStmt33);
        ReadFileStmt readF3 = new ReadFileStmt(new VarExp("f"), "v");
        CompStmt compStmt35 = new CompStmt(readF3, compStmt34);
        ReadFileStmt readF4 = new ReadFileStmt(new VarExp("f"), "y");
        CompStmt compStmt36 = new CompStmt(readF4, compStmt35);
        ReadFileStmt readF5 = new ReadFileStmt(new VarExp("f"), "x");
        CompStmt compStmt37 = new CompStmt(readF5, compStmt36);
        OpenRFileStmt openRF3 = new OpenRFileStmt(new VarExp("f"));
        CompStmt compStmt38 = new CompStmt(openRF3, compStmt37);
        VarDeclStmt varDeclStmt31 = new VarDeclStmt("v", new IntType());
        CompStmt compStmt39 = new CompStmt(varDeclStmt31, compStmt38);
        VarDeclStmt varDeclStmt32 = new VarDeclStmt("y", new IntType());
        CompStmt compStmt310 = new CompStmt(varDeclStmt32, compStmt39);
        VarDeclStmt varDeclStmt33 = new VarDeclStmt("x", new IntType());
        CompStmt compStmt311 = new CompStmt(varDeclStmt33, compStmt310);
        VarDeclStmt varDeclStmt34 = new VarDeclStmt("a", new BoolType());
        CompStmt compStmt312 = new CompStmt(varDeclStmt34, compStmt311);
        AssignStmt assignStmt35 = new AssignStmt("f", new ValueExp(new StringValue("f2.in")));
        CompStmt compStmt313 = new CompStmt(assignStmt35, compStmt312);
        VarDeclStmt varDeclStmt35 = new VarDeclStmt("f", new StringType());
        IStmt ex3 = new CompStmt(varDeclStmt35, compStmt313);

        PrgState prgState3 = new PrgState(new MyStack<IStmt>(), new MyDictionary<String, IValue>(), new MyList<IValue>(), ex3, new MyDictionary<StringValue, BufferedReader>());
        IRepo repository3 = new Repository(prgState3, logFilePath);
        Controller controller3 = new Controller(repository3);
        */

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
    }
}
