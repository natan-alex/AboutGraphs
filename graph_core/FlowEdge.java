package aboutGraphs.core;

public class FlowEdge extends Edge {
    public int howMuchFlowCanStillPass;
    public int maximumCapacity;

    public FlowEdge(String edgeRepresentation, int maximumCapacity) throws IllegalArgumentException {
        super(edgeRepresentation);
        this.maximumCapacity = maximumCapacity;
        howMuchFlowCanStillPass = 0;
    }

    @Override
    public String toString() {
        return stringRepresentation + ": " + howMuchFlowCanStillPass + ", " + maximumCapacity;
    }
}
