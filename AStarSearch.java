import java.util.Comparator;
import java.util.PriorityQueue;

public class AStarSearch extends BaseSearchStructure {
    private final SuccessorAdjacencyList successorAdjacencyList;
    private final PriorityQueue<Vertice> discoveredVertices;
    private final GraphHeuristics relatedGraphHeuristics;
    private Vertice currentVertice;
    private int verticeIndexInVerticeSet;

    private final class VerticeComparatorInAStarSearch implements Comparator<Vertice> {
        @Override
        public int compare(Vertice arg0, Vertice arg1) {
            Edge edgeWithCurrentVerticeAndArg0 = getEdgeThatContainsThisVertices(currentVertice, arg0);
            Edge edgeWithCurrentVerticeAndArg1 = getEdgeThatContainsThisVertices(currentVertice, arg1);
            float evaluationFunctionValueForArg0 = relatedGraphHeuristics.heuristics.get(arg0);
            float evaluationFunctionValueForArg1 = relatedGraphHeuristics.heuristics.get(arg1);

            if (edgeWithCurrentVerticeAndArg0 != null) {
                evaluationFunctionValueForArg0 += edgeWithCurrentVerticeAndArg0.value;
            }

            if (edgeWithCurrentVerticeAndArg1 != null) {
                evaluationFunctionValueForArg1 += edgeWithCurrentVerticeAndArg1.value;
            }

            return Float.compare(evaluationFunctionValueForArg0, evaluationFunctionValueForArg1);
        }
    }

    protected AStarSearch(Graph graph, SuccessorAdjacencyList graphSuccessorAdjacencyList,
        GraphHeuristics graphHeuristics) {
        super(graph);

        successorAdjacencyList = graphSuccessorAdjacencyList;

        discoveredVertices = new PriorityQueue<>(graph.numberOfVertices, new VerticeComparatorInAStarSearch());
        relatedGraphHeuristics = graphHeuristics;

        performAStarSearchAndComputeTimesInArrays();
    }   

    private void performAStarSearchAndComputeTimesInArrays() {
        for (int timeNumber = 1; timeNumber <= 2 * relatedGraph.numberOfVertices; timeNumber++) {
            if (discoveredVertices.isEmpty()) {
                currentVertice = getNextNotDiscoveredVerticeBasedOnVerticeSet();
                verticeIndexInVerticeSet = currentVertice != null ? relatedGraph.verticesAndTheirIndices.get(currentVertice) : -1;
            } else {
                currentVertice = discoveredVertices.poll();
                verticeIndexInVerticeSet = relatedGraph.verticesAndTheirIndices.get(currentVertice);
            }

            if (discoveryTimes[verticeIndexInVerticeSet] == -1) {
                discoveryTimes[verticeIndexInVerticeSet] = timeNumber;
                
                addNecessaryItemsFromCurrentVerticeAdjacencyListToDiscoveredVertices();
            } else if (endTimes[verticeIndexInVerticeSet] == -1) {
                endTimes[verticeIndexInVerticeSet] = timeNumber;
            }
        }
    }

    private void addNecessaryItemsFromCurrentVerticeAdjacencyListToDiscoveredVertices() {
        for (Vertice vertice : successorAdjacencyList.adjacencyList.get(currentVertice)) {
            if (!discoveredVertices.contains(vertice) && discoveryTimes[relatedGraph.verticesAndTheirIndices.get(vertice)] == -1) {
                discoveredVertices.add(vertice);
            }
        }
    }
}
