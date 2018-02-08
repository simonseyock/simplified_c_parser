package simpleC.parser;

import simpleC.interpreter.AbstractWord;
import simpleC.interpreter.DeclarationWord;
import simpleC.interpreter.ExpressionWord;
import simpleC.interpreter.ParameterDeclarationWord;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeclarationParser extends AbstractParser {
    public DeclarationParser() {
        super("<declaration>", "<parameter-declaration> | <parameter-declaration> = <expression>");
    }

    @Override
    protected AbstractWord interpretResult(ParsingResult result) {
        DeclarationWord word = new DeclarationWord();
        word.parameterDeclaration = (ParameterDeclarationWord) result.wordResults.get(0).get(0);
        if (result.branch == 1) {
            word.value = (ExpressionWord) result.wordResults.get(1).get(1);
        }
        return word;
    }
}
