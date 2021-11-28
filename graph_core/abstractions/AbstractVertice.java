public abstract class AbstractVertice implements IRepresentable {
    private String representation;

    public AbstractVertice(String verticeRepresentation) {
        this.representation = verticeRepresentation;
    }

    @Override
    public boolean equals(Object obj) {
        AbstractVertice vertice = (AbstractVertice) obj;
        return getRepresentation().compareToIgnoreCase(vertice.getRepresentation()) == 0;
    }
}
