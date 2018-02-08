package simpleC.interpreter;

import simpleC.AbstractVisitor;
import simpleC.evaluator.EvaluateContext;

public class DeclarationWord extends ExternalDeclarationWord {
    public ParameterDeclarationWord parameterDeclaration;
    public ExpressionWord value;

    @Override
    public void interpret(EvaluateContext context, AbstractVisitor visitor) {
        visitor.visitDeclaration(this, context);
    }
}
