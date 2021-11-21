import java.util.regex.*;

public class GraphValidator {
    public static final Pattern PATTERN_TO_VALIDATE_A_DIRECTED_UNPONDERED_GRAPH = Pattern.compile(
            "^\\s*\\{\\s*(?:\\{\\s*\\w+\\s*,\\s*\\w+\\s*\\}\\s*,\\s*)*\\{\\s*\\w+\\s*,\\s*\\w+\\s*\\}\\s*\\}\\s*$",
            Pattern.MULTILINE);
    public static final Pattern PATTERN_TO_VALIDATE_AN_UNDIRECTED_UNPONDERED_GRAPH = Pattern.compile(
            "^\\s*\\{\\s*(?:\\(\\s*\\w+\\s*,\\s*\\w+\\s*\\)\\s*,\\s*)*\\(\\s*\\w+\\s*,\\s*\\w+\\s*\\)\\s*\\}\\s*$",
            Pattern.MULTILINE);
    public static final Pattern PATTERN_TO_VALIDATE_A_DIRECTED_PONDERED_GRAPH = Pattern.compile(
            "^\\s*\\{\\s*(?:\\(\\s*\\w+\\s*,\\s*\\w+\\s*,\\s*(?:\\+|\\-)?\\d+\\s*\\)\\s*,\\s*)*\\(\\s*\\w+\\s*,\\s*\\w+\\s*,\\s*(?:\\+|\\-)?\\d+\\s*\\)\\s*\\}\\s*$",
            Pattern.MULTILINE);
    public static final Pattern PATTERN_TO_VALIDATE_AN_UNDIRECTED_PONDERED_GRAPH = Pattern.compile(
            "^\\s*\\{\\s*(?:\\{\\s*\\w+\\s*,\\s*\\w+\\s*,\\s*(?:\\+|\\-)?\\d+\\s*\\}\\s*,\\s*)*\\{\\s*\\w+\\s*,\\s*\\w+\\s*,\\s*(?:\\+|\\-)?\\d+\\s*\\}\\s*\\}\\s*$",
            Pattern.MULTILINE);

    public static final String INFORMATIVE_EXCEPTION_MESSAGE = "A valid graph must be enclosed with {} and contains multiple edges inside curly braces."
            + "\nAn edge must be enclosed with () if it is part of a directed graph or {} if it is part of an undirected graph."
            + "\nExample of valid graphs: \n  { (a, b), (b, c) }\n  { {hello, world}, {foo, bar} }\n  { (a,b,10), (a,c,12) }";

    private static Matcher matcherForDirectedAndUnponderedPattern;
    private static Matcher matcherForDirectedAndPonderedPattern;
    private static Matcher matcherForUndirectedAndPonderedPattern;
    private static Matcher matcherForUndirectedAndUnponderedPattern;

    private static boolean isGraphDirected;
    private static boolean isGraphPondered;

    public static void validateStringRepresentationAndFillGraphProperties(String graphRepresentation)
            throws IllegalArgumentException {
        fillMatchersForGraphRepresentation(graphRepresentation);

        if (matcherForDirectedAndUnponderedPattern.matches()) {
            isGraphDirected = true;
            isGraphPondered = false;
        } else if (matcherForDirectedAndPonderedPattern.matches()) {
            isGraphDirected = true;
            isGraphPondered = true;
        } else if (matcherForUndirectedAndPonderedPattern.matches()) {
            isGraphDirected = false;
            isGraphPondered = true;
        } else if (matcherForUndirectedAndUnponderedPattern.matches()) {
            isGraphDirected = false;
            isGraphPondered = false;
        } else {
            throw new IllegalArgumentException(
                    "Invalid graph " + graphRepresentation + "\n" + INFORMATIVE_EXCEPTION_MESSAGE);
        }
    }

    private static void fillMatchersForGraphRepresentation(String graphRepresentation) {
        matcherForDirectedAndUnponderedPattern = PATTERN_TO_VALIDATE_A_DIRECTED_UNPONDERED_GRAPH
                .matcher(graphRepresentation);
        matcherForDirectedAndPonderedPattern = PATTERN_TO_VALIDATE_A_DIRECTED_PONDERED_GRAPH
                .matcher(graphRepresentation);
        matcherForUndirectedAndPonderedPattern = PATTERN_TO_VALIDATE_AN_UNDIRECTED_PONDERED_GRAPH
                .matcher(graphRepresentation);
        matcherForUndirectedAndUnponderedPattern = PATTERN_TO_VALIDATE_AN_UNDIRECTED_UNPONDERED_GRAPH
                .matcher(graphRepresentation);
    }

    public static boolean isGraphDirected() {
        return isGraphDirected;
    }

    public static boolean isGraphPondered() {
        return isGraphPondered;
    }
}