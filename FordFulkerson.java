import java.util.ArrayList;
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
        int minimumFlowBetweenEdges;

        while (path != null && !path.isEmpty()) {
            flowEdgesInThePath = getFlowEdgesInThePath(path);
            minimumFlowBetweenEdges = getMinimumFlowInFlowEdgeList(flowEdgesInThePath);

            addFlowToEdges(flowEdgesInThePath, minimumFlowBetweenEdges);

            disjointPaths.add(path);

            System.out.println("edges: " + flowEdgesInThePath);
            System.out.println("min flow: " + minimumFlowBetweenEdges);
            System.out.println("path: " + path);

            path = deepFirstSearch.getPathBetweenVertices(source, sink);
        }
        System.out.println("residual g: " + residualGraphNetwork.flowEdges);
    }

    private void addFlowToEdges(List<FlowEdge> flowEdges, int flow) {
        for (FlowEdge flowEdge : flowEdges) {
            flowEdge.currentCapacity += flow;

            if (flowEdge.currentCapacity > flowEdge.maximumCapacity) {
                flowEdge.currentCapacity = flowEdge.maximumCapacity;
            }

            flowEdge.currentCapacityInReversedDirection += flow;

            if (flowEdge.currentCapacityInReversedDirection > flowEdge.maximumCapacity) {
                flowEdge.currentCapacityInReversedDirection = flowEdge.maximumCapacity;
            }
        }
    }

    private int getMinimumFlowInFlowEdgeList(List<FlowEdge> edges) {
        return edges.stream().mapToInt(edge -> edge.maximumCapacity).min().getAsInt();
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
