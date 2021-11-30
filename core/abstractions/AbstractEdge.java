package core.abstractions;

public abstract class AbstractEdge {
    private String representation;
    private AbstractVertice firstVertice;
    private AbstractVertice secondVertice;

    protected abstract void validateEdgeRepresentation();

    public AbstractEdge(String edgeRepresentation) {
        representation = edgeRepresentation;
        validateEdgeRepresentation();
    }

    public String getRepresentation() {
        return representation;
    }

    protected void setRepresentation(String newRepresentation) {
        representation = newRepresentation;
    }

    public AbstractVertice getFirstVertice() {
        return firstVertice;
    }

    protected void setFirstVertice(AbstractVertice newVertice) {
        firstVertice = newVertice;
    }

    public AbstractVertice getSecondVertice() {
        return secondVertice;
    }

    protected void setSecondVertice(AbstractVertice newVertice) {
        secondVertice = newVertice;
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
