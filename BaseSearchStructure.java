import java.util.Map;

public class BaseSearchStructure {
    public final int[] discoveryTimes;
    public final int[] endTimes;
    public final Edge.EdgeClassifications[] edgeClassifications;
    public final Graph relatedGraph;

    protected BaseSearchStructure(Graph graph) {
        relatedGraph = graph;
        discoveryTimes = new int[graph.numberOfVertices];
        endTimes = new int[graph.numberOfVertices];
        edgeClassifications = new Edge.EdgeClassifications[graph.numberOfEdges];

        for (int i = 0; i < graph.numberOfVertices; i++) {
            discoveryTimes[i] = -1;
            endTimes[i] = -1;
        }
    }

    public void showTimes() {
        int timeIndex = 0;
        System.out.println("\nVertice -> Discovery time / End time");

        for (Map.Entry<Vertice, Integer> entry : relatedGraph.verticesAndTheirIndices.entrySet()) {
            System.out.print("  " + entry.getKey().name);
            System.out.print(" -> " + discoveryTimes[timeIndex]);
            System.out.println(" / " + endTimes[timeIndex]);
            timeIndex++;
        }
    }

    public void showEdgeClassifications() {
        System.out.println("\nEdge classifications:");
        int edgeIndex = 0;

        for (Map.Entry<Edge, Integer> entry : relatedGraph.edgesAndTheirIndices.entrySet()) {
            System.out.println("  " + entry.getKey().stringRepresentation + " -> " + edgeClassifications[edgeIndex]);
            edgeIndex++;
        }
    }

    protected Vertice getNextNotDiscoveredVerticeBasedOnVerticeSet() {
        for (Map.Entry<Vertice, Integer> entry : relatedGraph.verticesAndTheirIndices.entrySet()) {
            if (discoveryTimes[entry.getValue()] == -1) {
                return entry.getKey();
            }
        }

        return null;
    }

    protected Edge getEdgeThatContainsThisVertices(Vertice firstVertice, Vertice secondVertice) {
        for (Map.Entry<Edge, Integer> entry : relatedGraph.edgesAndTheirIndices.entrySet()) {
            if (entry.getKey().firstVertice.name.compareTo(firstVertice.name) == 0
                    && entry.getKey().secondVertice.name.compareTo(secondVertice.name) == 0) {
                return entry.getKey();
            }
        }

        return null;
    }

    protected void classifyTheEdge(Edge edge) {
        if (edge == null) {
            return;
        }

        if (edge.isDirected) {
            classifyTheEdgeForDirectedGraph(edge);
        } else {
            classifyTheEdgeForUndirectedGraph(edge);
        }
    }

    private void classifyTheEdgeForUndirectedGraph(Edge edge) {
        int indexOfFirstVerticeInVerticeSet = relatedGraph.verticesAndTheirIndices.get(edge.firstVertice);
        int indexOfSecondVerticeInVerticeSet = relatedGraph.verticesAndTheirIndices.get(edge.secondVertice);
        int edgeIndex = relatedGraph.edgesAndTheirIndices.get(edge);

        if (discoveryTimes[indexOfFirstVerticeInVerticeSet] != -1
                && discoveryTimes[indexOfSecondVerticeInVerticeSet] == -1) {
            edgeClassifications[edgeIndex] = Edge.EdgeClassifications.TREE;
        } else {
            edgeClassifications[edgeIndex] = Edge.EdgeClassifications.RETURN;
        }
    }

    private void classifyTheEdgeForDirectedGraph(Edge edge) {
        int indexOfFirstVerticeInVerticeSet = relatedGraph.verticesAndTheirIndices.get(edge.firstVertice);
        int indexOfSecondVerticeInVerticeSet = relatedGraph.verticesAndTheirIndices.get(edge.secondVertice);
        int edgeIndex = relatedGraph.edgesAndTheirIndices.get(edge);

        if (discoveryTimes[indexOfFirstVerticeInVerticeSet] != -1
                && discoveryTimes[indexOfSecondVerticeInVerticeSet] == -1) {
            edgeClassifications[edgeIndex] = Edge.EdgeClassifications.TREE;
        } else if (discoveryTimes[indexOfFirstVerticeInVerticeSet] < discoveryTimes[indexOfSecondVerticeInVerticeSet]
                && endTimes[indexOfSecondVerticeInVerticeSet] != -1) {
            edgeClassifications[edgeIndex] = Edge.EdgeClassifications.ADVANCE;
        } else if (discoveryTimes[indexOfFirstVerticeInVerticeSet] > discoveryTimes[indexOfSecondVerticeInVerticeSet]
                && endTimes[indexOfSecondVerticeInVerticeSet] == -1) {
            edgeClassifications[edgeIndex] = Edge.EdgeClassifications.RETURN;
        } else {
            edgeClassifications[edgeIndex] = Edge.EdgeClassifications.CROSSING;
        }
    }
}
