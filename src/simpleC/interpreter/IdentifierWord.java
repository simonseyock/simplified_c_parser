package simpleC.interpreter;

import simpleC.AbstractVisitor;
import simpleC.evaluator.EvaluateContext;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class IdentifierWord extends AbstractWord {

    public String name;

    @Override
    public void interpret(EvaluateContext context, AbstractVisitor visitor) {
        throw new NotImplementedException();
    }
}
