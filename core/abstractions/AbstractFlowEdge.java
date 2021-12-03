package core.abstractions;

public abstract class AbstractFlowEdge extends AbstractEdge {
    private final int maximumCapacity;
    private int currentFlowPassing;

    protected abstract int getMaximumCapacityFromRepresentation();

    public AbstractFlowEdge(String edgeRepresentation) {
        super(edgeRepresentation);
        maximumCapacity = getMaximumCapacityFromRepresentation();
        currentFlowPassing = 0;
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
