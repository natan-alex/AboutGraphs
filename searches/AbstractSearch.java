package searches;

import core.abstractions.AbstractEdge;
import core.abstractions.AbstractGraph;
import core.abstractions.AbstractTypedGraph;
import core.abstractions.AbstractVertice;

public abstract class AbstractSearch {
    protected final AbstractGraph relatedGraph;
    protected final int[] discoveryTimes;
    protected final int[] endTimes;

    public abstract void computeTimes();

    protected AbstractSearch(AbstractGraph graph) {
        relatedGraph = graph;
        discoveryTimes = new int[graph.getNumberOfVertices()];
        endTimes = new int[graph.getNumberOfVertices()];

        initializeTimeArrays();
    }

    protected void initializeTimeArrays() {
        for (int i = 0; i < relatedGraph.getNumberOfVertices(); i++) {
            discoveryTimes[i] = -1;
            endTimes[i] = -1;
        }
    }

    public void showTimes() {
        int timeIndex = 0;
        System.out.println("\nVertice -> Discovery time / End time");

        for (AbstractVertice vertice : relatedGraph.getVertices()) {
            System.out.print("  " + vertice.getRepresentation());
            System.out.print(" -> " + discoveryTimes[timeIndex]);
            System.out.println(" / " + endTimes[timeIndex]);
            timeIndex++;
        }
    }

    protected AbstractVertice getNextNotDiscoveredVertice() {
        int verticeIndex = 0;

        for (AbstractVertice vertice : relatedGraph.getVertices()) {
            if (discoveryTimes[verticeIndex++] == -1) {
                return vertice;
            }
        }

        return null;
    }

}