package simpleC;
import simpleC.interpreter.DeclarationWord;
import simpleC.interpreter.FunctionDefinitionWord;

public abstract class AbstractContext {

    protected AbstractContext parentContext;

    public AbstractContext () {
    }

    public AbstractContext (AbstractContext parentContext) {
        this.parentContext = parentContext;
    }

    public abstract void addDeclaration(DeclarationWord param);

    public abstract void addFunction(FunctionDefinitionWord func);

    public abstract DeclarationWord getDeclaration(String identifier);

    public abstract FunctionDefinitionWord getFunction(String identifier);
}
