package simpleC.parser;

import simpleC.interpreter.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class FunctionDefinitionParser extends AbstractParser {
    public FunctionDefinitionParser() {
        super("<function-definition>", "<type-specifier> <identifier> ( <parameter-list> ) <compound-statement>");
    }

    @Override
    protected AbstractWord interpretResult(ParsingResult result) {
        FunctionDefinitionWord word = new FunctionDefinitionWord();
        word.returnType = (TypeSpecifierWord) result.wordResults.get(0).get(0);
        word.identifier = (IdentifierWord) result.wordResults.get(1).get(0);
        word.parameterList = (ParameterListWord) result.wordResults.get(2).get(0);
        word.functionBody = (CompoundStatementWord) result.wordResults.get(3).get(0);
        return word;
    }
}
