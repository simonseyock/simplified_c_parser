package simpleC.interpreter;

import simpleC.AbstractVisitor;
import simpleC.evaluator.EvaluateContext;

public abstract class AbstractWord {
    public abstract void interpret(EvaluateContext context, AbstractVisitor visitor);
}
