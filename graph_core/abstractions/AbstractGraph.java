import java.util.List;

public abstract class AbstractGraph implements IRepresentable {
    private String representation;
    private List<AbstractEdge> edges;
    private List<AbstractVertice> vertices;

    public AbstractGraph(String graphRepresentation) {
        this.representation = graphRepresentation;
    }
}
