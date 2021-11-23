import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class FordFulkerson {
    private final Graph relatedGraph;
    private final Vertice source;
    private final Vertice sink;
    private Graph residualGraph;
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

    public FordFulkerson(Graph graph, Vertice source, Vertice sink) throws IllegalArgumentException {
        relatedGraph = graph;

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
        String stringRepresentationForZeroEdgeValues = relatedGraph.stringRepresentation.replaceAll(",\\s*\\d+", ",0");
        residualGraph = new Graph(stringRepresentationForZeroEdgeValues);
        disjointPaths = new ArrayList<>();
        deepFirstSearch = new DeepFirstSearch(residualGraph);
        List<Vertice> path = deepFirstSearch.getPathBetweenVertices(source, sink);
        List<Edge> edgesInThePath;
        int maximumFlowBetweenEdges;

        while (path != null && !path.isEmpty()) {
            edgesInThePath = getEdgesInThePath(path);
            System.out.println("edges: " + edgesInThePath);
            maximumFlowBetweenEdges = edgesInThePath.stream().mapToInt(edge -> edge.value).min().getAsInt();
            System.out.println("max flow: " + maximumFlowBetweenEdges);
            disjointPaths.add(path);
            System.out.println("path: " + path);
            path = deepFirstSearch.getPathBetweenVertices(source, sink);
        }
    }

    private List<Edge> getEdgesInThePath(List<Vertice> path) {
        List<Edge> edges = new ArrayList<>();
        Vertice[] pathArray = new Vertice[path.size()];
        int currentVerticeIndex = 0;

        path.toArray(pathArray);

        while (currentVerticeIndex + 1 < pathArray.length) {
            edges.add(relatedGraph.getDirectedEdgeWithThisVertices(pathArray[currentVerticeIndex],
                    pathArray[currentVerticeIndex + 1]));
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
        if (Pattern.compile(",\\s*" + source.name).matcher(relatedGraph.stringRepresentation).find()) {
            throw new IllegalArgumentException("The source can not have edges coming to it.");
        }

        if (Pattern.compile("\\(" + sink.name).matcher(relatedGraph.stringRepresentation).find()) {
            throw new IllegalArgumentException("The sink can not have edges leaving from it.");
        }
    }

    private void checkIfSourceAndSinkAreInVerticeSet() {
        if (!relatedGraph.vertices.contains(source)) {
            throw new IllegalArgumentException(getNotFoundExceptionMessage(source.name));
        }

        if (!relatedGraph.vertices.contains(sink)) {
            throw new IllegalArgumentException(getNotFoundExceptionMessage(sink.name));
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
