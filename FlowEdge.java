public class FlowEdge extends Edge {
    public int howMuchFlowCanPass;
    public int maximumCapacity;

    public FlowEdge(String edgeRepresentation, int maximumCapacity) throws IllegalArgumentException {
        super(edgeRepresentation);
        this.maximumCapacity = maximumCapacity;
        howMuchFlowCanPass = 0;
    }

    @Override
    public String toString() {
        return stringRepresentation + ": " + howMuchFlowCanPass + ", " + maximumCapacity;
    }
}
