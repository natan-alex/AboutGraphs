package AboutGraphs.searches;

import java.util.regex.*;

public class GraphHeuristicsValidator {
    private static Pattern PATTERN_TO_VALIDATE_THE_HEURISTICS = Pattern
            .compile("^\\s*\\[\\s*(?:\\s*\\w+\\s*:\\s*\\d+\\s*,)*\\s*\\w+\\s*:\\s*\\d+\\s*\\]\\s*", Pattern.MULTILINE);

    public static void validateHeuristicsRepresentation(String heuristicsRepresentation)
            throws IllegalArgumentException {
        Matcher matcher = PATTERN_TO_VALIDATE_THE_HEURISTICS.matcher(heuristicsRepresentation);

        if (!matcher.matches())
            throw new IllegalArgumentException("The heuristic " + heuristicsRepresentation
                    + " is not a valid heuristic. A valid heuristic is something like [ a: 1, b: 2.3, c: 3.0 ]"
                    + " where the vertice is on the left of the : and the heuristic value is on the rigth.");
    }
}
