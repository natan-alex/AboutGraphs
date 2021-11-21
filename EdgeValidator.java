import java.util.regex.*;

public class EdgeValidator {
    public static final String EXAMPLE_OF_A_VALID_EDGE_MESSAGE = "Example of a valid edge: \n  (a, b)\n  {hello, world}\n  (foo, bar, 10)";
    public static final String INFORMATIVE_EXCEPTION_MESSAGE = "A valid edge must be enclosed with () if it is part of a directed graph or {} if it is part of an undirected graph."
            + "\n" + EXAMPLE_OF_A_VALID_EDGE_MESSAGE;

    public static final Pattern PATTERN_TO_VALIDATE_AN_EDGE = Pattern
            .compile("[(|{]\\s*\\w+\\s*,\\s*\\w+\\s*(?:,\\s*\\d{1,6}\\s*)?[)|}]");

    public static void checkIfEdgeRepresentationIsValid(String edgeRepresentation) throws IllegalArgumentException {
        checkIfRepresentationMatchesThePattern(edgeRepresentation);
        checkIfRepresentationIsEnclosedCorrectly(edgeRepresentation);
    }

    private static void checkIfRepresentationMatchesThePattern(String edgeRepresentation) {
        Matcher matcher = PATTERN_TO_VALIDATE_AN_EDGE.matcher(edgeRepresentation);

        if (!matcher.matches()) {
            throw new IllegalArgumentException(
                    "Invalid edge " + edgeRepresentation + "\n" + EXAMPLE_OF_A_VALID_EDGE_MESSAGE);
        }
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
