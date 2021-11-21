import java.util.regex.Pattern;

public class FordFulkerson {
    private final Graph relatedGraph;
    private final Vertice source;
    private final Vertice sink;

    public FordFulkerson(Graph graph, String source, String sink) throws IllegalArgumentException {
        relatedGraph = graph;

        throwExceptionIfGraphIsInvalid();

        this.source = relatedGraph.getVerticeByName(source);
        this.sink = relatedGraph.getVerticeByName(sink);

        throwExceptionIfVerticeIsNull(this.source, source);
        throwExceptionIfVerticeIsNull(this.sink, sink);

        checkIfSourceAndSinkAreValid();
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
