package simpleC.parser;

import simpleC.interpreter.AbstractWord;
import simpleC.interpreter.IntegerConstantWord;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IntegerConstantParser extends AbstractParser {
    public IntegerConstantParser() {
        super("<integer-constant>", "[0-9]+", false);
    }

    @Override
    protected AbstractWord interpretResult(ParsingResult result) {
        IntegerConstantWord word = new IntegerConstantWord();
        word.value = Integer.parseInt(result.stringResults.get(0));
        return word;
    }
}
