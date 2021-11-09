public class Edge {
    public final String stringRepresentation;
    public final Vertice firstVertice;
    public final Vertice secondVertice;
    public final float value;

    public Edge(String edgeRepresentation) throws IllegalArgumentException {
        EdgeValidator.checkIfEdgeRepresentationIsValid(edgeRepresentation);

        stringRepresentation = edgeRepresentation.trim();
        String withoutParenthesisOrCurlyBraces = stringRepresentation.substring(1, stringRepresentation.length() - 1);
        String[] partsOfStringRepresentation = withoutParenthesisOrCurlyBraces.split(",");

        firstVertice = new Vertice(partsOfStringRepresentation[0].trim());
        secondVertice = new Vertice(partsOfStringRepresentation[1].trim());

        if (partsOfStringRepresentation.length == 3) {
            value = Float.parseFloat(partsOfStringRepresentation[2].trim());
        } else {
            value = 0F;
        }
    }

    @Override
    public boolean equals(Object obj) {
        return stringRepresentation.compareTo(((Edge) obj).stringRepresentation) == 0;
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
