package AboutGraphs.core;

import java.util.regex.*;

public class GraphValidator {
        public static final String EXAMPLES_OF_VALID_GRAPHS_MESSAGE = "Examples of valid graphs:"
                        + "\n  { (a, b) } for a directed and unpondered graph"
                        + "\n  { {hello, world} } for a undirected and unpondered graph"
                        + "\n  { (foo, bar, 10) } for a directed and pondered graph"
                        + "\n  { {hey, man, 1} } for a undirected and pondered graph";

        public static final String INFORMATIVE_VALID_GRAPH_EXCEPTION_MESSAGE = "A valid graph must be enclosed with {} and contains multiple edges inside curly braces."
                        + "\nAn edge must be enclosed with () if it is part of a directed graph or {} if it is part of an undirected graph."
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

        public static boolean isGraphRepresentationValid(String graphRepresentation) {
                return whichTypeOfGraphIs(graphRepresentation) != null;
        }

        public static GraphTypes whichTypeOfGraphIs(String graphRepresentation) throws IllegalArgumentException {
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
                } else {
                        throwExceptionIfGraphIsInvalid(graphRepresentationType);
                }

                return graphRepresentationType;
        }

        private static void throwExceptionIfGraphIsInvalid(GraphTypes graphType) throws IllegalArgumentException {
                if (graphType == null) {
                        throw new IllegalArgumentException(INFORMATIVE_VALID_GRAPH_EXCEPTION_MESSAGE);
                }
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