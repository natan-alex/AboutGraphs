package core;

import core.abstractions.AbstractEdge;
import core.validators.EdgeValidator;
import core.validators.FlowEdgeValidator;

public class FlowEdge extends AbstractEdge {
    private int currentFlowPassing;
    private final int maximumCapacity;

    public FlowEdge(String edgeRepresentation) throws IllegalArgumentException {
        super(edgeRepresentation);

        setRepresentation(removeSpacesFromEdgeRepresentation(edgeRepresentation));
        String[] partsOfStringRepresentation = splitStringRepresentation();

        setFirstVertice(new Vertice(partsOfStringRepresentation[0]));
        setSecondVertice(new Vertice(partsOfStringRepresentation[1]));
        maximumCapacity = Integer.parseInt(partsOfStringRepresentation[2]);
        currentFlowPassing = 0;
    }

    private String removeSpacesFromEdgeRepresentation(String edgeRepresentation) {
        String newRepresentation = edgeRepresentation.trim();
        newRepresentation = newRepresentation.replaceAll("\\s*,\\s*", ",");
        return newRepresentation;
    }

    private String[] splitStringRepresentation() {
        String withoutParenthesis = getRepresentation().substring(1, getRepresentation().length() - 1);
        return withoutParenthesis.split(",");
    }

    @Override
    public String toString() {
        return getRepresentation() + ": " + currentFlowPassing + ", " + maximumCapacity;
    }

    @Override
    protected void validateEdgeRepresentation() {
        FlowEdgeValidator.checkIfFlowEdgeRepresentationIsValid(getRepresentation());
    }

    public int getCurrentFlowPassing() {
        return currentFlowPassing;
    }

    public void setCurrentFlowPassing(int currentFlowPassing) {
        if (currentFlowPassing >= 0)
            this.currentFlowPassing = currentFlowPassing;
    }

    public int getMaximumCapacity() {
        return maximumCapacity;
    }
}
