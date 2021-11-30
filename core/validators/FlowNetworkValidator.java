package core.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import core.GraphTypes;
import core.abstractions.AbstractVertice;

public class FlowNetworkValidator {
    private static String representation;
    private static AbstractVertice sourceVertice;
    private static AbstractVertice sinkVertice;

    public static final String EXAMPLES_OF_VALID_FLOW_NETWORKS_MESSAGE = "Examples of valid flow networks:"
            + "\n  { (foo, bar, 10), (john, doe, 5) }"
            + "\n  { (foo,bar,10), (john,doe,3) }"
            + "\n  { (A, B, 1), (A, C, 2), (B, C, 3) }"
            + "\n  { (1, 2, 4), (1, 2, 5) }";

    public static final String INFORMATIVE_VALID_GRAPH_EXCEPTION_MESSAGE = "A valid flow network must be enclosed with {}."
            + "\nEach edge must be enclosed with () and have the following structure:"
            + "\n  (first_vertice, second_vertice, maximum_capacity)"
            + "\n" + EXAMPLES_OF_VALID_FLOW_NETWORKS_MESSAGE;

    public static final Pattern PATTERN_TO_VALIDATE_A_FLOW_NETWORK = Pattern.compile(
            "^\\s*\\{\\s*(?:\\(\\s*\\w+\\s*,\\s*\\w+\\s*,\\s*\\d+\\s*\\)\\s*,\\s*)*\\(\\s*\\w+\\s*,\\s*\\w+\\s*,\\s*\\d+\\s*\\)\\s*\\}\\s*$",
            Pattern.MULTILINE);

    public static void validateFlowNetworkRepresentation(String flowNetworkRepresentation) throws IllegalArgumentException {
        Matcher matcher = PATTERN_TO_VALIDATE_A_FLOW_NETWORK.matcher(flowNetworkRepresentation);

        if (!matcher.matches()) {
            throw new IllegalArgumentException(INFORMATIVE_VALID_GRAPH_EXCEPTION_MESSAGE);
        }
    }

    public static void validateSourceAndSinkVertices(
            String flowNetworkRepresentation, AbstractVertice source, AbstractVertice sink
    ) throws IllegalArgumentException {
        representation = flowNetworkRepresentation;
        sourceVertice = source;
        sinkVertice = sink;
        throwExceptionIfSourceOrSinkIsNull();
        throwExceptionIfSourceAndSinkAreEquals();
        checkIfSourceAndSinkAreValid();
    }

    private static void throwExceptionIfSourceOrSinkIsNull()
            throws IllegalArgumentException {
        if (sourceVertice == null) {
            throw new IllegalArgumentException("The source vertice cannot be null");
        }

        if (sinkVertice == null) {
            throw new IllegalArgumentException("The sink vertice cannot be null");
        }
    }

    private static void throwExceptionIfSourceAndSinkAreEquals() throws IllegalArgumentException {
        if (sourceVertice.equals(sinkVertice)) {
            throw new IllegalArgumentException("Source and sink vertices cannot be the same vertice.");
        }
    }

    private static void checkIfSourceAndSinkAreValid() throws IllegalArgumentException {
        // check is there is a substring where source is the vertice where edge ends
        if (Pattern.compile(",\\s*" + sourceVertice.getRepresentation()).matcher(representation).find()) {
            throw new IllegalArgumentException("The source can not have edges coming to it.");
        }

        // check is there is a substring where sink is the vertice where edge starts
        if (Pattern.compile("\\(" + sinkVertice.getRepresentation()).matcher(representation).find()) {
            throw new IllegalArgumentException("The sink can not have edges leaving from it.");
        }
    }
}
