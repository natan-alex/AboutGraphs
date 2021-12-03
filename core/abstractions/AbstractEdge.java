package core.abstractions;

import core.Vertice;

public abstract class AbstractEdge {
    private final String representation;
    private final AbstractVertice firstVertice;
    private final AbstractVertice secondVertice;

    protected abstract void validateEdgeRepresentation();

    public AbstractEdge(String edgeRepresentation) {
        representation = edgeRepresentation.trim();
        validateEdgeRepresentation();
        firstVertice = getFirstVerticeFromRepresentation();
        secondVertice = getSecondVerticeFromRepresentation();
    }

    protected AbstractVertice getFirstVerticeFromRepresentation() {
        int indexOfOpeningParenthesis = representation.indexOf('(');
        int indexOfFirstComma = representation.indexOf(',', indexOfOpeningParenthesis + 1);
        return new Vertice(representation.substring(indexOfOpeningParenthesis + 1, indexOfFirstComma).trim());
    }

    protected AbstractVertice getSecondVerticeFromRepresentation() {
        int indexOfFirstComma = representation.indexOf(',');
        int indexOfSecondComma = representation.indexOf(',', indexOfFirstComma + 1);
        return new Vertice(representation.substring(indexOfFirstComma + 1, indexOfSecondComma).trim());
    }

    public String getRepresentation() {
        return representation;
    }

    public AbstractVertice getFirstVertice() {
        return firstVertice;
    }

    public AbstractVertice getSecondVertice() {
        return secondVertice;
    }

    @Override
    public boolean equals(Object obj) {
        AbstractEdge edge = (AbstractEdge) obj;
        return getRepresentation().compareToIgnoreCase(edge.getRepresentation()) == 0;
    }

    @Override
    public int hashCode() {
        return 0;
    }

}
