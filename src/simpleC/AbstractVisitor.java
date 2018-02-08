package simpleC;

import simpleC.evaluator.EvaluateContext;
import simpleC.interpreter.*;

public abstract class AbstractVisitor {
    public abstract void visitIntegerConstant(IntegerConstantWord integerConstantWord, EvaluateContext context);

    public abstract void visitDeclaration(DeclarationWord declarationWord, EvaluateContext context);

    public abstract void visitFunctionDefinition(FunctionDefinitionWord functionDefinitionWord, EvaluateContext context);

    public abstract void visitAdditiveExpression(AdditiveExpressionWord additiveExpressionWord, EvaluateContext context);
}
