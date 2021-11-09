import java.util.Map;

public class EdgeClassifier {
    private int indexOfFirstVerticeInVerticeSet;
    private int indexOfSecondVerticeInVerticeSet;
    private int edgeIndex;
    private final Graph relatedGraph;
    private final int[] discoveryTimes;
    private final int[] endTimes;

    public final EdgeClassifications[] edgeClassifications;

    public EdgeClassifier(Graph graph, int[] discoveryTimes, int[] endTimes) {
        relatedGraph = graph;
        this.discoveryTimes = discoveryTimes;
        this.endTimes = endTimes;
        edgeClassifications = new EdgeClassifications[graph.numberOfEdges];
    }

    public void classifyTheEdge(Edge edge) {
        if (edge == null) {
            return;
        }

        indexOfFirstVerticeInVerticeSet = relatedGraph.verticesAndTheirIndices.get(edge.firstVertice);
        indexOfSecondVerticeInVerticeSet = relatedGraph.verticesAndTheirIndices.get(edge.secondVertice);
        edgeIndex = relatedGraph.edgesAndTheirIndices.get(edge);

        if (relatedGraph.isDirected) {
            classifyTheEdgeForDirectedGraph(edge);
        } else {
            classifyTheEdgeForUndirectedGraph(edge);
        }
    }

    private void classifyTheEdgeForUndirectedGraph(Edge edge) {
        if (discoveryTimes[indexOfFirstVerticeInVerticeSet] != -1
                && discoveryTimes[indexOfSecondVerticeInVerticeSet] == -1) {
            edgeClassifications[edgeIndex] = EdgeClassifications.TREE;
        } else {
            edgeClassifications[edgeIndex] = EdgeClassifications.RETURN;
        }
    }

    private void classifyTheEdgeForDirectedGraph(Edge edge) {
        if (discoveryTimes[indexOfFirstVerticeInVerticeSet] != -1
                && discoveryTimes[indexOfSecondVerticeInVerticeSet] == -1) {
            edgeClassifications[edgeIndex] = EdgeClassifications.TREE;
        } else if (discoveryTimes[indexOfFirstVerticeInVerticeSet] < discoveryTimes[indexOfSecondVerticeInVerticeSet]
                && endTimes[indexOfSecondVerticeInVerticeSet] != -1) {
            edgeClassifications[edgeIndex] = EdgeClassifications.ADVANCE;
        } else if (discoveryTimes[indexOfFirstVerticeInVerticeSet] > discoveryTimes[indexOfSecondVerticeInVerticeSet]
                && endTimes[indexOfSecondVerticeInVerticeSet] == -1) {
            edgeClassifications[edgeIndex] = EdgeClassifications.RETURN;
        } else {
            edgeClassifications[edgeIndex] = EdgeClassifications.CROSSING;
        }
    }

    public void showEdgeClassifications() {
        int edgeIndex = 0;

        for (Map.Entry<Edge, Integer> entry : relatedGraph.edgesAndTheirIndices.entrySet()) {
            System.out.println("  " + entry.getKey().stringRepresentation + " -> " + edgeClassifications[edgeIndex]);
            edgeIndex++;
        }
    }
}
