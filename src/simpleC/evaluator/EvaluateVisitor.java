package simpleC.evaluator;

import simpleC.AbstractVisitor;
import simpleC.interpreter.*;

public class EvaluateVisitor extends AbstractVisitor {
    @Override
    public void visitIntegerConstant(IntegerConstantWord integerConstantWord, EvaluateContext context) {
        context.result = integerConstantWord;
    }

    @Override
    public void visitDeclaration(DeclarationWord declarationWord, EvaluateContext context) {
        if (declarationWord.value != null) {
            declarationWord.value.interpret(context, this);
            declarationWord.value = context.result;
        }
        context.addDeclaration(declarationWord);
    }

    @Override
    public void visitFunctionDefinition(FunctionDefinitionWord functionDefinitionWord, EvaluateContext context) {
        context.addFunction(functionDefinitionWord);
    }

    @Override
    public void visitAdditiveExpression(AdditiveExpressionWord additiveExpressionWord, EvaluateContext context) {
        additiveExpressionWord.left.interpret(context, this);
        ExpressionWord left = context.result;
        additiveExpressionWord.right.interpret(context, this);
        ExpressionWord right = context.result;

        if (left instanceof IntegerConstantWord && right instanceof IntegerConstantWord) {
            IntegerConstantWord integer = new IntegerConstantWord();
            if (additiveExpressionWord.operator.equals("+")) {
                integer.value = ((IntegerConstantWord) left).value + ((IntegerConstantWord) right).value;
            } else if (additiveExpressionWord.operator.equals("-")) {
                integer.value = ((IntegerConstantWord) left).value - ((IntegerConstantWord) right).value;
            }
            context.result = integer;
            return;
        } else {
            throw new RuntimeException("Incompatible types for addition");
        }
    }

}
