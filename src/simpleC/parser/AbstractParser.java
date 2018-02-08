package simpleC.parser;

import simpleC.interpreter.AbstractWord;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractParser {

    public final String identifier;
    private Pattern regEx;
    private String combinator;


    public AbstractParser(String identifier, String combinator) {
        this.identifier = identifier;
        this.combinator = combinator;
    }

    public AbstractParser(String identifier, String expr, boolean isCombinator) {
        this.identifier = identifier;
        if (isCombinator) {
            this.combinator = expr;
        }
        else {
            this.regEx = Pattern.compile("\\s*(" + expr + ")\\s*");
        }
    }

    private List<ParsingBranch> branches;

    private static List<Memo> memos = new LinkedList<>();

    private parseWord(ParsingState state, String identifier, int branch) {
        Memo memo = memos.stream().filter(m -> m.position == state.position && m.identifier.equals(identifier) && m.branch == branch)
                .findAny().orElse(null);
        if (memo != null) {
            return memo.word;
        }
        Parsers.getParser(identifier).read(state.text);
    }

    private ParsingResult parseBranch(ParsingState state, int branch) {
        ParsingBranch b = branches.get(branch);
        List<String> stringResults = new LinkedList<>();
        List<List<AbstractWord>> wordResults = new LinkedList<>();
        for (int i = 0; i < b.childWords.size(); i++) {
            if (b.childWords.get(i) != null) {
                AbstractParser parser = Parsers.getParser(b.childWords.get(i));
                AbstractWord word = parser.readInternal(state);
                List<AbstractWord> words = new LinkedList<>();
                String quantifier = b.wordQuantifier.get(i);
                if (quantifier == null) {
                    if (word == null) {
                        return null;
                    } else {
                        words.add(word);
                    }
                } else if (quantifier.equals("*")) {
                    while (word != null) {
                        words.add(word);
                        word = parser.readInternal(state);
                    }
                } else if (quantifier.equals("+")) {
                    if (word == null) {
                        return null;
                    } else {
                        while (word != null) {
                            words.add(word);
                            word = parser.readInternal(state);
                        }
                    }
                } else if (quantifier.equals("?")) {
                    if (word != null) {
                        words.add(word);
                    }
                }
                wordResults.add(words);
            }
            if (b.other.get(i) != null) {
                Pattern pattern = Pattern.compile("\\s*(" + Pattern.quote(b.other.get(i)) + ")\\s*");
                Matcher matcher = pattern.matcher(state.text);
                if (!matcher.find()) {
                    return null;
                }
                state.text = matcher.replaceFirst("");
                stringResults.add(matcher.group(1));
            }
        }
        return new ParsingResult(stringResults, wordResults, branch);
    }

    private void prepareBranches() {
        branches = new LinkedList<>();
        String[] textBranches = combinator.split("\\|");
        for (String textBranch: textBranches) {
            Pattern stepper = Pattern.compile("\\s*((<[^>]+>)([*+?])?)?\\s*([^<]+)?\\s*");
            Matcher matcher = stepper.matcher(textBranch);
            List<String> childWords = new LinkedList<>();
            List<String> wordQuantifier = new LinkedList<>();
            List<String> other = new LinkedList<>();
            while (matcher.find()) {
                if (!matcher.group(0).equals("")) {
                    childWords.add(matcher.group(2));
                    wordQuantifier.add(matcher.group(3));
                    other.add(matcher.group(4));
                }
            }
            ParsingBranch branch = new ParsingBranch();
            branch.childWords = childWords;
            branch.wordQuantifier = wordQuantifier;
            branch.other = other;
            branches.add(branch);
        }
    }

    public AbstractWord read(String text) {
        return readInternal(new ParsingState(text));
    }

    private AbstractWord readInternal(ParsingState state) {
        ParsingResult result = null;
        if (combinator != null) {
            if (branches == null) {
                prepareBranches();
            }

            for (Function<ParsingState, ParsingResult> branch : branches) {
                result = branch.apply(state);
                if (result != null) {
                    break;
                }
            }
        } else {
            Matcher matcher = regEx.matcher(state.text);
            if (!matcher.find()) {
                return null;
            }
            LinkedList<String> stringResults = new LinkedList<>();
            stringResults.add(matcher.group(1));
            state.text = matcher.replaceFirst("");
            result = new ParsingResult(stringResults, null, 0);
        }
        if (result == null) {
            return null;
        }
        return this.interpretResult(result);
    }

    protected abstract AbstractWord interpretResult(ParsingResult result);

    public class ParsingState {
        public String text;
        public int position;

        ParsingState(String text) {
            this.text = text;
        }

        public ParsingState cloneState() {
            return new ParsingState(this.text);
        }

        public void fix(ParsingState state) {
            int offset = text.length() - state.text.length();
            text = state.text;
            position += offset;
        }
    }

    private class Memo {
        int position;
        String identifier;
        int branch;
        AbstractWord word;
    }

    class ParsingResult {
        final List<String> stringResults;
        final List<List<AbstractWord>> wordResults;
        final int branch;

        ParsingResult(List<String> stringResults, List<List<AbstractWord>> wordResults, int branch) {
            this.stringResults = stringResults;
            this.wordResults = wordResults;
            this.branch = branch;
        }
    }

    private class ParsingBranch {
        public List<String> childWords;
        public List<String> wordQuantifier;
        public List<String> other;
    }
}