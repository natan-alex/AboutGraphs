package AboutGraphs.core;

import java.util.ArrayList;
import java.util.List;

public class FlowNetwork extends Graph {
    public final List<FlowEdge> flowEdges;
    public final List<FlowEdge> flowEdgesInReversedDirection;

    public FlowNetwork(String graphRepresentation) throws IllegalArgumentException {
        // on super call the edge list and vertice set will be filled
        super(graphRepresentation);

        flowEdges = new ArrayList<>(numberOfEdges);
        flowEdgesInReversedDirection = new ArrayList<>(numberOfEdges);

        // use the edge values as their maximum capacity
        for (Edge edge : edges) {
            flowEdges.add(new FlowEdge(edge.stringRepresentation, edge.value));
            flowEdgesInReversedDirection
                    .add(new FlowEdge(createEdgeRepresentationWithVerticesReversed(edge), edge.value));
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

    public FlowEdge getDirectedEdgeInReversedDirectionWithThisVertices(Vertice firstVertice, Vertice secondVertice) {
        for (FlowEdge flowEdge : flowEdgesInReversedDirection) {
            if (flowEdge.firstVertice.equals(firstVertice) && flowEdge.secondVertice.equals(secondVertice)) {
                return flowEdge;
            }
        }

        return null;
    }
}
