import java.util.Map;

public class EdgeClassifier {
    private int indexOfFirstVerticeInVerticeSet;
    private int indexOfSecondVerticeInVerticeSet;
    private int edgeIndex;
    public final EdgeClassifications[] edgeClassifications;
    private final BaseSearchStructure searchStructure;

    public EdgeClassifier(BaseSearchStructure relatedSearchStructure) {
        searchStructure = relatedSearchStructure;
        edgeClassifications = new EdgeClassifications[searchStructure.relatedGraph.numberOfEdges];
    }

    public void classifyTheEdge(Edge edge) {
        if (edge == null) {
            return;
        }

        indexOfFirstVerticeInVerticeSet = searchStructure.relatedGraph.verticesAndTheirIndices.get(edge.firstVertice);
        indexOfSecondVerticeInVerticeSet = searchStructure.relatedGraph.verticesAndTheirIndices.get(edge.secondVertice);
        edgeIndex = searchStructure.relatedGraph.edgesAndTheirIndices.get(edge);

        if (searchStructure.relatedGraph.isDirected) {
            classifyTheEdgeForDirectedGraph(edge);
        } else {
            classifyTheEdgeForUndirectedGraph(edge);
        }
    }

    private void classifyTheEdgeForUndirectedGraph(Edge edge) {
        if (searchStructure.discoveryTimes[indexOfFirstVerticeInVerticeSet] != -1
                && searchStructure.discoveryTimes[indexOfSecondVerticeInVerticeSet] == -1) {
            edgeClassifications[edgeIndex] = EdgeClassifications.TREE;
        } else {
            edgeClassifications[edgeIndex] = EdgeClassifications.RETURN;
        }
    }

    private void classifyTheEdgeForDirectedGraph(Edge edge) {
        if (searchStructure.discoveryTimes[indexOfFirstVerticeInVerticeSet] != -1
                && searchStructure.discoveryTimes[indexOfSecondVerticeInVerticeSet] == -1) {
            edgeClassifications[edgeIndex] = EdgeClassifications.TREE;
        } else if (searchStructure.discoveryTimes[indexOfFirstVerticeInVerticeSet] < searchStructure.discoveryTimes[indexOfSecondVerticeInVerticeSet]
                && searchStructure.endTimes[indexOfSecondVerticeInVerticeSet] != -1) {
            edgeClassifications[edgeIndex] = EdgeClassifications.ADVANCE;
        } else if (searchStructure.discoveryTimes[indexOfFirstVerticeInVerticeSet] > searchStructure.discoveryTimes[indexOfSecondVerticeInVerticeSet]
                && searchStructure.endTimes[indexOfSecondVerticeInVerticeSet] == -1) {
            edgeClassifications[edgeIndex] = EdgeClassifications.RETURN;
        } else {
            edgeClassifications[edgeIndex] = EdgeClassifications.CROSSING;
        }
    }

    public void showEdgeClassifications() {
        int edgeIndex = 0;

        for (Map.Entry<Edge, Integer> entry : searchStructure.relatedGraph.edgesAndTheirIndices.entrySet()) {
            System.out.println("  " + entry.getKey().stringRepresentation + " -> " + edgeClassifications[edgeIndex]);
            edgeIndex++;
        }
    }
}
