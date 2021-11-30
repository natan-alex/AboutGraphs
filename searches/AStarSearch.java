package searches;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Map;
import java.util.Collections;
import java.util.Queue;

import core.*;
import representations.SuccessorAdjacencyList;

public class AStarSearch extends BaseSearchStructure {
    private final Map<Vertice, List<Vertice>> successorAdjacencyList;
    private final Queue<Vertice> verticesToBeExplored;
    private final GraphHeuristics relatedGraphHeuristics;

    protected AStarSearch(Graph graph, String heuristicsRepresentation) {
        super(graph);

        successorAdjacencyList = new SuccessorAdjacencyList(graph).adjacencyList;

        verticesToBeExplored = new ArrayDeque<>(graph.numberOfVertices);
        relatedGraphHeuristics = new GraphHeuristics(graph, heuristicsRepresentation);

        performAStarSearchAndComputeTimesInArrays();
    }

    private void performAStarSearchAndComputeTimesInArrays() {
        Vertice currentVertice;
        int verticeIndexInVerticeSet;

        for (int timeNumber = 1; timeNumber <= 2 * relatedGraph.numberOfVertices; timeNumber++) {
            if (verticesToBeExplored.isEmpty())
                currentVertice = getNextNotDiscoveredVerticeBasedOnVerticeSet();
            else
                currentVertice = verticesToBeExplored.poll();

            verticeIndexInVerticeSet = relatedGraph.vertices.indexOf(currentVertice);

            if (discoveryTimes[verticeIndexInVerticeSet] == -1) {
                discoveryTimes[verticeIndexInVerticeSet] = timeNumber;

                addVerticeChildrenToNotExploredVertices(currentVertice);

                verticesToBeExplored.add(currentVertice);
            } else if (endTimes[verticeIndexInVerticeSet] == -1) {
                endTimes[verticeIndexInVerticeSet] = timeNumber;
            }
        }
    }

    private void addVerticeChildrenToNotExploredVertices(Vertice vertice) {
        VerticeComparatorInAStarSearch verticeComparator = new VerticeComparatorInAStarSearch(vertice, relatedGraph,
                relatedGraphHeuristics);
        var currentVerticeChildren = successorAdjacencyList.get(vertice);
        Collections.sort(currentVerticeChildren, verticeComparator);

        for (Vertice v : currentVerticeChildren) {
            if (!verticesToBeExplored.contains(v) && discoveryTimes[relatedGraph.vertices.indexOf(v)] == -1) {
                verticesToBeExplored.add(v);
            }
        }
    }

    @Override
    public void showTimes() {
        System.out.println("\n\tA* SEARCH TIMES");
        super.showTimes();
    }
}
