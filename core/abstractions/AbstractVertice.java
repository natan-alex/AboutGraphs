package core.abstractions;

public abstract class AbstractVertice {
    private final String representation;

    public AbstractVertice(String verticeRepresentation) {
        representation = verticeRepresentation.trim();
    }

    public String getRepresentation() {
        return representation;
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
