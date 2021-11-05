import java.util.PriorityQueue;

public class AStarSearch extends BaseSearchStructure {
    private final SuccessorAdjacencyList successorAdjacencyList;
    private final PriorityQueue<Vertice> discoveredVertices;
    private final GraphHeuristics heuristics;
    private Vertice currentVertice;
    private int verticeIndexInVerticeSet;

    protected AStarSearch(Graph graph, SuccessorAdjacencyList graphSuccessorAdjacencyList,
        GraphHeuristics graphHeuristics) {
        super(graph);

        successorAdjacencyList = graphSuccessorAdjacencyList;

        discoveredVertices = new PriorityQueue<>(graph.numberOfVertices);
        heuristics = graphHeuristics;

        makeAStarSearchAndComputeTimesInArrays();
    }   

    private void makeAStarSearchAndComputeTimesInArrays() {
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
}
