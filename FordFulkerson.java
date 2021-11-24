import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

public class FordFulkerson {
    private final Graph relatedGraph;
    private final Vertice source;
    private final Vertice sink;
    private FlowNetwork residualGraphNetwork;
    private List<List<Vertice>> disjointPaths;
    private DeepFirstSearch deepFirstSearch;

    public FordFulkerson(Graph graph, String source, String sink) throws IllegalArgumentException {
        relatedGraph = graph;

        throwExceptionIfSourceAndSinkAreEquals(source, sink);
        throwExceptionIfGraphIsInvalid();

        this.source = relatedGraph.getVerticeByName(source);
        this.sink = relatedGraph.getVerticeByName(sink);

        throwExceptionIfVerticeIsNull(this.source, source);
        throwExceptionIfVerticeIsNull(this.sink, sink);

        checkIfSourceAndSinkAreValid();

        computeMaximumFlow();
    }

    private void computeMaximumFlow() {
        // set edge values to 0 in residual graph
        residualGraphNetwork = new FlowNetwork(relatedGraph.stringRepresentation);
        disjointPaths = new ArrayList<>();
        deepFirstSearch = new DeepFirstSearch(residualGraphNetwork);
        List<Vertice> path = deepFirstSearch.getPathBetweenVertices(source, sink);
        List<FlowEdge> flowEdgesInThePath;
        int minimumCapacityBetweenEdges;

        while (path != null && !path.isEmpty() && !disjointPaths.contains(path)) {
            flowEdgesInThePath = getFlowEdgesInThePath(path);
            minimumCapacityBetweenEdges = getMinimumCapacityInFlowEdgeList(flowEdgesInThePath);

            addFlowToResidualNetworkFlowEdges(minimumCapacityBetweenEdges);

            disjointPaths.add(path);

            System.out.println("edges: " + flowEdgesInThePath);
            System.out.println("min flow: " + minimumCapacityBetweenEdges);
            System.out.println("path: " + path);

            path = deepFirstSearch.getPathBetweenVertices(source, sink);
        }
        System.out.println("residual g: " + residualGraphNetwork.flowEdges);
        System.out.println("residual g rev: " + residualGraphNetwork.flowEdgesInReversedDirection);
    }

    private void addFlowToResidualNetworkFlowEdges(int flow) {
        Iterator<FlowEdge> flowEdgesIterator = residualGraphNetwork.flowEdges.iterator();
        Iterator<FlowEdge> reversedFlowEdgesIterator = residualGraphNetwork.flowEdgesInReversedDirection.iterator();
        FlowEdge currentFlowEdge;
        FlowEdge currentReversedFlowEdge;

        for (int i = 0; i < residualGraphNetwork.flowEdges.size(); i++) {
            currentFlowEdge = flowEdgesIterator.next();
            currentReversedFlowEdge = reversedFlowEdgesIterator.next();

            currentFlowEdge.currentFlow = currentFlowEdge.maximumCapacity - flow - currentFlowEdge.currentFlow;

            if (currentFlowEdge.currentFlow < 0) {
                currentFlowEdge.currentFlow = 0;
            }

            currentReversedFlowEdge.currentFlow += flow;

            if (currentReversedFlowEdge.currentFlow > currentReversedFlowEdge.maximumCapacity) {
                currentReversedFlowEdge.currentFlow = currentReversedFlowEdge.maximumCapacity;
            }
        }
    }

    private int getMinimumCapacityInFlowEdgeList(List<FlowEdge> flowEdges) {
        return flowEdges.stream().mapToInt(edge -> edge.maximumCapacity).min().getAsInt();
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

    private void throwExceptionIfSourceAndSinkAreEquals(String first, String second) {
        if (first.compareToIgnoreCase(second) == 0) {
            throw new IllegalArgumentException("Source and sink vertices can not be the same vertice.");
        }
    }

    private void checkIfSourceAndSinkAreValid() throws IllegalArgumentException {
        if (Pattern.compile(",\\s*" + source.name).matcher(relatedGraph.stringRepresentation).find()) {
            throw new IllegalArgumentException("The source can not have edges coming to it.");
        }

        if (Pattern.compile("\\(" + sink.name).matcher(relatedGraph.stringRepresentation).find()) {
            throw new IllegalArgumentException("The sink can not have edges leaving from it.");
        }
    }

    private void throwExceptionIfGraphIsInvalid() throws IllegalArgumentException {
        if (relatedGraph.type != GraphTypes.DIRECTED_AND_PONDERED) {
            throw new IllegalArgumentException(
                    "Can not execute ford fulkerson algorithm in an unpondered or undirected graph.");
        }
    }

    private void throwExceptionIfVerticeIsNull(Vertice verticeFound, String verticeName)
            throws IllegalArgumentException {
        if (verticeFound == null) {
            throw new IllegalArgumentException(getNotFoundExceptionMessage(verticeName));
        }
    }

    private String getNotFoundExceptionMessage(String verticeName) {
        return "The vertice " + verticeName + " is not in the vertice set." + "\nThe vertice set is: "
                + relatedGraph.vertices;
    }

}
