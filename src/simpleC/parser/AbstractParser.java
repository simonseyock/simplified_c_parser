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

    private List<Function<ParsingState, ParsingResult>> branches;

    private ParsingResult parseBranch(ParsingState state, List<String> childWords, List<String> wordQuantifier, List<String> other, int branch) {
        List<String> stringResults = new LinkedList<>();
        List<List<AbstractWord>> wordResults = new LinkedList<>();
        for (int i = 0; i < childWords.size(); i++) {
            if (childWords.get(i) != null) {
                AbstractParser parser = Parsers.getParser(childWords.get(i));
                AbstractWord word = parser.readInternal(state);
                List<AbstractWord> words = new LinkedList<>();
                String quantifier = wordQuantifier.get(i);
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
            if (other.get(i) != null) {
                Pattern pattern = Pattern.compile("\\s*(" + Pattern.quote(other.get(i)) + ")\\s*");
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
        for (int i = 0; i < textBranches.length; i++) {
            final int branch = i;
            String textBranch = textBranches[branch];
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
            branches.add((ParsingState inputState) -> {
                ParsingState clonedState = inputState.cloneState();
                ParsingResult result = parseBranch(clonedState, childWords, wordQuantifier, other, branch);
                if (result != null) {
                    inputState.fix(clonedState);
                }
                return result;
            });
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

        public List<MemoResult> results = new LinkedList<>();

        public boolean completed;

        ParsingState(String text) {
            this.text = text;
        }

        public ParsingState getNextState(int branch) {
            ParsingState state = new ParsingState(this.text);
            return state;
        }

        public MemoResult getMemoResult(String identifier, int branch) {
            return results.stream().filter(r -> r.identifier.equals(identifier) && r.branch == branch)
                    .findAny().orElse(null);
        }

        public void addMemoResult(MemoResult res) {
            if (res.nextState.text == text) {
                this
            }
            results.add(res);
        }
    }

    public class MemoResult {
        public String identifier;
        public AbstractWord result;
        public int branch;
        public ParsingState nextState;
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
}