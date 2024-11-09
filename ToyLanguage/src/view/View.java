package view;

import exceptions.MyException;
import model.adt.*;
import model.expressions.ValueExp;
import model.expressions.VarExp;
import model.statements.*;
import model.types.IntType;
import model.values.IValue;
import model.state.PrgState;
import model.values.IntValue;
import model.values.StringValue;
import repository.*;
import controller.*;

import java.io.BufferedReader;
import java.util.Scanner;

public class View {
    private Controller controller;

    public View(Controller controller) {
        this.controller = controller;
    }

    public void printMenu() {
        System.out.println("\nToy Language Interpreter Menu");
        System.out.println("1. Input a program");
        System.out.println("2. Execute one step");
        System.out.println("3. Execute the whole program");
        System.out.println("4. Exit the program");
    }


    public PrgState addProgram(IStmt stmt) {
        //MyIStack<IStmt> exeStack, MyIDictionary<String,IValue> symTable, MyIList<IValue> outList, IStmt program
        PrgState prgState = new PrgState(new MyStack<IStmt>(), new MyDictionary<String, IValue>(), new MyList<IValue>(), stmt, new MyDictionary<StringValue, BufferedReader>());
        return prgState;
    }

    public void run() {
        while(true) {
            this.printMenu();
            System.out.print("Enter an option from the menu: ");
            Scanner scanner = new Scanner(System.in);
            String option = scanner.nextLine();

            if (option.equals("1")) {
                System.out.print("Enter the program: ");
                String program = scanner.nextLine();
                if (program.equals("1") || program.equals("2") || program.equals("3")) {
                    //ex 1
                    //prgState = this.addProgram(ex1);
                    System.out.println("The program was added successfully!");
                }
                else {
                    System.out.println("Invalid program");
                }
            }
            else if (option.equals("2")) {
                //this.controller.oneStep(prgState);
                System.out.println("Execute the program step by step");
            }
            else if (option.equals("3")) {
                try {
                    this.controller.allStep();
                }
                catch (MyException e) {
                    System.out.println(e.getMessage());
                }
            }
            else if (option.equals("4")) {
                System.out.println("Exiting the program...");
                break;
            }
            else {
                System.out.println("Invalid option");
            }
        }
    }

}
