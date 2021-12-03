package core.abstractions;

public abstract class AbstractValuedEdge {
    private final int value;

    protected abstract int getEdgeValueFromRepresentation();

    public AbstractEdge(String edgeRepresentation) {
        super(edgeRepresentation);
        value = getEdgeValueFromRepresentation();
    }

    public int getValue() {
        return value;
    }
}
