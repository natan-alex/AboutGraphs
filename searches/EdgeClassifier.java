package searches;

import core.abstractions.AbstractEdge;
import core.abstractions.AbstractTypedGraph;

public class EdgeClassifier {
    public static EdgeClassifications classifyEdge(
            AbstractEdge edge, AbstractTypedGraph graph, AbstractSearch search
    ) {
        if (edge == null) {
            return null;
        }

        if (graph.getType().isDirected()) {
            return classifyEdgeForDirectedGraph(edge, graph, search);
        } else {
            return classifyEdgeForUndirectedGraph(edge, graph, search);
        }
    }

    private static EdgeClassifications classifyEdgeForUndirectedGraph(
            AbstractEdge edge, AbstractTypedGraph graph, AbstractSearch search
    ) {
        int indexOfFirstVerticeInVerticeSet = graph.indexOfVertice(edge.getFirstVertice());
        int indexOfSecondVerticeInVerticeSet = graph.indexOfVertice(edge.getSecondVertice());

        if (search.discoveryTimes[indexOfFirstVerticeInVerticeSet] != -1
                && search.discoveryTimes[indexOfSecondVerticeInVerticeSet] == -1) {
            return EdgeClassifications.TREE;
        } else {
            return EdgeClassifications.RETURN;
        }
    }

    private static EdgeClassifications classifyEdgeForDirectedGraph(
            AbstractEdge edge, AbstractTypedGraph graph, AbstractSearch search
    ) {
        int indexOfFirstVerticeInVerticeSet = graph.indexOfVertice(edge.getFirstVertice());
        int indexOfSecondVerticeInVerticeSet = graph.indexOfVertice(edge.getSecondVertice());

        if (search.discoveryTimes[indexOfFirstVerticeInVerticeSet] != -1
                && search.discoveryTimes[indexOfSecondVerticeInVerticeSet] == -1) {
            return EdgeClassifications.TREE;
        } else if (search.discoveryTimes[indexOfFirstVerticeInVerticeSet] < search.discoveryTimes[indexOfSecondVerticeInVerticeSet]
                && search.endTimes[indexOfSecondVerticeInVerticeSet] != -1) {
            return EdgeClassifications.ADVANCE;
        } else if (search.discoveryTimes[indexOfFirstVerticeInVerticeSet] > search.discoveryTimes[indexOfSecondVerticeInVerticeSet]
                && search.endTimes[indexOfSecondVerticeInVerticeSet] == -1) {
            return EdgeClassifications.RETURN;
        } else {
            return EdgeClassifications.CROSSING;
        }
    }
}
