public class Edge {
    public final String stringRepresentation;
    public final Vertice firstVertice;
    public final Vertice secondVertice;
    public final float value;
    public final boolean isDirected;
    public final boolean isPondered;

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
        EdgeValidator.checkIfEdgeRepresentationIsValid(edgeRepresentation);

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
