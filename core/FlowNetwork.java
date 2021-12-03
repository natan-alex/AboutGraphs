package core;

import core.abstractions.AbstractFlowEdge;
import core.abstractions.AbstractFlowNetwork;
import core.abstractions.AbstractVertice;
import core.validators.FlowEdgeValidator;
import core.validators.FlowNetworkValidator;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;

public class FlowNetwork extends AbstractFlowNetwork {
    public FlowNetwork(String graphRepresentation,
        String sourceVerticeRepresentation,
        String sinkVerticeRepresentation) throws IllegalArgumentException {
        super(graphRepresentation, sourceVerticeRepresentation, sinkVerticeRepresentation);
    }

    public FlowNetwork(String graphRepresentation,
                       AbstractVertice sourceVertice,
                       AbstractVertice sinkVertice) throws IllegalArgumentException {
        super(graphRepresentation, sourceVertice, sinkVertice);
    }

    @Override
    protected void validateGraphRepresentation() {
        FlowNetworkValidator.validateFlowNetworkRepresentation(getRepresentation());
    }

    @Override
    protected void validateSourceAndSinkVertices() {
        FlowNetworkValidator.validateSourceAndSinkVertices(getRepresentation(), getSourceVertice(), getSinkVertice());
    }

    @Override
    protected AbstractFlowEdge[] getEdgesFromRepresentation() {
        Matcher matcher = FlowEdgeValidator.PATTERN_TO_VALIDATE_A_FLOW_EDGE.matcher(getRepresentation());
        List<AbstractFlowEdge> edges = new ArrayList<>();

        while (matcher.find()) {
            edges.add(new FlowEdge(matcher.group()));
        }

        AbstractFlowEdge[] edgesArray = new AbstractFlowEdge[edges.size()];
        return edges.toArray(edgesArray);
    }

    @Override
    protected AbstractVertice[] getVerticesFromEdgesArray() {
        Set<AbstractVertice> vertices = new LinkedHashSet<>();

        for (AbstractFlowEdge edge : getEdges()) {
            vertices.add(edge.getFirstVertice());
            vertices.add(edge.getSecondVertice());
        }

        AbstractVertice[] verticesArray = new AbstractVertice[vertices.size()];
        return vertices.toArray(verticesArray);
    }

    @Override
    protected AbstractFlowEdge[] getReversedEdgesFromEdgesArray() {
        List<FlowEdge> reversedEdges = new ArrayList<>(getNumberOfEdges());
        FlowEdge newFlowEdge;

        for (AbstractFlowEdge edge : getEdges()) {
            newFlowEdge = new FlowEdge(createEdgeRepresentationWithVerticesReversed(edge));
            reversedEdges.add(newFlowEdge);
        }

        AbstractFlowEdge[] reversedEdgesArray = new AbstractFlowEdge[reversedEdges.size()];
        return reversedEdges.toArray(reversedEdgesArray);
    }

    private String createEdgeRepresentationWithVerticesReversed(AbstractFlowEdge edge) {
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

    public AbstractFlowEdge getReversedEdge(AbstractVertice firstVertice, AbstractVertice secondVertice) {
        for (AbstractFlowEdge flowEdge : getReversedEdges()) {
            if (flowEdge.getFirstVertice().equals(firstVertice) && flowEdge.getSecondVertice().equals(secondVertice)) {
                return flowEdge;
            }
        }

        return null;
    }
}
