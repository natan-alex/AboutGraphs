package fordFulkerson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import core.abstractions.AbstractFlowEdge;
import core.abstractions.AbstractFlowNetwork;
import core.abstractions.AbstractVertice;
import searches.DeepFirstSearch;
import core.*;
import representations.SuccessorAdjacencyList;

public class FordFulkerson {
    private final AbstractFlowNetwork relatedFlowNetwork;
    private AbstractFlowNetwork residualGraphNetwork;
    private List<List<AbstractVertice>> disjointPaths;
    private DeepFirstSearch deepFirstSearch;
    private List<AbstractFlowEdge> flowEdgesInThePath;
    private List<AbstractFlowEdge> flowEdgesInReversedDirectionInThePath;
    private int minimumCapacityBetweenEdges;

    public FordFulkerson(AbstractFlowNetwork flowNetwork) {
        relatedFlowNetwork = flowNetwork;
    }

    public List<List<AbstractVertice>> computeMaximumFlowAndGetDisjointPaths() {
        initializeNecessaryFieldsToGetDisjointPaths();
        List<AbstractVertice> pathFound = deepFirstSearch.getPathBetweenVertices(
                relatedFlowNetwork.getSourceVertice(), relatedFlowNetwork.getSinkVertice());

        while (pathFound != null && !pathFound.isEmpty()) {
            flowEdgesInThePath = getFlowEdgesInThePath(pathFound);
            flowEdgesInReversedDirectionInThePath = getCorrespondingReversedEdges(
                    flowEdgesInThePath);

            minimumCapacityBetweenEdges = getMinimumCapacityInFlowEdgeList(flowEdgesInThePath);

            addFlowToFlowEdges();

            disjointPaths.add(pathFound);
            removeFromResidualGraphTheEdgesInThePathThatCannotReceiveMoreFlow(flowEdgesInThePath);

            pathFound = initializeDFSAndGetNextPath();
        }

        return disjointPaths;
    }

    private void initializeNecessaryFieldsToGetDisjointPaths() {
        residualGraphNetwork = new FlowNetwork(relatedFlowNetwork.getRepresentation(),
                relatedFlowNetwork.getSourceVertice(), relatedFlowNetwork.getSinkVertice());
        disjointPaths = new ArrayList<>();
        deepFirstSearch = new DeepFirstSearch(residualGraphNetwork);
    }

    private List<AbstractVertice> initializeDFSAndGetNextPath() {
        deepFirstSearch = new DeepFirstSearch(residualGraphNetwork,
                new SuccessorAdjacencyList(residualGraphNetwork));
        return deepFirstSearch.getPathBetweenVertices(
                relatedFlowNetwork.getSourceVertice(), relatedFlowNetwork.getSinkVertice());
    }

    private void addFlowToFlowEdges() {
        addFlowToFlowEdgesInTheList(minimumCapacityBetweenEdges, flowEdgesInThePath);
        addFlowToFlowEdgesInTheList(minimumCapacityBetweenEdges, flowEdgesInReversedDirectionInThePath);
    }

    private List<AbstractFlowEdge> getFlowEdgesInThePath(List<AbstractVertice> path) {
        List<AbstractFlowEdge> flowEdges = new ArrayList<>();
        AbstractVertice[] pathArray = new AbstractVertice[path.size()];
        int currentVerticeIndex = 0;
        AbstractFlowEdge flowEdgeFound;

        path.toArray(pathArray);

        while (currentVerticeIndex + 1 < pathArray.length) {
            flowEdgeFound = residualGraphNetwork.getEdge(
                    pathArray[currentVerticeIndex], pathArray[currentVerticeIndex + 1]);
            flowEdges.add(flowEdgeFound);
            currentVerticeIndex++;
        }

        return flowEdges;
    }

    private List<AbstractFlowEdge> getCorrespondingReversedEdges(List<AbstractFlowEdge> list) {
        List<AbstractFlowEdge> flowEdgesInReversedDirectionList = new ArrayList<>();
        AbstractFlowEdge flowEdgeContainingTheVertices;

        for (AbstractFlowEdge flowEdge : list) {
            flowEdgeContainingTheVertices = residualGraphNetwork.getReversedEdge(
                    flowEdge.getSecondVertice(), flowEdge.getFirstVertice());
            flowEdgesInReversedDirectionList.add(flowEdgeContainingTheVertices);
        }

        return flowEdgesInReversedDirectionList;
    }

    private int getMinimumCapacityInFlowEdgeList(List<AbstractFlowEdge> flowEdges) {
        return flowEdges.stream().mapToInt(AbstractFlowEdge::getMaximumCapacity).min().getAsInt();
    }

    private void removeFromResidualGraphTheEdgesInThePathThatCannotReceiveMoreFlow(List<AbstractFlowEdge> path) {
        List<AbstractFlowEdge> currentFlowEdges = new ArrayList<>(Arrays.asList(residualGraphNetwork.getEdges()));

        for (AbstractFlowEdge flowEdge : residualGraphNetwork.getEdges()) {
            if (path.contains(flowEdge) && flowEdge.getCurrentFlowPassing() == flowEdge.getMaximumCapacity()) {
                currentFlowEdges.remove(flowEdge);
            }
        }

        residualGraphNetwork = new FlowNetwork(getGraphRepresentationForEdges(currentFlowEdges),
                relatedFlowNetwork.getSourceVertice(), relatedFlowNetwork.getSinkVertice());
    }

    private String getGraphRepresentationForEdges(List<AbstractFlowEdge> edges) {
        StringBuilder newRepresentation = new StringBuilder();

        newRepresentation.append('{');

        for (AbstractFlowEdge edge : edges) {
            newRepresentation.append(edge.getRepresentation());
            newRepresentation.append(',');
        }

        newRepresentation.deleteCharAt(newRepresentation.lastIndexOf(","));
        newRepresentation.append('}');

        return newRepresentation.toString();
    }

    private void addFlowToFlowEdgesInTheList(int flow, List<AbstractFlowEdge> list) {
        for (AbstractFlowEdge flowEdge : list) {
            flowEdge.setCurrentFlowPassing(flowEdge.getCurrentFlowPassing() + flow);

            if (flowEdge.getCurrentFlowPassing() > flowEdge.getMaximumCapacity()) {
                flowEdge.setCurrentFlowPassing(flowEdge.getMaximumCapacity());
            }
        }
    }
}
