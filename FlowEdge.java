public class FlowEdge extends Edge {
    public int currentCapacity;
    public int currentCapacityInReversedDirection;
    public int maximumCapacity;

    public FlowEdge(String edgeRepresentation, int maximumCapacity) throws IllegalArgumentException {
        super(edgeRepresentation);
        this.maximumCapacity = maximumCapacity;
        currentCapacity = 0;
        currentCapacityInReversedDirection = 0;
    }

    @Override
    public String toString() {
        return stringRepresentation + ": " + "+" + currentCapacity + ", -" + currentCapacityInReversedDirection + ", "
                + maximumCapacity;
    }
}
