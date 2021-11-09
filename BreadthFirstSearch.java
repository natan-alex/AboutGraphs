import java.util.ArrayDeque;
import java.util.Queue;

public class BreadthFirstSearch extends BaseSearchStructure {
    private final SuccessorAdjacencyList successorAdjacencyList;
    private final Queue<Vertice> verticesToBeExplored;
    private Vertice currentVertice;
    private int verticeIndexInVerticeSet;

    protected BreadthFirstSearch(Graph graph, SuccessorAdjacencyList graphSuccessorAdjacencyList) {
        super(graph);
        successorAdjacencyList = graphSuccessorAdjacencyList;

        verticesToBeExplored = new ArrayDeque<>(relatedGraph.numberOfVertices);

        performBreadthSearchAndComputeTimesInArrays();
    }

    private void performBreadthSearchAndComputeTimesInArrays() {
        for (int timeNumber = 1; timeNumber <= 2 * relatedGraph.numberOfVertices; timeNumber++) {
            if (verticesToBeExplored.isEmpty())
                currentVertice = getNextNotDiscoveredVerticeBasedOnVerticeSet();
            else
                currentVertice = verticesToBeExplored.poll();

            verticeIndexInVerticeSet = relatedGraph.verticesAndTheirIndices.get(currentVertice);

            if (discoveryTimes[verticeIndexInVerticeSet] == -1) {
                discoveryTimes[verticeIndexInVerticeSet] = timeNumber;
                
                addCurrentVerticeChildrenToNotExploredVertices();
                verticesToBeExplored.add(currentVertice);
            } else if (endTimes[verticeIndexInVerticeSet] == -1) {
                endTimes[verticeIndexInVerticeSet] = timeNumber;
            }
        }
    }

    private void addCurrentVerticeChildrenToNotExploredVertices() {
        for (Vertice vertice : successorAdjacencyList.adjacencyList.get(currentVertice)) {
            if (!verticesToBeExplored.contains(vertice) && discoveryTimes[relatedGraph.verticesAndTheirIndices.get(vertice)] == -1) {
                verticesToBeExplored.add(vertice);
            }
        }
    }

    @Override
    public void showTimes() {
        System.out.println("\n\tBREADTH SEARCH TIMES");
        super.showTimes();
    }
}