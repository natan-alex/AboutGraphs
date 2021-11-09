import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Collections;
import java.util.Comparator;

public class BreadthFirstSearch extends BaseSearchStructure {
    private final SuccessorAdjacencyList successorAdjacencyList;
    private final Queue<Vertice> verticesToBeExplored;
    private Vertice currentVertice;
    private int verticeIndexInVerticeSet;
    private final VerticeComparatorInBFS verticeComparatorInBFS;

    private final class VerticeComparatorInBFS implements Comparator<Vertice> {
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
                evaluationFunctionValue = edgeThatContainsTheVertices.value;
            }

            return evaluationFunctionValue;
        }
    }

    protected BreadthFirstSearch(Graph graph, SuccessorAdjacencyList graphSuccessorAdjacencyList) {
        super(graph);
        successorAdjacencyList = graphSuccessorAdjacencyList;

        verticesToBeExplored = new ArrayDeque<>(relatedGraph.numberOfVertices);
        verticeComparatorInBFS = new VerticeComparatorInBFS();

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
        var currentVerticeChildren = successorAdjacencyList.adjacencyList.get(currentVertice);
        Collections.sort(currentVerticeChildren, verticeComparatorInBFS);

        for (Vertice vertice : currentVerticeChildren) {
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