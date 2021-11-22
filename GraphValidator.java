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
                return whichTypeOfGraphRepresentationIs(graphRepresentation) != null;
        }

        public static GraphRepresentationTypes whichTypeOfGraphRepresentationIs(String graphRepresentation) {
                GraphRepresentationTypes graphRepresentationType;

                if (matcherForDirectedAndUnponderedPattern.matches()) {
                        graphRepresentationType = GraphRepresentationTypes.DIRECTED_AND_UNPONDERED;
                } else if (matcherForDirectedAndPonderedPattern.matches()) {
                        graphRepresentationType = GraphRepresentationTypes.DIRECTED_AND_PONDERED;
                } else if (matcherForUndirectedAndPonderedPattern.matches()) {
                        graphRepresentationType = GraphRepresentationTypes.UNDIRECTED_AND_PONDERED;
                } else if (matcherForUndirectedAndUnponderedPattern.matches()) {
                        graphRepresentationType = GraphRepresentationTypes.UNDIRECTED_AND_UNPONDERED;
                } else {
                        graphRepresentationType = null;
                }

                return graphRepresentationType;
        }
}