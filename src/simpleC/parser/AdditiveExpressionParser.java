package simpleC.parser;

import simpleC.interpreter.AbstractWord;
import simpleC.interpreter.AdditiveExpressionWord;
import simpleC.interpreter.ExpressionWord;

public class AdditiveExpressionParser extends AbstractParser {
    public AdditiveExpressionParser() {
        super("<additive-expression>", "<integer-constant> | <additive-expression> + <integer-constant> | <additive-expression> - <integer-constant>");
    }

    @Override
    protected AbstractWord interpretResult(ParsingResult result) {
        if (result.branch == 0) {
            return result.wordResults.get(0).get(0);
        }
        AdditiveExpressionWord word = new AdditiveExpressionWord();
        word.left = (ExpressionWord) result.wordResults.get(0).get(0);
        word.operator = result.stringResults.get(0);
        word.right = (ExpressionWord) result.wordResults.get(1).get(0);
        return word;
    }
}
