package core.validators;

import core.GraphTypes;

import java.util.regex.*;

public class GraphValidator {
    public static final String EXAMPLES_OF_VALID_GRAPHS_MESSAGE = "Examples of valid graphs:"
            + "\n  { (a, b) } for a directed and unpondered typedGraph"
            + "\n  { {hello, world} } for a undirected and unpondered typedGraph"
            + "\n  { (foo, bar, 10) } for a directed and pondered typedGraph"
            + "\n  { {hey, man, 1} } for a undirected and pondered typedGraph";

    public static final String INFORMATIVE_VALID_GRAPH_EXCEPTION_MESSAGE = "A valid typedGraph must be enclosed with {} and contains multiple edges inside curly braces."
            + "\nAn edge must be enclosed with () if it is part of a directed typedGraph or {} if it is part of an undirected typedGraph."
            + "\n" + EXAMPLES_OF_VALID_GRAPHS_MESSAGE;

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

    private static Matcher matcherForDirectedAndUnponderedPattern;
    private static Matcher matcherForDirectedAndPonderedPattern;
    private static Matcher matcherForUndirectedAndPonderedPattern;
    private static Matcher matcherForUndirectedAndUnponderedPattern;

    public static void validateGraphRepresentation(String graphRepresentation) throws IllegalArgumentException {
        GraphTypes type = whichTypeOfGraphIs(graphRepresentation);

        if (type == null)
            throwExceptionIfGraphIsInvalid();
    }

    public static GraphTypes whichTypeOfGraphIs(String graphRepresentation) {
        fillMatchersForGraphRepresentation(graphRepresentation);

        GraphTypes graphRepresentationType = null;

        if (matcherForDirectedAndUnponderedPattern.matches()) {
            graphRepresentationType = GraphTypes.DIRECTED_AND_UNPONDERED;
        } else if (matcherForDirectedAndPonderedPattern.matches()) {
            graphRepresentationType = GraphTypes.DIRECTED_AND_PONDERED;
        } else if (matcherForUndirectedAndPonderedPattern.matches()) {
            graphRepresentationType = GraphTypes.UNDIRECTED_AND_PONDERED;
        } else if (matcherForUndirectedAndUnponderedPattern.matches()) {
            graphRepresentationType = GraphTypes.UNDIRECTED_AND_UNPONDERED;
        }

        return graphRepresentationType;
    }

    private static void throwExceptionIfGraphIsInvalid() throws IllegalArgumentException {
        throw new IllegalArgumentException(INFORMATIVE_VALID_GRAPH_EXCEPTION_MESSAGE);
    }

    private static void fillMatchersForGraphRepresentation(String graphRepresentation) {
        matcherForDirectedAndPonderedPattern = PATTERN_TO_VALIDATE_A_DIRECTED_PONDERED_GRAPH
                .matcher(graphRepresentation);
        matcherForDirectedAndUnponderedPattern = PATTERN_TO_VALIDATE_A_DIRECTED_UNPONDERED_GRAPH
                .matcher(graphRepresentation);
        matcherForUndirectedAndPonderedPattern = PATTERN_TO_VALIDATE_AN_UNDIRECTED_PONDERED_GRAPH
                .matcher(graphRepresentation);
        matcherForUndirectedAndUnponderedPattern = PATTERN_TO_VALIDATE_AN_UNDIRECTED_UNPONDERED_GRAPH
                .matcher(graphRepresentation);
    }
}