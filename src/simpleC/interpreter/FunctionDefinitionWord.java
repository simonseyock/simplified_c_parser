package simpleC.interpreter;

import simpleC.AbstractVisitor;
import simpleC.evaluator.EvaluateContext;

public class FunctionDefinitionWord extends ExternalDeclarationWord {


    public TypeSpecifierWord returnType;
    public IdentifierWord identifier;
    public ParameterListWord parameterList;
    public CompoundStatementWord functionBody;

    @Override
    public void interpret(EvaluateContext context, AbstractVisitor visitor) {
        visitor.visitFunctionDefinition(this, context);
    }
}
