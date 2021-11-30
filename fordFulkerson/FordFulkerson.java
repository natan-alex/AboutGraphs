package fordFulkerson;

import java.util.ArrayList;
import java.util.List;

import searches.DeepFirstSearch;
import core.*;
import representations.SuccessorAdjacencyList;

public class FordFulkerson {
    private FlowNetwork relatedFlowNetwork;
    private FlowNetwork residualGraphNetwork;
    private List<List<Vertice>> disjointPaths;
    private DeepFirstSearch deepFirstSearch;
    private List<Vertice> pathFound;
    private List<FlowEdge> flowEdgesInThePath;
    private List<FlowEdge> flowEdgesInReversedDirectionInThePath;
    private int minimumCapacityBetweenEdges;

    public FordFulkerson(FlowNetwork flowNetwork) {
        relatedFlowNetwork = flowNetwork;
    }

    public List<List<Vertice>> computeMaximumFlowAndGetDisjointPaths() {
        residualGraphNetwork = new FlowNetwork(relatedFlowNetwork.getRepresentation(), relatedFlowNetwork.getSourceVertice(), relatedFlowNetwork.getSinkVertice());
        disjointPaths = new ArrayList<>();
        deepFirstSearch = new DeepFirstSearch(residualGraphNetwork);
        pathFound = deepFirstSearch.getPathBetweenVertices(relatedFlowNetwork.getSourceVertice(), relatedFlowNetwork.getSinkVertice());

        while (pathFound != null && !pathFound.isEmpty() && !disjointPaths.contains(pathFound)) {
            flowEdgesInThePath = getFlowEdgesInThePath(pathFound);
            flowEdgesInReversedDirectionInThePath = getCorrespondingReversedEdges(
                    flowEdgesInThePath);

            minimumCapacityBetweenEdges = getMinimumCapacityInFlowEdgeList(flowEdgesInThePath);

            addFlowToFlowEdgesInTheList(minimumCapacityBetweenEdges, flowEdgesInThePath);
            addFlowToFlowEdgesInTheList(minimumCapacityBetweenEdges, flowEdgesInReversedDirectionInThePath);

            disjointPaths.add(pathFound);
            removeFromResidualGraphTheEdgesInThePathThatCannotReceiveMoreFlow(flowEdgesInThePath);

            deepFirstSearch = new DeepFirstSearch(residualGraphNetwork,
                    new SuccessorAdjacencyList(residualGraphNetwork));
            pathFound = deepFirstSearch.getPathBetweenVertices(relatedFlowNetwork.getSourceVertice(), relatedFlowNetwork.getSinkVertice());
        }

        return disjointPaths;
    }

    private List<FlowEdge> getFlowEdgesInThePath(List<Vertice> path) {
        List<FlowEdge> flowEdges = new ArrayList<>();
        Vertice[] pathArray = new Vertice[path.size()];
        int currentVerticeIndex = 0;
        FlowEdge flowEdgeFound;

        path.toArray(pathArray);

        while (currentVerticeIndex + 1 < pathArray.length) {
            flowEdgeFound = residualGraphNetwork.getDirectedEdgeWithTheseVertices(pathArray[currentVerticeIndex],
                    pathArray[currentVerticeIndex + 1]);
            flowEdges.add(flowEdgeFound);
            currentVerticeIndex++;
        }

        return flowEdges;
    }

    private List<FlowEdge> getCorrespondingReversedEdges(List<FlowEdge> list) {
        List<FlowEdge> flowEdgesInReversedDirectionList = new ArrayList<>();
        FlowEdge flowEdgeContainingTheVertices;

        for (FlowEdge flowEdge : list) {
            flowEdgeContainingTheVertices = residualGraphNetwork
                    .getDirectedReversedEdgeWithTheseVertices(flowEdge.getSecondVertice(), flowEdge.getFirstVertice());
            flowEdgesInReversedDirectionList.add(flowEdgeContainingTheVertices);
        }

        return flowEdgesInReversedDirectionList;
    }

    private int getMinimumCapacityInFlowEdgeList(List<FlowEdge> flowEdges) {
        return flowEdges.stream().mapToInt(edge -> edge.getMaximumCapacity()).min().getAsInt();
    }

    private void removeFromResidualGraphTheEdgesInThePathThatCannotReceiveMoreFlow(List<FlowEdge> path) {
        List<FlowEdge> currentFlowEdges = new ArrayList<>(residualGraphNetwork.getEdges());

        for (FlowEdge flowEdge : currentFlowEdges) {
            if (path.contains(flowEdge) && flowEdge.getCurrentFlowPassing() == flowEdge.getMaximumCapacity()) {
                residualGraphNetwork.getEdges().remove(flowEdge);
            }
        }
    }

    private void addFlowToFlowEdgesInTheList(int flow, List<FlowEdge> list) {
        for (FlowEdge flowEdge : list) {
            flowEdge.setCurrentFlowPassing(flowEdge.getCurrentFlowPassing() + flow);

            if (flowEdge.getCurrentFlowPassing() > flowEdge.getMaximumCapacity()) {
                flowEdge.setCurrentFlowPassing(flowEdge.getMaximumCapacity());
            }
        }
    }
}
