package model.statements;

import exceptions.MyException;
import javafx.util.Pair;
import model.adt.MyDictionary;
import model.adt.MyIDictionary;
import model.adt.MyList;
import model.expressions.IExp;
import model.state.PrgState;
import model.types.IType;
import model.values.IValue;

import java.util.List;
import java.util.Vector;

public class FuncCallStmt implements IStmt{
    private String functionName;
    private MyList<IExp> parameters;

    public FuncCallStmt(String functionName, List<IExp> parameters) {
        this.functionName = functionName;
        this.parameters = new MyList<IExp>();

        for (int i = 0; i < parameters.size(); ++i) {
            this.parameters.add(parameters.get(i));
        }
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        try {
            Pair<List<String>, IStmt> functionEntry = state.getProcTable().getContent(functionName);
            if (functionEntry == null)
                throw new MyException(String.format("Function '%s' does not exist!", functionName));

            List<String> paramNames = functionEntry.getKey();
            IStmt functionBody = functionEntry.getValue();

            List<IValue> paramValues = new Vector<IValue>();
            for (int i = 0; i < parameters.size(); ++i)
                paramValues.add(parameters.get(i).eval(state.getSymTable(), state.getHeap()));

            MyIDictionary<String, IValue> newSymbolsTable = new MyDictionary<>();
            int size = paramNames.size();
            for (int i = 0; i < size; ++i)
                newSymbolsTable.insert(paramNames.get(i), paramValues.get(i));

            state.getSymTables().push(newSymbolsTable);
            state.getStack().push(new FuncReturnStmt());
            state.getStack().push(functionBody);
        } catch (MyException e) {
            throw new MyException(e.getMessage());
        }

        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String,IType> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public IStmt deepcopy() {
        return new FuncCallStmt(functionName, parameters.getAll());
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(functionName).append("(");
        List<IExp> list = parameters.getAll();
        for (IExp elem : list) {
            s.append(elem.toString()).append(", ");
        }
        s.append(")");

        // delete the last comma
        s.deleteCharAt(s.length() - 3);
        s.deleteCharAt(s.length() - 2);
        return s.toString();
    }
}
