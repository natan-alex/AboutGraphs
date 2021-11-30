package core.abstractions;

public abstract class AbstractVertice {
    private String representation;

    public AbstractVertice(String verticeRepresentation) {
        representation = verticeRepresentation;
    }

    public String getRepresentation() {
        return representation;
    }

    protected void setRepresentation(String newRepresentation) {
        representation = newRepresentation;
    }

    @Override
    public boolean equals(Object obj) {
        AbstractVertice vertice = (AbstractVertice) obj;
        return representation.compareToIgnoreCase(vertice.getRepresentation()) == 0;
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
