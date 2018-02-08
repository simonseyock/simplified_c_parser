package simpleC.parser;

import simpleC.interpreter.AbstractWord;

public class ExpressionParser extends AbstractParser {
    public ExpressionParser() {
        super("<expression>", "<additive-expression>");
    }

    @Override
    protected AbstractWord interpretResult(ParsingResult result) {
        return result.wordResults.get(0).get(0);
    }
}
