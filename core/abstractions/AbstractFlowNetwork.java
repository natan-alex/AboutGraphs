package core.abstractions;

import core.abstractions.AbstractFlowEdge;
import core.abstractions.AbstractGraph;
import core.abstractions.AbstractVertice;

public abstract class AbstractFlowNetwork extends AbstractGraph {
    private final AbstractFlowEdge[] edges;
    private final AbstractFlowEdge[] reversedEdges;
    private final AbstractVertice source;
    private final AbstractVertice sink;

    protected abstract AbstractFlowEdge[] getReversedEdgesFromEdgesArray();
    protected abstract void validateSourceAndSinkVertices();

    public AbstractFlowNetwork(String graphRepresentation, AbstractVertice sourceVertice, AbstractVertice sinkVertice) {
        super(graphRepresentation);
        source = sourceVertice;
        sink = sinkVertice;
        validateSourceAndSinkVertices();
        reversedEdges = getReversedEdgesFromEdgesArray();
    }

    public AbstractFlowNetwork(String graphRepresentation, String sourceVerticeRepresentation, AbstractVertice sinkVerticeRepresentation) {
        super(graphRepresentation);
        source = getVerticeByRepresentation(sourceVerticeRepresentation);
        sink = getVerticeByRepresentation(sinkVerticeRepresentation);
        validateSourceAndSinkVertices();
        fillReversedEdgesArray();
    }

    public FlowEdge getReversedEdge(AbstractVertice firstVertice, AbstractVertice secondVertice) {
        for (AbstractEdge edge : reversedEdges) {
            if (edge.getFirstVertice().equals(firstVertice) && edge.getSecondVertice().equals(secondVertice)) {
                return edge;
            }
        }

        return null;
    }

    public AbstractVertice getSourceVertice() {
        return source;
    }

    public AbstractVertice getSinkVertice() {
        return sink;
    }
}
