package simpleC.interpreter;

import simpleC.AbstractVisitor;
import simpleC.evaluator.EvaluateContext;

public class AdditiveExpressionWord extends ExpressionWord {

    public ExpressionWord left;
    public ExpressionWord right;
    public String operator;

    @Override
    public void interpret(EvaluateContext context, AbstractVisitor visitor) {
        visitor.visitAdditiveExpression(this, context);
    }
}
