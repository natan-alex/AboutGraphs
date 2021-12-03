package core.abstractions;

public abstract class AbstractEdge {
    private final String representation;
    private final AbstractVertice firstVertice;
    private final AbstractVertice secondVertice;

    protected abstract void validateEdgeRepresentation();
    protected abstract AbstractVertice createVerticeFromRepresentation(String representation);

    public AbstractEdge(String edgeRepresentation) {
        representation = edgeRepresentation.trim();
        validateEdgeRepresentation();
        firstVertice = createVerticeFromRepresentation(getFirstVerticeRepresentation());
        secondVertice = createVerticeFromRepresentation(getSecondVerticeRepresentation());
    }

    protected String getFirstVerticeRepresentation() {
        int indexOfOpeningParenthesis = representation.indexOf('(');
        int indexOfFirstComma = representation.indexOf(',', indexOfOpeningParenthesis + 1);
        return representation.substring(indexOfOpeningParenthesis + 1, indexOfFirstComma).trim();
    }

    protected String getSecondVerticeRepresentation() {
        int indexOfFirstComma = representation.indexOf(',');
        int indexOfSecondComma = representation.indexOf(',', indexOfFirstComma + 1);
        return representation.substring(indexOfFirstComma + 1, indexOfSecondComma).trim();
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
