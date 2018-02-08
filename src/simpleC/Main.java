package simpleC;

import simpleC.interpreter.ProgramWord;
import simpleC.parser.Parsers;

public class Main {

    public static void main(String[] args) {
        ProgramWord program = (ProgramWord) Parsers.getParser("<program>").read("int test = 4");
    }
}
