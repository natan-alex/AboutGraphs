package core;

import core.abstractions.*;
import core.validators.*;

public class Edge extends AbstractEdge {
    public final int value;

    @Override
    protected void validateEdgeRepresentation() throws IllegalArgumentException {
        EdgeValidator.checkIfEdgeRepresentationIsValid(getRepresentation());
    }

    public Edge(String edgeRepresentation) throws IllegalArgumentException {
        super(edgeRepresentation);
        setRepresentation(removeSpacesFromEdgeRepresentation(edgeRepresentation));
        String[] partsOfStringRepresentation = splitStringRepresentation();

        setFirstVertice(new Vertice(partsOfStringRepresentation[0]));
        setSecondVertice(new Vertice(partsOfStringRepresentation[1]));

        if (partsOfStringRepresentation.length == 3) {
            value = Integer.parseInt(partsOfStringRepresentation[2]);
        } else {
            value = 0;
        }
    }

    private String[] splitStringRepresentation() {
        String withoutParenthesisOrCurlyBraces = getRepresentation().substring(1, getRepresentation().length() - 1);
        return withoutParenthesisOrCurlyBraces.split(",");
    }

    private String removeSpacesFromEdgeRepresentation(String edgeRepresentation) {
        String newRepresentation = edgeRepresentation.trim();
        newRepresentation = newRepresentation.replaceAll("\\s*,\\s*", ",");
        return newRepresentation;
    }

    @Override
    public boolean equals(Object obj) {
        return getRepresentation().compareToIgnoreCase(((Edge) obj).getRepresentation()) == 0;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return getRepresentation();
    }
}
