package simpleC.evaluator;

import simpleC.AbstractContext;
import simpleC.interpreter.DeclarationWord;
import simpleC.interpreter.ExpressionWord;
import simpleC.interpreter.FunctionDefinitionWord;

import java.util.LinkedList;
import java.util.List;

public class EvaluateContext extends AbstractContext {
    private List<DeclarationWord> declarations = new LinkedList<>();
    private List<FunctionDefinitionWord> functions = new LinkedList<>();
    public ExpressionWord result;

    public EvaluateContext() {
        super();
    }

    public EvaluateContext(EvaluateContext parentContext) {
        super(parentContext);
    }

    @Override
    public void addDeclaration(DeclarationWord param) {
        declarations.add(param);
    }

    @Override
    public void addFunction(FunctionDefinitionWord func) {
        functions.add(func);
    }

    @Override
    public DeclarationWord getDeclaration(String identifier) {
        DeclarationWord var = declarations.stream().filter(v -> v.parameterDeclaration.identifier.equals(identifier))
                .findAny()
                .orElse(null);

        if (var != null) return var;

        if (parentContext != null) {
            return parentContext.getDeclaration(identifier);
        }

        throw new RuntimeException("Variable <" + identifier + "> not defined!");
    }

    @Override
    public FunctionDefinitionWord getFunction(String identifier) {
        FunctionDefinitionWord func = functions.stream().filter(f -> f.identifier.equals(identifier))
                .findAny()
                .orElse(null);

        if (func != null) return func;

        if (parentContext != null) {
            return parentContext.getFunction(identifier);
        }

        throw new RuntimeException("Function <" + identifier + "> not defined!");
    }
}
