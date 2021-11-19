public abstract class BaseSearchStructure {
    public final int[] discoveryTimes;
    public final int[] endTimes;
    protected final Graph relatedGraph;
    protected final EdgeClassifications[] edgeClassifications;

    protected BaseSearchStructure(Graph graph) {
        relatedGraph = graph;
        discoveryTimes = new int[graph.numberOfVertices];
        endTimes = new int[graph.numberOfVertices];
        edgeClassifications = new EdgeClassifications[graph.numberOfEdges];

        initializeTimeArrays(graph.numberOfVertices);
    }

    private void initializeTimeArrays(int numberOfVertices) {
        for (int i = 0; i < numberOfVertices; i++) {
            discoveryTimes[i] = -1;
            endTimes[i] = -1;
        }
    }

    public void showTimes() {
        int timeIndex = 0;
        System.out.println("\nVertice -> Discovery time / End time");

        for (Vertice vertice : relatedGraph.vertices) {
            System.out.print("  " + vertice.name);
            System.out.print(" -> " + discoveryTimes[timeIndex]);
            System.out.println(" / " + endTimes[timeIndex]);
            timeIndex++;
        }
    }

    protected Vertice getNextNotDiscoveredVerticeBasedOnVerticeSet() {
        int verticeIndex = 0;

        for (Vertice vertice : relatedGraph.vertices) {
            if (discoveryTimes[verticeIndex] == -1) {
                return vertice;
            }
        }

        return null;
    }

    protected void classifyTheEdge(Edge edge) {
        if (edge == null) {
            return;
        }

        if (relatedGraph.isDirected) {
            classifyTheEdgeForDirectedGraph(edge);
        } else {
            classifyTheEdgeForUndirectedGraph(edge);
        }
    }

    private void classifyTheEdgeForUndirectedGraph(Edge edge) {
        int indexOfFirstVerticeInVerticeSet = relatedGraph.vertices.indexOf(edge.firstVertice);
        int indexOfSecondVerticeInVerticeSet = relatedGraph.vertices.indexOf(edge.secondVertice);
        int edgeIndex = relatedGraph.edges.indexOf(edge);

        if (discoveryTimes[indexOfFirstVerticeInVerticeSet] != -1
                && discoveryTimes[indexOfSecondVerticeInVerticeSet] == -1) {
            edgeClassifications[edgeIndex] = EdgeClassifications.TREE;
        } else {
            edgeClassifications[edgeIndex] = EdgeClassifications.RETURN;
        }
    }

    private void classifyTheEdgeForDirectedGraph(Edge edge) {
        int indexOfFirstVerticeInVerticeSet = relatedGraph.vertices.indexOf(edge.firstVertice);
        int indexOfSecondVerticeInVerticeSet = relatedGraph.vertices.indexOf(edge.secondVertice);
        int edgeIndex = relatedGraph.edges.indexOf(edge);

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

        for (Edge edge : relatedGraph.edges) {
            System.out.println("  " + edge.stringRepresentation + " -> " + edgeClassifications[edgeIndex]);
            edgeIndex++;
        }
    }
}