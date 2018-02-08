package simpleC.interpreter;

import simpleC.AbstractVisitor;
import simpleC.evaluator.EvaluateContext;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class CompoundStatementWord extends AbstractWord {
    @Override
    public void interpret(EvaluateContext context, AbstractVisitor visitor) {
        throw new NotImplementedException();
    }
}
