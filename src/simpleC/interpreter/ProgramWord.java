package simpleC.interpreter;

import simpleC.AbstractVisitor;
import simpleC.evaluator.EvaluateContext;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.LinkedList;
import java.util.List;

public class ProgramWord extends AbstractWord {
    public List<ExternalDeclarationWord> externalDeclarations = new LinkedList<>();

    @Override
    public void interpret(EvaluateContext context, AbstractVisitor visitor) {
        throw new NotImplementedException();
    }
}
