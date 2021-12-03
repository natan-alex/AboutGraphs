package core;

import core.abstractions.*;
import core.validators.*;

public class ValuedEdge extends AbstractValuedEdge {
    public ValuedEdge(String edgeRepresentation) throws IllegalArgumentException {
        super(edgeRepresentation);
    }

    @Override
    protected void validateEdgeRepresentation() throws IllegalArgumentException {
        EdgeValidator.checkIfEdgeRepresentationIsValid(getRepresentation());
    }

    @Override
    protected AbstractVertice createVerticeFromRepresentation(String representation) {
        return new Vertice(representation);
    }

    @Override
    public String toString() {
        return getRepresentation();
    }

}
