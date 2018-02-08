package simpleC.parser;

import simpleC.interpreter.AbstractWord;
import simpleC.interpreter.IdentifierWord;
import simpleC.interpreter.ParameterDeclarationWord;
import simpleC.interpreter.TypeSpecifierWord;

public class ParameterDeclarationParser extends AbstractParser {
    public ParameterDeclarationParser() {
        super("<parameter-declaration>", "<type-specifier> <identifier>");
    }

    @Override
    protected AbstractWord interpretResult(ParsingResult result) {
        ParameterDeclarationWord word = new ParameterDeclarationWord();
        word.type = (TypeSpecifierWord) result.wordResults.get(0).get(0);
        word.identifier = (IdentifierWord) result.wordResults.get(1).get(0);
        return word;
    }
}
