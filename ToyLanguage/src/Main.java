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
        /*
        //Example1:
        //int v; v=2;Print(v) is represented as:
        IStmt ex1= new CompStmt(new VarDeclStmt("v",new IntType()),
                new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(2))), new PrintStmt(new
                        VarExp("v"))));





        //Example1:
        int v; v=2;Print(v) is represented as:
        IStmt ex1= new CompStmt(new VarDeclStmt("v",new IntType()),
        new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(2))), new PrintStmt(new
        VarExp("v"))))


        //Example2:
        int a;int b; a=2+3*5;b=a+1;Print(b) is represented as:
        IStmt ex2 = new CompStmt( new VarDeclStmt("a",new IntType()),
        new CompStmt(new VarDeclStmt("b",new IntType()),
        new CompStmt(new AssignStmt("a", new ArithExp('+',new ValueExp(new IntValue(2)),new
        ArithExp('*',new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
         new CompStmt(new AssignStmt("b",new ArithExp('+',new VarExp("a"), new ValueExp(new
        IntValue(1)))), new PrintStmt(new VarExp("b")))))

        //Example3:
        bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v) is represented as
        Stmt ex3 = new CompStmt(new VarDeclStmt("a",new BoolType()),
        new CompStmt(new VarDeclStmt("v", new IntType()),
        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
        new CompStmt(new IfStmt(new VarExp("a"),new AssignStmt("v",new ValueExp(new
        IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new
        VarExp("v")))))))

         */

        /*
        //ex 1
        IStmt stmt = new CompStmt(new VarDeclStmt("v",new IntType()),
                new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(2))), new PrintStmt(new
                        VarExp("v"))));
        PrgState prgState = new PrgState(new MyStack<IStmt>(), new MyDictionary<String, IValue>(), new MyList<IValue>(), stmt);

        //ex2
       // int a;int b; a=2+3*5;b=a+1;Print(b) is represented as:
//        IStmt ex2 = new CompStmt( new VarDeclStmt("a",new IntType()),
//                new CompStmt(new VarDeclStmt("b",new IntType()),
//                        new CompStmt(new AssignStmt("a", new ArithExp('+',new ValueExp(new IntValue(2)),new
//                                ArithExp('*',new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
//                                new CompStmt(new AssignStmt("b",new ArithExp('+',new VarExp("a"), new ValueExp(new
//                                        IntValue(1)))), new PrintStmt(new VarExp("b")))));

        */

        IStmt stmt = new CompStmt(new VarDeclStmt("a", new IntType()),
                new CompStmt(new VarDeclStmt("b", new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithExp(new ValueExp(new IntValue(2)),
                                new ArithExp(new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5)), 3), 1)),
                                new CompStmt(new AssignStmt("b", new ArithExp(new VarExp("a"),
                                        new ValueExp(new IntValue(1)), 1)), new PrintStmt(new VarExp("b"))))));

        PrgState prgState = new PrgState(new MyStack<IStmt>(), new MyDictionary<String, IValue>(), new MyList<IValue>(), stmt);

        IRepo repository = new Repository();
        repository.addPrgState(prgState);
        Controller controller = new Controller(repository);
        View view = new View(controller);

        view.run();
    }
}