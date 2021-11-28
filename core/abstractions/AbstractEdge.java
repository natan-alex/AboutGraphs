public abstract class AbstractEdge {
    public abstract AbstractVertice getFirstVertice();
    public abstract AbstractVertice getSecondVertice();
    public abstract String getRepresentation();

    @Override
    public boolean equals(Object obj) {
        AbstractEdge edge = (AbstractEdge) obj;
        return getRepresentation().compareToIgnoreCase(edge.getRepresentation()) == 0;
    }
}
