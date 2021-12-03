package core;

import core.abstractions.*;
import core.validators.*;

public class ValuedEdge extends AbstractValuedEdge {
    @Override
    protected void validateEdgeRepresentation() throws IllegalArgumentException {
        EdgeValidator.checkIfEdgeRepresentationIsValid(getRepresentation());
    }

    public ValuedEdge(String edgeRepresentation) throws IllegalArgumentException {
        super(edgeRepresentation);
    }

    @Override
    public String toString() {
        return getRepresentation();
    }

}
