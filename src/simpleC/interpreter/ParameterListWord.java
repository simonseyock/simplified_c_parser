package simpleC.interpreter;

import simpleC.AbstractVisitor;
import simpleC.evaluator.EvaluateContext;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class ParameterListWord extends AbstractWord {
    ParameterListWord left;
    ParameterDeclarationWord right;
    @Override
    public void interpret(EvaluateContext context, AbstractVisitor visitor) {
        throw new NotImplementedException();
    }
}
