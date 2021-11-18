public abstract class BaseSearchStructure {
    public final int[] discoveryTimes;
    public final int[] endTimes;
    public final Graph relatedGraph;
    protected final EdgeClassifier edgeClassifier;

    protected BaseSearchStructure(Graph graph) {
        relatedGraph = graph;
        discoveryTimes = new int[graph.numberOfVertices];
        endTimes = new int[graph.numberOfVertices];
        edgeClassifier = new EdgeClassifier(relatedGraph, discoveryTimes, endTimes);

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

    protected Edge getEdgeThatContainsThisVertices(Vertice firstVertice, Vertice secondVertice) {
        for (Edge edge : relatedGraph.edges) {
            if (edge.firstVertice.equals(firstVertice) && edge.secondVertice.equals(secondVertice)) {
                return edge;
            }
        }

        return null;
    }
}