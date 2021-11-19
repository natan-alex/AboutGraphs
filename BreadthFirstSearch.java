import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class BreadthFirstSearch extends BaseSearchStructure {
    private final Map<Vertice, List<Vertice>> successorAdjacencyList;
    private final Queue<Vertice> verticesToBeExplored;

    protected BreadthFirstSearch(Graph graph, Map<Vertice, List<Vertice>> graphSuccessorAdjacencyList) {
        super(graph);
        successorAdjacencyList = graphSuccessorAdjacencyList;

        verticesToBeExplored = new ArrayDeque<>(relatedGraph.numberOfVertices);

        performBreadthSearchAndComputeTimesInArrays();
    }

    private void performBreadthSearchAndComputeTimesInArrays() {
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
        VerticeComparatorInBFS verticeComparator = new VerticeComparatorInBFS(vertice, relatedGraph);
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
        System.out.println("\n\tBREADTH SEARCH TIMES");
        super.showTimes();
    }
}