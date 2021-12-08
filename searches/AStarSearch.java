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
    private Queue<AbstractVertice> verticesToBeExplored;
    private final GraphHeuristics relatedGraphHeuristics;

    protected AStarSearch(AbstractTypedGraph typedGraph, String heuristicsRepresentation) {
        super(typedGraph);
        relatedGraph = typedGraph;

        successorAdjacencyList = new SuccessorAdjacencyList(typedGraph).adjacencyList;
        relatedGraphHeuristics = new GraphHeuristics(typedGraph, heuristicsRepresentation);
    }

    @Override
    public void computeTimes() {
        verticesToBeExplored = new ArrayDeque<>(relatedGraph.getNumberOfVertices());
        AbstractVertice currentVertice;
        int verticeIndexInVerticeSet;

        for (int timeNumber = 1; timeNumber <= 2 * relatedGraph.getNumberOfVertices(); timeNumber++) {
            currentVertice = getNextNotExploredVertice();
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

    private AbstractVertice getNextNotExploredVertice() {
        if (verticesToBeExplored.isEmpty())
            return getNextNotDiscoveredVertice();
        else
            return verticesToBeExplored.poll();
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
