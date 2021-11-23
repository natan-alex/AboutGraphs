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

        private static Matcher matcherForDirectedAndUnponderedPattern;
        private static Matcher matcherForDirectedAndPonderedPattern;
        private static Matcher matcherForUndirectedAndPonderedPattern;
        private static Matcher matcherForUndirectedAndUnponderedPattern;

        public static boolean isGraphRepresentationValid(String graphRepresentation) {
                return whichTypeOfGraphIs(graphRepresentation) != null;
        }

        public static GraphTypes whichTypeOfGraphIs(String graphRepresentation) {
                fillMatchersForGraphRepresentation(graphRepresentation);

                GraphTypes graphRepresentationType;

                if (matcherForDirectedAndUnponderedPattern.matches()) {
                        graphRepresentationType = GraphTypes.DIRECTED_AND_UNPONDERED;
                } else if (matcherForDirectedAndPonderedPattern.matches()) {
                        graphRepresentationType = GraphTypes.DIRECTED_AND_PONDERED;
                } else if (matcherForUndirectedAndPonderedPattern.matches()) {
                        graphRepresentationType = GraphTypes.UNDIRECTED_AND_PONDERED;
                } else if (matcherForUndirectedAndUnponderedPattern.matches()) {
                        graphRepresentationType = GraphTypes.UNDIRECTED_AND_UNPONDERED;
                } else {
                        graphRepresentationType = null;
                }

                return graphRepresentationType;
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