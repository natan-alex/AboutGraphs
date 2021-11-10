import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Collections;
import java.util.Queue;

public class AStarSearch extends BaseSearchStructure {
    private final SuccessorAdjacencyList successorAdjacencyList;
    private final Queue<Vertice> verticesToBeExplored;
    private final GraphHeuristics relatedGraphHeuristics;
    private Vertice currentVertice;
    private int verticeIndexInVerticeSet;
    private final VerticeComparatorInAStarSearch verticeComparatorInAStarSearch;

    private final class VerticeComparatorInAStarSearch implements Comparator<Vertice> {
        @Override
        public int compare(Vertice arg0, Vertice arg1) {
            float evaluationFunctionValueForArg0 = getEvaluationFunctionValueForVertices(currentVertice, arg0);
            float evaluationFunctionValueForArg1 = getEvaluationFunctionValueForVertices(currentVertice, arg1);

            return Float.compare(evaluationFunctionValueForArg0, evaluationFunctionValueForArg1);
        }

        private float getEvaluationFunctionValueForVertices(Vertice firstVertice, Vertice secondVertice) {
            Edge edgeThatContainsTheVertices = getEdgeThatContainsThisVertices(firstVertice, secondVertice);
            float evaluationFunctionValue = Float.MAX_VALUE;

            if (edgeThatContainsTheVertices != null) {
                evaluationFunctionValue = edgeThatContainsTheVertices.value + 
                    relatedGraphHeuristics.verticesAndTheirHeuristics.get(secondVertice);
            }

            return evaluationFunctionValue;
        }
    }

    protected AStarSearch(Graph graph, SuccessorAdjacencyList graphSuccessorAdjacencyList,
        GraphHeuristics graphHeuristics) {
        super(graph);

        successorAdjacencyList = graphSuccessorAdjacencyList;

        verticesToBeExplored = new ArrayDeque<>(graph.numberOfVertices);
        relatedGraphHeuristics = graphHeuristics;
        verticeComparatorInAStarSearch = new VerticeComparatorInAStarSearch();

        performAStarSearchAndComputeTimesInArrays();
    }   

    private void performAStarSearchAndComputeTimesInArrays() {
        for (int timeNumber = 1; timeNumber <= 2 * relatedGraph.numberOfVertices; timeNumber++) {
            fillCurrentVerticeAndItsIndexAccordingToVerticesToBeExplored();

            if (discoveryTimes[verticeIndexInVerticeSet] == -1) {
                discoveryTimes[verticeIndexInVerticeSet] = timeNumber;
                
                addCurrentVerticeChildrenToNotExploredVertices();

                verticesToBeExplored.add(currentVertice);
            } else if (endTimes[verticeIndexInVerticeSet] == -1) {
                endTimes[verticeIndexInVerticeSet] = timeNumber;
            }
        }
    }

    private void fillCurrentVerticeAndItsIndexAccordingToVerticesToBeExplored() {
        if (verticesToBeExplored.isEmpty())
            currentVertice = getNextNotDiscoveredVerticeBasedOnVerticeSet();
        else
            currentVertice = verticesToBeExplored.poll();

        verticeIndexInVerticeSet = relatedGraph.verticesAndTheirIndices.get(currentVertice);
    }

    private void addCurrentVerticeChildrenToNotExploredVertices() {
        var currentVerticeChildren = successorAdjacencyList.adjacencyList.get(currentVertice);
        Collections.sort(currentVerticeChildren, verticeComparatorInAStarSearch);

        for (Vertice vertice : currentVerticeChildren) {
            if (!verticesToBeExplored.contains(vertice) && discoveryTimes[relatedGraph.verticesAndTheirIndices.get(vertice)] == -1) {
                verticesToBeExplored.add(vertice);
            }
        }
    }
    
    @Override
    public void showTimes() {
        System.out.println("\n\tA* SEARCH TIMES");
        super.showTimes();
    }
}
