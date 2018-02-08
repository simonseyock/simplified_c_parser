package simpleC.parser;

import simpleC.interpreter.AbstractWord;

public class ExternalDeclarationParser extends AbstractParser {
    public ExternalDeclarationParser() {
        super("<external-declaration>", "<function-definition> | <declaration>");
    }

    @Override
    protected AbstractWord interpretResult(ParsingResult result) {
        return result.wordResults.get(0).get(0);
    }
}
