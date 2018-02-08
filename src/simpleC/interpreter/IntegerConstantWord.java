package simpleC.interpreter;

import simpleC.AbstractVisitor;
import simpleC.evaluator.EvaluateContext;

public class IntegerConstantWord extends ExpressionWord {
    public int value;
    @Override
    public void interpret(EvaluateContext context, AbstractVisitor visitor) {
        visitor.visitIntegerConstant(this, context);
    }
}
