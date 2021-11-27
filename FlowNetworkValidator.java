import java.util.regex.Pattern;

public class FlowNetworkValidator {
    private static Graph relatedGraph;
    private static Vertice sourceVertice;
    private static Vertice sinkVertice;

    public static void validateGraphAsFlowNetwork(Graph graph) {
        relatedGraph = graph;
        throwExceptionIfGraphIsNotAValidFlowNetwork();
    }
    
    private static void throwExceptionIfGraphIsNotAValidFlowNetwork() throws IllegalArgumentException {
        if (relatedGraph.type != GraphTypes.DIRECTED_AND_PONDERED) {
            throw new IllegalArgumentException(
                    "Can not create a flow network from an unpondered or undirected graph.");
        }
    }

    public static void validateSourceAndSinkVertices(Vertice source, String sourceName, Vertice sink, String sinkName) {
        sourceVertice = source;
        sinkVertice = sink;

        throwExceptionIfVerticeIsNull(source, sourceName);
        throwExceptionIfVerticeIsNull(sink, sinkName);
        throwExceptionIfSourceAndSinkAreEquals(sourceName, sinkName);
        checkIfSourceAndSinkAreValid();
    }

    private static void throwExceptionIfVerticeIsNull(Vertice verticeFound, String verticeName) throws IllegalArgumentException {
        if (verticeFound == null) {
            throw new IllegalArgumentException(getNotFoundExceptionMessage(verticeName));
        }
    }

    private static String getNotFoundExceptionMessage(String verticeName) {
        return "The vertice " + verticeName + " is not in the vertice set." + "\nThe vertice set is: "
                + relatedGraph.vertices;
    }

    private static void throwExceptionIfSourceAndSinkAreEquals(String first, String second) {
        if (first.compareToIgnoreCase(second) == 0) {
            throw new IllegalArgumentException("Source and sink vertices can not be the same vertice.");
        }
    }

    private static void checkIfSourceAndSinkAreValid() throws IllegalArgumentException {
        // check is there is a substring where source is the vertice where edge ends
        if (Pattern.compile(",\\s*" + sourceVertice.name).matcher(relatedGraph.stringRepresentation).find()) {
            throw new IllegalArgumentException("The source can not have edges coming to it.");
        }

        // check is there is a substring where sink is the vertice where edge starts
        if (Pattern.compile("\\(" + sinkVertice.name).matcher(relatedGraph.stringRepresentation).find()) {
            throw new IllegalArgumentException("The sink can not have edges leaving from it.");
        }
    }
}
