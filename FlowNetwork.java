import java.util.ArrayList;
import java.util.List;

public class FlowNetwork extends Graph {
    public final List<FlowEdge> flowEdges;
    public final List<FlowEdge> flowEdgesInReversedDirection;

    public FlowNetwork(String graphRepresentation) throws IllegalArgumentException {
        super(graphRepresentation);
        flowEdges = new ArrayList<>(numberOfEdges);
        flowEdgesInReversedDirection = new ArrayList<>(numberOfEdges);

        for (Edge edge : edges) {
            flowEdges.add(new FlowEdge(edge.stringRepresentation, edge.value));
            flowEdgesInReversedDirection.add(new FlowEdge(createEdgeRepresentationWithVerticesReversed(edge), edge.value));
        }
    }

    private String createEdgeRepresentationWithVerticesReversed(Edge edge) {
        StringBuffer newRepresentation = new StringBuffer();
        newRepresentation.append("(");
        newRepresentation.append(edge.secondVertice.name);
        newRepresentation.append(",");
        newRepresentation.append(edge.firstVertice.name);
        newRepresentation.append(",");
        newRepresentation.append(edge.value);
        newRepresentation.append(")");
        return newRepresentation.toString();
    }

    @Override
    public FlowEdge getDirectedEdgeWithThisVertices(Vertice firstVertice, Vertice secondVertice) {
        for (FlowEdge flowEdge : flowEdges) {
            if (flowEdge.firstVertice.equals(firstVertice) && flowEdge.secondVertice.equals(secondVertice)) {
                return flowEdge;
            }
        }

        return null;
    }

}
