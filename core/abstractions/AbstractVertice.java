public abstract class AbstractVertice {
    public abstract String getRepresentation();

    @Override
    public boolean equals(Object obj) {
        AbstractVertice vertice = (AbstractVertice) obj;
        return getRepresentation().compareToIgnoreCase(vertice.getRepresentation()) == 0;
    }
}
