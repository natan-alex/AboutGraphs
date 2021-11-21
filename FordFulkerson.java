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

        throwExceptionIfSourceAndSinkRepresentationsAreEquals(source, sink);
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
        residualGraph = new Graph(relatedGraph.stringRepresentation.replaceAll(",\\s*\\d+", ",0"));
        disjointPaths = new ArrayList<>();
        deepFirstSearch = new DeepFirstSearch(residualGraph);
        List<Vertice> path = deepFirstSearch.getPathBetweenVertices(source, sink);

        while (path != null && !path.isEmpty()) {
            System.out.println("path: " + path);
            System.out.println(getEdgesInThePath(path));
            path = deepFirstSearch.getPathBetweenVertices(source, sink);
        }
    }

    private List<Edge> getEdgesInThePath(List<Vertice> path) {
        List<Edge> edges = new ArrayList<>();
        Vertice[] pathArray = new Vertice[path.size()];
        int currentVerticeIndex = 0;

        path.toArray(pathArray);

        while (currentVerticeIndex + 1 < pathArray.length) {
            edges.add(residualGraph.getDirectedEdgeWithThisVertices(pathArray[currentVerticeIndex],
                    pathArray[currentVerticeIndex + 1]));
            currentVerticeIndex++;
        }

        return edges;
    }

    private void throwExceptionIfSourceAndSinkRepresentationsAreEquals(String first, String second) {
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
        if (!relatedGraph.isPondered && !relatedGraph.isDirected) {
            throw new IllegalArgumentException(
                    "Can not execute ford fulkerson algorithm in an unpondered or undirected graph.");
        }
    }

    private void throwExceptionIfVerticeIsNull(Vertice verticeFound, String verticeName)
            throws IllegalArgumentException {
        if (verticeFound == null) {
            throw new IllegalArgumentException("The vertice " + verticeName + " is not in the vertice set."
                    + " The vertice set is: " + relatedGraph.vertices);
        }
    }
}
