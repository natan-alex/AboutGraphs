package aboutGraphs.core;

public class Edge {
    public final String stringRepresentation;
    public final Vertice firstVertice;
    public final Vertice secondVertice;
    public final int value;

    public Edge(String edgeRepresentation) throws IllegalArgumentException {
        EdgeValidator.checkIfEdgeRepresentationIsValid(edgeRepresentation);

        stringRepresentation = removeSpacesFromEdgeRepresentation(edgeRepresentation);
        String[] partsOfStringRepresentation = splitStringRepresentation();

        firstVertice = new Vertice(partsOfStringRepresentation[0]);
        secondVertice = new Vertice(partsOfStringRepresentation[1]);

        if (partsOfStringRepresentation.length == 3) {
            value = Integer.parseInt(partsOfStringRepresentation[2]);
        } else {
            value = 0;
        }
    }

    private String[] splitStringRepresentation() {
        String withoutParenthesisOrCurlyBraces = stringRepresentation.substring(1, stringRepresentation.length() - 1);
        String[] partsOfStringRepresentation = withoutParenthesisOrCurlyBraces.split(",");
        return partsOfStringRepresentation;
    }

    private String removeSpacesFromEdgeRepresentation(String edgeRepresentation) {
        edgeRepresentation = edgeRepresentation.trim();
        edgeRepresentation = edgeRepresentation.replaceAll("\\s*,\\s*", ",");
        return edgeRepresentation;
    }

    @Override
    public boolean equals(Object obj) {
        return stringRepresentation.compareToIgnoreCase(((Edge) obj).stringRepresentation) == 0;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return stringRepresentation;
    }
}
