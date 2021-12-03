package searches;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import core.abstractions.AbstractTypedGraph;
import core.abstractions.AbstractVertice;
import representations.SuccessorAdjacencyList;

public class AStarSearch extends AbstractSearch {
    private final AbstractTypedGraph relatedGraph;
    private final Map<AbstractVertice, List<AbstractVertice>> successorAdjacencyList;
    private final Queue<AbstractVertice> verticesToBeExplored;
    private final GraphHeuristics relatedGraphHeuristics;

    protected AStarSearch(AbstractTypedGraph typedGraph, String heuristicsRepresentation) {
        super(typedGraph);
        relatedGraph = typedGraph;

        successorAdjacencyList = new SuccessorAdjacencyList(typedGraph).adjacencyList;

        verticesToBeExplored = new ArrayDeque<>(typedGraph.getNumberOfVertices());
        relatedGraphHeuristics = new GraphHeuristics(typedGraph, heuristicsRepresentation);

        performAStarSearchAndComputeTimesInArrays();
    }

    private void performAStarSearchAndComputeTimesInArrays() {
        AbstractVertice currentVertice;
        int verticeIndexInVerticeSet;

        for (int timeNumber = 1; timeNumber <= 2 * relatedGraph.getNumberOfVertices(); timeNumber++) {
            if (verticesToBeExplored.isEmpty())
                currentVertice = getNextNotDiscoveredVertice();
            else
                currentVertice = verticesToBeExplored.poll();

            verticeIndexInVerticeSet = relatedGraph.indexOfVertice(currentVertice);

            if (discoveryTimes[verticeIndexInVerticeSet] == -1) {
                discoveryTimes[verticeIndexInVerticeSet] = timeNumber;

                addVerticeChildrenToNotExploredVertices(currentVertice);

                verticesToBeExplored.add(currentVertice);
            } else if (endTimes[verticeIndexInVerticeSet] == -1) {
                endTimes[verticeIndexInVerticeSet] = timeNumber;
            }
        }
    }

    private void addVerticeChildrenToNotExploredVertices(AbstractVertice vertice) {
        VerticeComparatorInAStarSearch verticeComparator = new VerticeComparatorInAStarSearch(vertice, relatedGraph,
                relatedGraphHeuristics);
        var currentVerticeChildren = successorAdjacencyList.get(vertice);
        currentVerticeChildren.sort(verticeComparator);

        for (AbstractVertice v : currentVerticeChildren) {
            if (!verticesToBeExplored.contains(v) && discoveryTimes[relatedGraph.indexOfVertice(v)] == -1) {
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
