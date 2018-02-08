package simpleC.parser;

import simpleC.interpreter.AbstractWord;
import simpleC.interpreter.IdentifierWord;

public class IdentifierWordParser extends AbstractParser {
    public IdentifierWordParser() {
        super("<identifier>","[a-zA-Z_][a-zA-Z_0-9]*", false);
    }

    @Override
    protected AbstractWord interpretResult(ParsingResult result) {
        IdentifierWord word = new IdentifierWord();
        word.name = result.stringResults.get(0);
        return word;
    }
}
