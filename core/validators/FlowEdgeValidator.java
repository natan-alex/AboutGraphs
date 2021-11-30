package core.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FlowEdgeValidator {
    public static final String EXAMPLES_OF_VALID_FLOW_EDGES_MESSAGE = "Examples of valid flow edges:"
            + "\n  (foo, bar, 10)"
            + "\n  (john,doe,1)"
            + "\n  (1,2,3)";

    public static final String INFORMATIVE_EXCEPTION_MESSAGE = "A valid flow edge must be enclosed with ()."
            + "\n" + EXAMPLES_OF_VALID_FLOW_EDGES_MESSAGE;

    public static final Pattern PATTERN_TO_VALIDATE_A_FLOW_EDGE = Pattern
            .compile("\\(\\s*\\w+\\s*,\\s*\\w+\\s*,\\s*\\d{1,6}\\s*\\)");

    public static void checkIfFlowEdgeRepresentationIsValid(String flowEdgeRepresentation) throws IllegalArgumentException {
        checkIfRepresentationMatchesThePattern(flowEdgeRepresentation);
    }

    private static void checkIfRepresentationMatchesThePattern(String flowEdgeRepresentation) {
        Matcher matcher = PATTERN_TO_VALIDATE_A_FLOW_EDGE.matcher(flowEdgeRepresentation);

        if (!matcher.matches()) {
            throwExceptionForInvalidFlowEdgeRepresentation(flowEdgeRepresentation);
        }
    }

    private static void throwExceptionForInvalidFlowEdgeRepresentation(String flowEdgeRepresentation) {
        throw new IllegalArgumentException(
                "Invalid flow edge " + flowEdgeRepresentation + "\n" + EXAMPLES_OF_VALID_FLOW_EDGES_MESSAGE);
    }
}
