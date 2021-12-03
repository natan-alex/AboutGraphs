package core.abstractions;

public abstract class AbstractEdge {
    private final String representation;
    private final AbstractVertice firstVertice;
    private final AbstractVertice secondVertice;

    protected abstract void validateEdgeRepresentation();
    protected abstract AbstractVertice getFirstVerticeFromRepresentation();
    protected abstract AbstractVertice getSecondVerticeFromRepresentation();

    public AbstractEdge(String edgeRepresentation) {
        representation = edgeRepresentation;
        validateEdgeRepresentation();
        firstVertice = getFirstVerticeFromRepresentation();
        secondVertice = getSecondVerticeFromRepresentation();
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
