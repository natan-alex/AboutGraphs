public class FlowEdge extends Edge {
    public int currentCapacity;
    public int maximumCapacity;

    public FlowEdge(String edgeRepresentation, int maximumCapacity) throws IllegalArgumentException {
        super(edgeRepresentation);
        this.maximumCapacity = maximumCapacity;
        currentCapacity = 0;
    }

}
