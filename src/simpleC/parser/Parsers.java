package simpleC.parser;

import java.util.HashMap;
import java.util.Map;

public class Parsers {
    private static AbstractParser[] parsersList = new AbstractParser[]{
            new ProgramParser(),
            new ExternalDeclarationParser(),
            new FunctionDefinitionParser(),
            new IdentifierWordParser(),
            new DeclarationParser(),
            new ExpressionParser(),
            new AdditiveExpressionParser(),
            new IntegerConstantParser(),
            new ParameterDeclarationParser(),
            new TypeSpecifierParser()
    };

    private static Map<String, AbstractParser> parsers;

    public static AbstractParser getParser(String identifier) {
        if (parsers == null) {
            parsers = new HashMap<>();
            for (AbstractParser parser : parsersList) {
                parsers.put(parser.identifier, parser);
            }
        }
        if (!parsers.containsKey(identifier)) {
            throw new RuntimeException("Unknown identifier: " + identifier);
        }
        return parsers.get(identifier);
    }
}
