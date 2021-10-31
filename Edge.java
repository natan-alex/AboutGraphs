import java.util.regex.*;

public class Edge {
    public final String stringRepresentation;
    public final Vertice firstVertice;
    public final Vertice secondVertice;
    public final int value;
    public final boolean isDirected;
    public final boolean isPondered;

    public static final Pattern PATTERN_TO_VALIDATE_AN_EDGE = Pattern
            .compile("[(|{]\\s*\\w+\\s*,(\\s*\\w+\\s*,)?\\s*\\w+\\s*[)|}]");

    public enum EdgeClassifications {
        TREE("Tree"), CROSSING("Crossing"), RETURN("Return"), ADVANCE("Advance");

        String name;

        private EdgeClassifications(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public Edge(String edgeRepresentation) throws IllegalArgumentException {
        checkIfRepresentationIsValid(edgeRepresentation);

        stringRepresentation = edgeRepresentation.trim();
        String[] partsOfStringRepresentation = stringRepresentation.substring(1, stringRepresentation.length() - 1)
                .split(",");

        if (partsOfStringRepresentation.length == 3) {
            isPondered = true;
            value = Integer.parseInt(partsOfStringRepresentation[2].trim());
        } else {
            isPondered = false;
            value = Integer.MIN_VALUE;
        }

        firstVertice = new Vertice(partsOfStringRepresentation[0].trim());
        secondVertice = new Vertice(partsOfStringRepresentation[1].trim());

        if (stringRepresentation.charAt(0) == '(')
            isDirected = true;
        else
            isDirected = false;
    }

    private void checkIfRepresentationIsValid(String edgeRepresentation) throws IllegalArgumentException {
        Matcher matcher = PATTERN_TO_VALIDATE_AN_EDGE.matcher(edgeRepresentation);

        if (!matcher.matches()) {
            throw new IllegalArgumentException(
                    "Invalid edge " + edgeRepresentation + "\nExample of a valid edge: (a, b) or {hello, world}");
        }

        if (!isRepresentationEnclosedCorrectly(edgeRepresentation)) {
            throw new IllegalArgumentException("Invalid edge " + edgeRepresentation
                    + ": the edge representation is not enclosed correctly."
                    + "\nA valid edge must be enclosed with () if it is part of a directed graph or {} if it is part of an undirected graph."
                    + "\nExample of a valid edge: (a, b) or {hello, world}");
        }
    }

    private boolean isRepresentationEnclosedCorrectly(String edgeRepresentation) {
        char openingChar = edgeRepresentation.charAt(0);
        char closingChar = edgeRepresentation.charAt(edgeRepresentation.length() - 1);

        return ((openingChar == '(' && closingChar == ')') || (openingChar == '{' && closingChar == '}'));
    }

    @Override
    public boolean equals(Object obj) {
        return stringRepresentation.compareTo(((Edge) obj).stringRepresentation) == 0;
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
