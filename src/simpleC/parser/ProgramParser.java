package simpleC.parser;

import simpleC.interpreter.AbstractWord;
import simpleC.interpreter.ExternalDeclarationWord;
import simpleC.interpreter.ProgramWord;

public class ProgramParser extends AbstractParser {

    public ProgramParser() {
        super("<program>", "<external-declaration>*");
    }

    @Override
    protected AbstractWord interpretResult(ParsingResult result) {
        ProgramWord programWord = new ProgramWord();
        for (AbstractWord word: result.wordResults.get(0)) {
            programWord.externalDeclarations.add((ExternalDeclarationWord)word);
        }
        return programWord;
    }
}
