package simpleC.parser;

import simpleC.interpreter.AbstractWord;
import simpleC.interpreter.TypeSpecifierWord;

public class TypeSpecifierParser extends AbstractParser {
    public TypeSpecifierParser() {
        super("<type-specifier>", "void|int|char|float", false);
    }

    @Override
    protected AbstractWord interpretResult(ParsingResult result) {
        TypeSpecifierWord word = new TypeSpecifierWord();
        word.type = result.stringResults.get(0);
        return word;
    }
}
