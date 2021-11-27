package aboutGraphs.core;

import java.util.regex.*;

public class EdgeValidator {
    public static final String EXAMPLES_OF_VALID_EDGES_MESSAGE = "Example of valid edges:"
            + "\n  (a, b) for a directed and unpondered edge"
            + "\n  {hello, world} for a undirected and unpondered edge"
            + "\n  (foo, bar, 10) for a directed and pondered edge"
            + "\n  {hey, man, 1} for a undirected and pondered edge";

    public static final String INFORMATIVE_EXCEPTION_MESSAGE = "A valid edge must be enclosed with () if it is part of a directed graph and with {} if it is part of an undirected graph."
            + "\n" + EXAMPLES_OF_VALID_EDGES_MESSAGE;

    public static final Pattern PATTERN_TO_VALIDATE_AN_EDGE = Pattern
            .compile("[(|{]\\s*\\w+\\s*,\\s*\\w+\\s*(?:,\\s*\\d{1,6}\\s*)?[)|}]");

    public static void checkIfEdgeRepresentationIsValid(String edgeRepresentation) throws IllegalArgumentException {
        checkIfRepresentationMatchesThePattern(edgeRepresentation);
        checkIfRepresentationIsEnclosedCorrectly(edgeRepresentation);
    }

    private static void checkIfRepresentationMatchesThePattern(String edgeRepresentation) {
        Matcher matcher = PATTERN_TO_VALIDATE_AN_EDGE.matcher(edgeRepresentation);

        if (!matcher.matches()) {
            throwExceptionForInvalidEdge(edgeRepresentation);
        }
    }

    private static void throwExceptionForInvalidEdge(String edgeRepresentation) {
        throw new IllegalArgumentException(
                "Invalid edge " + edgeRepresentation + "\n" + EXAMPLES_OF_VALID_EDGES_MESSAGE);
    }

    private static void checkIfRepresentationIsEnclosedCorrectly(String edgeRepresentation) {
        if (!isEdgeRepresentationEnclosedCorrectly(edgeRepresentation)) {
            throw new IllegalArgumentException("Invalid edge " + edgeRepresentation
                    + ": the edge representation is not enclosed correctly.\n" + INFORMATIVE_EXCEPTION_MESSAGE);
        }
    }

    private static boolean isEdgeRepresentationEnclosedCorrectly(String edgeRepresentation) {
        char openingChar = edgeRepresentation.charAt(0);
        char closingChar = edgeRepresentation.charAt(edgeRepresentation.length() - 1);

        return ((openingChar == '(' && closingChar == ')') || (openingChar == '{' && closingChar == '}'));
    }
}
