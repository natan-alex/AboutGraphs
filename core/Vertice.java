package core;

import core.abstractions.AbstractVertice;

public class Vertice extends AbstractVertice {
    public Vertice(String verticeRepresentation) {
        super(verticeRepresentation);
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return getRepresentation();
    }
}
