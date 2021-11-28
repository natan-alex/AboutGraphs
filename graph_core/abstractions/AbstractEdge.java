public abstract class AbstractEdge implements IRepresentable {
    private String representation;
    
    public AbstractEdge(String edgeRepresentation) {
        this.representation = edgeRepresentation;
    }

    public abstract AbstractVertice getFirstVertice();
    public abstract AbstractVertice getSecondVertice();

    @Override
    public boolean equals(Object obj) {
        AbstractEdge edge = (AbstractEdge) obj;
        return getRepresentation().compareToIgnoreCase(edge.getRepresentation()) == 0;
    }
}
