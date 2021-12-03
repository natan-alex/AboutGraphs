package core;

import core.abstractions.AbstractFlowEdge;
import core.validators.FlowEdgeValidator;

public class FlowEdge extends AbstractFlowEdge {
    public FlowEdge(String edgeRepresentation) throws IllegalArgumentException {
        super(edgeRepresentation);
    }

    @Override
    public String toString() {
        return getRepresentation() + ": " + getCurrentFlowPassing() + ", " + getMaximumCapacity();
    }

    @Override
    protected void validateEdgeRepresentation() throws IllegalArgumentException {
        FlowEdgeValidator.checkIfFlowEdgeRepresentationIsValid(getRepresentation());
    }
}
