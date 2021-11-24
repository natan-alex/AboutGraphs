public class FlowEdge extends Edge {
    public int currentFlow;
    public int maximumCapacity;

    public FlowEdge(String edgeRepresentation, int maximumCapacity) throws IllegalArgumentException {
        super(edgeRepresentation);
        this.maximumCapacity = maximumCapacity;
        currentFlow = 0;
    }

    @Override
    public String toString() {
        return stringRepresentation + ": " + currentFlow + ", " + maximumCapacity;
    }
}
