import java.util.Optional;
import java.util.regex.*;

public class Edge {
    private String stringRepresentation;
    private String firstVertice;
    private String secondVertice;
    private int value;
    private boolean isDirected;
    private boolean isPondered;

    public static final Pattern PATTERN_TO_VALIDATE_AN_EDGE = Pattern
            .compile("[(|{]\\s*\\w+\\s*,(\\s*\\w+\\s*,)?\\s*\\w+\\s*[)|}]");

    public boolean isDirected() {
        return isDirected;
    }

    public boolean isPondered() {
        return isPondered;
    }

    public String getStringRepresentation() {
        return stringRepresentation;
    }

    public int getValue() {
        return value;
    }

    public String getFirstVertice() {
        return firstVertice;
    }

    public String getSecondVertice() {
        return secondVertice;
    }

    public static Optional<Edge> fromString(String edgeRepresentation) {
        Matcher matcher = PATTERN_TO_VALIDATE_AN_EDGE.matcher(edgeRepresentation);
        Optional<Edge> result = Optional.empty();

        if (!matcher.matches()) {
            return result;
        }

        Edge edge = new Edge();
        edge.stringRepresentation = edgeRepresentation.trim();

        if (!edge.isEdgeEnclosedCorrectly()) {
            return result;
        }

        edge.fillPropertiesOfTheEdge();
        result = Optional.of(edge);
        return result;
    }

    private boolean isEdgeEnclosedCorrectly() {
        char openingChar = stringRepresentation.charAt(0);
        char closingChar = stringRepresentation.charAt(stringRepresentation.length() - 1);

        return ((openingChar == '(' && closingChar == ')') || (openingChar == '{' && closingChar == '}'));
    }

    private void fillPropertiesOfTheEdge() {
        String[] partsOfStringRepresentation = stringRepresentation.substring(1, stringRepresentation.length() - 1)
                .split(",");

        if (partsOfStringRepresentation.length == 3) {
            isPondered = true;
            value = Integer.parseInt(partsOfStringRepresentation[2].trim());
        } else {
            isPondered = false;
        }

        firstVertice = partsOfStringRepresentation[0].trim();
        secondVertice = partsOfStringRepresentation[1].trim();

        if (stringRepresentation.charAt(0) == '(')
            isDirected = true;
        else
            isDirected = false;
    }
}
