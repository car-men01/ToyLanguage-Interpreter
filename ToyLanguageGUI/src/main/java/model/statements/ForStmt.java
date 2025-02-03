package model.statements;

import exceptions.MyException;
import model.adt.MyIDictionary;
import model.expressions.IExp;
import model.expressions.RelationalExp;
import model.expressions.VarExp;
import model.state.PrgState;
import model.types.IType;
import model.types.IntType;

public class ForStmt implements IStmt {
    //Define the new statement:
    //
    //for(v=exp1;v<exp2;v=exp3) stmt
    //Its execution on the ExeStack is the following:
    //- pop the statement
    //- create the following statement:
    //
    //int v; v=exp1;(while(v<exp2) stmt;v=exp3)
    //
    //- push the new statement on the stack
    //The typecheck method of for statement verifies if v, exp1, exp2, and exp3 have
    //the type int.



    private IStmt stmt;
    private IExp exp1;
    private IExp exp2;
    private IExp exp3;
    private String var;

    public ForStmt(String var, IExp exp1, IExp exp2, IExp exp3, IStmt stmt) {
        this.var = var;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.exp3 = exp3;
        this.stmt = stmt;
    }

    @Override
    public String toString() {
        return "for(" + var + "=" + exp1.toString() + ";" + var + "<" + exp2.toString() + ";" + var + "=" + exp3.toString() + ") {\n" + stmt.toString() + "\n}";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IStmt newStmt = new CompStmt(new VarDeclStmt(var, new IntType()),
                new CompStmt(new AssignStmt(var, exp1),
                        new WhileStmt(new RelationalExp( new VarExp(var), exp2, "<"),
                                new CompStmt(stmt, new AssignStmt(var, exp3)))));
        state.getStack().push(newStmt);
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IStmt vardeclstmt = new VarDeclStmt(var, new IntType());
        vardeclstmt.typecheck(typeEnv);

        IType t1 = exp1.typecheck(typeEnv);
        IType t2 = exp2.typecheck(typeEnv);
        IType t3 = exp3.typecheck(typeEnv);
        if (t1.equals(new IntType()) && t2.equals(new IntType()) && t3.equals(new IntType())) {
            stmt.typecheck(typeEnv);
            return typeEnv;
        }
        else throw new MyException("For statement typecheck failed");
    }

    @Override
    public IStmt deepcopy() {
        return new ForStmt(var, exp1.deepcopy(), exp2.deepcopy(), exp3.deepcopy(), stmt.deepcopy());
    }
}
