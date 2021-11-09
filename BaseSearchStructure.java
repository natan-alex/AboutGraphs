import java.util.Map;

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
}