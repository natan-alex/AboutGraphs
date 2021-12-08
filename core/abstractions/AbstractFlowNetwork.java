package core.abstractions;

import core.abstractions.AbstractFlowEdge;
import core.abstractions.AbstractGraph;
import core.abstractions.AbstractVertice;

public abstract class AbstractFlowNetwork extends AbstractGraph {
    private final AbstractFlowEdge[] reversedEdges;
    private final AbstractVertice source;
    private final AbstractVertice sink;

    protected abstract AbstractFlowEdge[] getReversedEdgesFromEdgesArray();
    protected abstract AbstractFlowEdge[] getEdgesFromRepresentation();
    protected abstract void validateSourceAndSinkVertices();

    public AbstractFlowNetwork(String graphRepresentation, AbstractVertice sourceVertice, AbstractVertice sinkVertice) {
        super(graphRepresentation);
        source = sourceVertice;
        sink = sinkVertice;
        validateSourceAndSinkVertices();
        reversedEdges = getReversedEdgesFromEdgesArray();
    }

    public AbstractFlowNetwork(String graphRepresentation, String sourceVerticeRepresentation, String sinkVerticeRepresentation) {
        super(graphRepresentation);
        source = getVerticeByRepresentation(sourceVerticeRepresentation);
        sink = getVerticeByRepresentation(sinkVerticeRepresentation);
        validateSourceAndSinkVertices();
        reversedEdges = getReversedEdgesFromEdgesArray();
    }

    public AbstractFlowEdge getReversedEdge(AbstractVertice firstVertice, AbstractVertice secondVertice) {
        for (AbstractFlowEdge edge : reversedEdges) {
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

    @Override
    public AbstractFlowEdge[] getEdges() {
        return (AbstractFlowEdge[]) super.getEdges();
    }

    @Override
    public AbstractFlowEdge getEdge(AbstractVertice firstVertice, AbstractVertice secondVertice) {
        return (AbstractFlowEdge) super.getEdge(firstVertice, secondVertice);
    }

    public AbstractFlowEdge[] getReversedEdges() {
        return reversedEdges;
    }
}
