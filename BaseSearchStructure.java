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

        for (Map.Entry<Vertice, Integer> entry : relatedGraph.vertices.entrySet()) {
            System.out.print("  " + entry.getKey().name);
            System.out.print(" -> " + discoveryTimes[timeIndex]);
            System.out.println(" / " + endTimes[timeIndex]);
            timeIndex++;
        }
    }

    public void showEdgeClassifications() {
        System.out.println("\nEdge classifications:");
        int edgeIndex = 0;

        for (Map.Entry<Edge, Integer> entry : relatedGraph.edges.entrySet()) {
            System.out.println("  " + entry.getKey().stringRepresentation + " -> " + edgeClassifications[edgeIndex]);
            edgeIndex++;
        }
    }

    protected Vertice getNextNotDiscoveredVerticeBasedOnVerticeSet() {
        for (Map.Entry<Vertice, Integer> entry : relatedGraph.vertices.entrySet()) {
            if (discoveryTimes[entry.getValue()] == -1) {
                return entry.getKey();
            }
        }

        return null;
    }

    protected Edge getEdgeThatContainsThisVertices(Vertice firstVertice, Vertice secondVertice) {
        for (Map.Entry<Edge, Integer> entry : relatedGraph.edges.entrySet()) {
            if (entry.getKey().firstVertice.name.compareTo(firstVertice.name) == 0
                    && entry.getKey().secondVertice.name.compareTo(secondVertice.name) == 0) {
                return entry.getKey();
            }
        }

        return null;
    }


    protected void classifyTheEdge(Edge edge) {
        int edgeIndex = relatedGraph.edges.get(edge);
        int indexOfFirstVerticeInVerticeSet = relatedGraph.vertices.get(edge.firstVertice);
        int indexOfSecondVerticeInVerticeSet = relatedGraph.vertices.get(edge.secondVertice);

        if (edge.isDirected) {
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
        } else {
            if (discoveryTimes[indexOfFirstVerticeInVerticeSet] != -1
                    && discoveryTimes[indexOfSecondVerticeInVerticeSet] == -1) {
                edgeClassifications[edgeIndex] = Edge.EdgeClassifications.TREE;
            } else {
                edgeClassifications[edgeIndex] = Edge.EdgeClassifications.RETURN;
            }
        }
    }
}
