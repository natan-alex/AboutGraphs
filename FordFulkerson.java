import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class FordFulkerson {
    private final FlowNetwork relatedGraphNetwork;
    private final Vertice source;
    private final Vertice sink;
    private FlowNetwork residualGraphNetwork;
    private List<List<Vertice>> disjointPaths;
    private DeepFirstSearch deepFirstSearch;

    public FordFulkerson(Graph graph, String source, String sink) throws IllegalArgumentException {
        relatedGraphNetwork = new FlowNetwork(graph.stringRepresentation);

        throwExceptionIfSourceAndSinkAreEquals(source, sink);
        throwExceptionIfGraphIsInvalid();

        this.source = relatedGraphNetwork.getVerticeByName(source);
        this.sink = relatedGraphNetwork.getVerticeByName(sink);

        throwExceptionIfVerticeIsNull(this.source, source);
        throwExceptionIfVerticeIsNull(this.sink, sink);

        checkIfSourceAndSinkAreValid();

        computeMaximumFlow();
    }

    public FordFulkerson(Graph graph, Vertice source, Vertice sink) throws IllegalArgumentException {
        relatedGraphNetwork = new FlowNetwork(graph.stringRepresentation);

        throwExceptionIfSourceAndSinkAreEquals(source, sink);
        throwExceptionIfGraphIsInvalid();

        this.source = source;
        this.sink = sink;

        checkIfSourceAndSinkAreValid();
        checkIfSourceAndSinkAreInVerticeSet();

        computeMaximumFlow();
    }

    private void computeMaximumFlow() {
        // set edge values to 0 in residual graph
        String stringRepresentationForZeroEdgeValues = relatedGraphNetwork.stringRepresentation.replaceAll(",\\s*\\d+",
                ",0");
        residualGraphNetwork = new FlowNetwork(stringRepresentationForZeroEdgeValues);
        disjointPaths = new ArrayList<>();
        deepFirstSearch = new DeepFirstSearch(residualGraphNetwork);
        List<Vertice> path = deepFirstSearch.getPathBetweenVertices(source, sink);
        List<FlowEdge> edgesInThePath;
        int maximumFlowBetweenEdges;
        System.out.println("network edges: " + residualGraphNetwork.flowEdges);

        while (path != null && !path.isEmpty()) {
            edgesInThePath = getEdgesInThePath(path);
            System.out.println("edges: " + edgesInThePath);
            maximumFlowBetweenEdges = edgesInThePath.stream().mapToInt(edge -> edge.maximumCapacity).min().getAsInt();
            System.out.println("max flow: " + maximumFlowBetweenEdges);
            disjointPaths.add(path);
            System.out.println("path: " + path);
            path = deepFirstSearch.getPathBetweenVertices(source, sink);
        }
    }

    private List<FlowEdge> getEdgesInThePath(List<Vertice> path) {
        List<FlowEdge> edges = new ArrayList<>();
        Vertice[] pathArray = new Vertice[path.size()];
        int currentVerticeIndex = 0;
        Edge edgeFound;

        path.toArray(pathArray);

        while (currentVerticeIndex + 1 < pathArray.length) {
            edgeFound = relatedGraphNetwork.getDirectedEdgeWithThisVertices(pathArray[currentVerticeIndex],
                    pathArray[currentVerticeIndex + 1]);
            edges.add(new FlowEdge(edgeFound.stringRepresentation, edgeFound.value));
            currentVerticeIndex++;
        }

        return edges;
    }

    private void throwExceptionIfSourceAndSinkAreEquals(String first, String second) {
        if (first.compareToIgnoreCase(second) == 0) {
            throw new IllegalArgumentException("Source and sink vertices can not be the same vertice.");
        }
    }

    private void throwExceptionIfSourceAndSinkAreEquals(Vertice first, Vertice second) {
        if (first.equals(second)) {
            throw new IllegalArgumentException("Source and sink vertices can not be the same vertice.");
        }
    }

    private void checkIfSourceAndSinkAreValid() throws IllegalArgumentException {
        if (Pattern.compile(",\\s*" + source.name).matcher(relatedGraphNetwork.stringRepresentation).find()) {
            throw new IllegalArgumentException("The source can not have edges coming to it.");
        }

        if (Pattern.compile("\\(" + sink.name).matcher(relatedGraphNetwork.stringRepresentation).find()) {
            throw new IllegalArgumentException("The sink can not have edges leaving from it.");
        }
    }

    private void checkIfSourceAndSinkAreInVerticeSet() {
        if (!relatedGraphNetwork.vertices.contains(source)) {
            throw new IllegalArgumentException(getNotFoundExceptionMessage(source.name));
        }

        if (!relatedGraphNetwork.vertices.contains(sink)) {
            throw new IllegalArgumentException(getNotFoundExceptionMessage(sink.name));
        }
    }

    private void throwExceptionIfGraphIsInvalid() throws IllegalArgumentException {
        if (relatedGraphNetwork.type != GraphTypes.DIRECTED_AND_PONDERED) {
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
                + relatedGraphNetwork.vertices;
    }

}
