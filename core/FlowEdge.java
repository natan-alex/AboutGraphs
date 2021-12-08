package core;

import core.abstractions.AbstractFlowEdge;
import core.abstractions.AbstractVertice;
import core.validators.FlowEdgeValidator;

public class FlowEdge extends AbstractFlowEdge {
    public FlowEdge(String edgeRepresentation) throws IllegalArgumentException {
        super(edgeRepresentation);
    }

    @Override
    protected void validateEdgeRepresentation() throws IllegalArgumentException {
        FlowEdgeValidator.checkIfFlowEdgeRepresentationIsValid(getRepresentation());
    }

    @Override
    protected AbstractVertice createVerticeFromRepresentation(String representation) {
        return new Vertice(representation);
    }

    @Override
    public String toString() {
        return getRepresentation() + ": " + getCurrentFlowPassing() + ", " + getMaximumCapacity();
    }
}
