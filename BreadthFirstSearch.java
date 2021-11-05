import java.util.ArrayDeque;
import java.util.Queue;

public class BreadthFirstSearch extends BaseSearchStructure {
    private final SuccessorAdjacencyList successorAdjacencyList;
    private final Queue<Vertice> discoveredVertices;
    private Vertice currentVertice;
    private int verticeIndexInVerticeSet;

    protected BreadthFirstSearch(Graph graph, SuccessorAdjacencyList graphSuccessorAdjacencyList) {
        super(graph);
        successorAdjacencyList = graphSuccessorAdjacencyList;

        discoveredVertices = new ArrayDeque<>(relatedGraph.numberOfVertices);

        makeBreadthSearchAndComputeTimesInArrays();
    }

    private void makeBreadthSearchAndComputeTimesInArrays() {
        for (int timeNumber = 1; timeNumber <= 2 * relatedGraph.numberOfVertices; timeNumber++) {
            if (discoveredVertices.isEmpty()) {
                currentVertice = getNextNotDiscoveredVerticeBasedOnVerticeSet();
                verticeIndexInVerticeSet = currentVertice != null ? relatedGraph.vertices.get(currentVertice) : -1;
            } else {
                currentVertice = discoveredVertices.poll();
                verticeIndexInVerticeSet = relatedGraph.vertices.get(currentVertice);
            }

            if (discoveryTimes[verticeIndexInVerticeSet] == -1) {
                discoveryTimes[verticeIndexInVerticeSet] = timeNumber;
                
                addNecessaryItemsFromCurrentVerticeAdjacencyListToDiscoveredVertices();

                discoveredVertices.add(currentVertice);
            } else if (endTimes[verticeIndexInVerticeSet] == -1) {
                endTimes[verticeIndexInVerticeSet] = timeNumber;
            }
        }
    }

    private void addNecessaryItemsFromCurrentVerticeAdjacencyListToDiscoveredVertices() {
        for (Vertice vertice : successorAdjacencyList.adjacencyList.get(currentVertice)) {
            if (!discoveredVertices.contains(vertice) && discoveryTimes[relatedGraph.vertices.get(vertice)] == -1) {
                discoveredVertices.add(vertice);
            }
        }
    }

    @Override
    public void showTimes() {
        System.out.println("\n\tBREADTH SEARCH TIMES");
        super.showTimes();
    }
}