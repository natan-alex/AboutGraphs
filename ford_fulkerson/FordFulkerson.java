package aboutGraphs.fordFulkerson;

import java.util.ArrayList;
import java.util.List;

import aboutGraphs.searches.DeepFirstSearch;
import aboutGraphs.core.*;

public class FordFulkerson {
    private final Graph relatedGraph;
    private final Vertice source;
    private final Vertice sink;
    private FlowNetwork residualGraphNetwork;
    private List<List<Vertice>> disjointPaths;
    private DeepFirstSearch deepFirstSearch;
    private List<Vertice> pathFound;
    private List<FlowEdge> flowEdgesInThePath;
    private List<FlowEdge> flowEdgesInReversedDirectionInThePath;
    private int minimumCapacityBetweenEdges;

    public FordFulkerson(Graph graph, String source, String sink) throws IllegalArgumentException {
        FlowNetworkValidator.validateGraphAsFlowNetwork(graph);

        this.source = graph.getVerticeByName(source);
        this.sink = graph.getVerticeByName(sink);

        FlowNetworkValidator.validateSourceAndSinkVertices(this.source, source, this.sink, sink);

        relatedGraph = graph;
    }

    public List<List<Vertice>> computeMaximumFlowAndGetDisjointPaths() {
        residualGraphNetwork = new FlowNetwork(relatedGraph.stringRepresentation);
        disjointPaths = new ArrayList<>();
        deepFirstSearch = new DeepFirstSearch(residualGraphNetwork);
        pathFound = deepFirstSearch.getPathBetweenVertices(source, sink);

        while (pathFound != null && !pathFound.isEmpty() && !disjointPaths.contains(pathFound)) {
            flowEdgesInThePath = getFlowEdgesInThePath(pathFound);
            flowEdgesInReversedDirectionInThePath = getCorrespondingFlowEdgesInReversedDirectionAccordingToFlowEdgeList(
                    flowEdgesInThePath);

            minimumCapacityBetweenEdges = getMinimumCapacityInFlowEdgeList(flowEdgesInThePath);

            adjustFlowThatCanStillPassInFlowEdgesInTheList(minimumCapacityBetweenEdges, flowEdgesInThePath);
            addFlowToFlowEdgesInTheList(minimumCapacityBetweenEdges, flowEdgesInReversedDirectionInThePath);

            disjointPaths.add(pathFound);
            removeFromResidualGraphTheEdgesInThePathThatCannotReceiveMoreFlow(flowEdgesInThePath);

            pathFound = deepFirstSearch.getPathBetweenVertices(source, sink);
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
            flowEdgeFound = residualGraphNetwork.getDirectedEdgeWithThisVertices(pathArray[currentVerticeIndex],
                    pathArray[currentVerticeIndex + 1]);
            flowEdges.add(flowEdgeFound);
            currentVerticeIndex++;
        }

        return flowEdges;
    }
    
    private List<FlowEdge> getCorrespondingFlowEdgesInReversedDirectionAccordingToFlowEdgeList(List<FlowEdge> list) {
        List<FlowEdge> flowEdgesInReversedDirectionList = new ArrayList<>();
        FlowEdge flowEdgeContainingTheVertices;

        for (FlowEdge flowEdge : list) {
            flowEdgeContainingTheVertices = residualGraphNetwork.getDirectedEdgeInReversedDirectionWithThisVertices(flowEdge.secondVertice, flowEdge.firstVertice);
            flowEdgesInReversedDirectionList.add(flowEdgeContainingTheVertices);
        }

        return flowEdgesInReversedDirectionList;
    }

    private int getMinimumCapacityInFlowEdgeList(List<FlowEdge> flowEdges) {
        return flowEdges.stream().mapToInt(edge -> edge.maximumCapacity).min().getAsInt();
    }

    private void removeFromResidualGraphTheEdgesInThePathThatCannotReceiveMoreFlow(List<FlowEdge> path) {
        List<FlowEdge> currentFlowEdges = new ArrayList<>(residualGraphNetwork.flowEdges);

        for (FlowEdge flowEdge : currentFlowEdges) {
            if (path.contains(flowEdge) && flowEdge.howMuchFlowCanStillPass == 0) {
                residualGraphNetwork.flowEdges.remove(flowEdge);
            }
        }
    }
    
    private void adjustFlowThatCanStillPassInFlowEdgesInTheList(int flow, List<FlowEdge> list) {
        for (FlowEdge flowEdge : list) {
            flowEdge.howMuchFlowCanStillPass = flowEdge.maximumCapacity - flow - flowEdge.howMuchFlowCanStillPass;

            if (flowEdge.howMuchFlowCanStillPass < 0) {
                flowEdge.howMuchFlowCanStillPass = 0;
            }
        }
    }

    private void addFlowToFlowEdgesInTheList(int flow, List<FlowEdge> list) {
        for (FlowEdge flowEdge : list) {
            flowEdge.howMuchFlowCanStillPass += flow;

            if (flowEdge.howMuchFlowCanStillPass > flowEdge.maximumCapacity) {
                flowEdge.howMuchFlowCanStillPass = flowEdge.maximumCapacity;
            }
        }
    }
}
