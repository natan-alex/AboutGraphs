package core.abstractions;

public abstract class AbstractTypedGraph extends AbstractGraph {
    private final GraphTypes type;

    protected abstract void checkIfTypeMatchesTheRepresentation();

    public AbstractTypedGraph(String graphRepresentation, GraphTypes graphType) {
        super(graphRepresentation);
        type = graphType;
        checkIfTypeMatchesTheRepresentation();
    }

    public GraphTypes getType() {
        return type;
    }
}