package aboutGraphs.core;

public class FlowEdge extends Edge {
    public int currentFlowPassing;
    public int maximumCapacity;

    public FlowEdge(String edgeRepresentation, int maximumCapacity) throws IllegalArgumentException {
        super(edgeRepresentation);
        this.maximumCapacity = maximumCapacity;
        currentFlowPassing = 0;
    }

    @Override
    public String toString() {
        return stringRepresentation + ": " + currentFlowPassing + ", " + maximumCapacity;
    }
}
