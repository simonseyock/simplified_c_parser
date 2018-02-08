package simpleC.interpreter;

import simpleC.AbstractVisitor;
import simpleC.evaluator.EvaluateContext;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class ParameterDeclarationWord extends AbstractWord {
    public TypeSpecifierWord type;
    public IdentifierWord identifier;
    @Override
    public void interpret(EvaluateContext context, AbstractVisitor visitor) {
        throw new NotImplementedException();
    }
}
