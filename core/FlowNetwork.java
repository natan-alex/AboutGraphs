package core;

import core.abstractions.AbstractEdge;
import core.abstractions.AbstractGraph;
import core.abstractions.AbstractVertice;
import core.validators.FlowEdgeValidator;
import core.validators.FlowNetworkValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class FlowNetwork extends AbstractGraph {
    private List<FlowEdge> flowEdgesInReversedDirection;
    private final AbstractVertice source;
    private final AbstractVertice sink;

    @Override
    protected void validateGraphRepresentation() {
        FlowNetworkValidator.validateFlowNetworkRepresentation(getRepresentation());
    }

    @Override
    protected void fillEdgeList() {
        Matcher matcher = FlowEdgeValidator.PATTERN_TO_VALIDATE_A_FLOW_EDGE.matcher(getRepresentation());
        List<AbstractEdge> edges = new ArrayList<>();

        while (matcher.find()) {
            edges.add(new FlowEdge(matcher.group()));
        }

        setEdges(edges);
    }

    public FlowNetwork(String graphRepresentation,
        String sourceVerticeRepresentation,
        String sinkVerticeRepresentation) throws IllegalArgumentException {

        super(graphRepresentation);

        source = getVerticeByRepresentation(sourceVerticeRepresentation);
        sink = getVerticeByRepresentation(sinkVerticeRepresentation);
        FlowNetworkValidator.validateSourceAndSinkVertices(getRepresentation(), source, sink);

        fillFlowEdgesReversed();
    }

    public FlowNetwork(String graphRepresentation,
                       AbstractVertice sourceVertice,
                       AbstractVertice sinkVertice) throws IllegalArgumentException {

        super(graphRepresentation);

        source = sourceVertice;
        sink = sinkVertice;
        FlowNetworkValidator.validateSourceAndSinkVertices(getRepresentation(), source, sink);

        fillFlowEdgesReversed();
    }

    private void fillFlowEdgesReversed() {
        flowEdgesInReversedDirection = new ArrayList<>(getNumberOfEdges());
        FlowEdge newFlowEdge;

        for (AbstractEdge edge : getEdges()) {
            newFlowEdge = new FlowEdge(createEdgeRepresentationWithVerticesReversed((FlowEdge) edge));
            flowEdgesInReversedDirection.add(newFlowEdge);
        }
    }

    private String createEdgeRepresentationWithVerticesReversed(FlowEdge edge) {
        StringBuilder newRepresentation = new StringBuilder();
        newRepresentation.append("(");
        newRepresentation.append(edge.getSecondVertice().getRepresentation());
        newRepresentation.append(",");
        newRepresentation.append(edge.getFirstVertice().getRepresentation());
        newRepresentation.append(",");
        newRepresentation.append(edge.getMaximumCapacity());
        newRepresentation.append(")");
        return newRepresentation.toString();
    }

    public FlowEdge getDirectedReversedEdgeWithTheseVertices(AbstractVertice firstVertice, AbstractVertice secondVertice) {
        for (FlowEdge flowEdge : flowEdgesInReversedDirection) {
            if (flowEdge.getFirstVertice().equals(firstVertice) && flowEdge.getSecondVertice().equals(secondVertice)) {
                return flowEdge;
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
