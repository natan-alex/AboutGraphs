package core.abstractions;

public abstract class AbstractFlowEdge extends AbstractEdge {
    private final int maximumCapacity;
    private int currentFlowPassing;

    public AbstractFlowEdge(String edgeRepresentation) {
        super(edgeRepresentation);
        maximumCapacity = getMaximumCapacityFromRepresentation();
        currentFlowPassing = 0;
    }

    protected int getMaximumCapacityFromRepresentation() {
        int indexOfLastComma = getRepresentation().lastIndexOf(',');
        int indexOfClosingParenthesis = getRepresentation().lastIndexOf(')');
        return Integer.parseInt(getRepresentation().substring(indexOfLastComma + 1, indexOfClosingParenthesis).trim());
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
