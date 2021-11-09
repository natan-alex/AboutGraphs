import java.util.Collections;
import java.util.Stack;

public class DeepFirstSearch extends BaseSearchStructure {
    private final SuccessorAdjacencyList successorAdjacencyList;
    private final Stack<Vertice> verticesToBeExplored;
    private int verticeIndexInVerticeSet;
    private Vertice currentVertice;

    public DeepFirstSearch(Graph graph, SuccessorAdjacencyList graphSuccessorAdjacencyList) {
        super(graph);
        successorAdjacencyList = graphSuccessorAdjacencyList;

        verticesToBeExplored = new Stack<>();

        performDeepSearchAndComputeTimesInArrays();
    }

    private void performDeepSearchAndComputeTimesInArrays() {
        for (int timeNumber = 1; timeNumber <= 2 * relatedGraph.numberOfVertices; timeNumber++) {
            if (verticesToBeExplored.isEmpty())
                currentVertice = getNextNotDiscoveredVerticeBasedOnVerticeSet();
            else
                currentVertice = verticesToBeExplored.pop();

            verticeIndexInVerticeSet = relatedGraph.verticesAndTheirIndices.get(currentVertice);

            if (discoveryTimes[verticeIndexInVerticeSet] == -1) {
                discoveryTimes[verticeIndexInVerticeSet] = timeNumber;

                verticesToBeExplored.add(currentVertice);
                addCurrentVerticeChildrenToNotExploredVerticesAndClassifyEdgesAlongTheWay();
            } else {
                endTimes[verticeIndexInVerticeSet] = timeNumber;
            }
        }
    }

    private void addCurrentVerticeChildrenToNotExploredVerticesAndClassifyEdgesAlongTheWay() {
        var currentVerticeChildren = successorAdjacencyList.adjacencyList.get(currentVertice);
        Collections.reverse(currentVerticeChildren);

        for (Vertice vertice : currentVerticeChildren) {
            if (canAddVerticeToDiscoveredVertices(vertice))
                verticesToBeExplored.add(vertice);

            edgeClassifier.classifyTheEdge(getEdgeThatContainsThisVertices(currentVertice, vertice));
        }
    }

    private boolean canAddVerticeToDiscoveredVertices(Vertice vertice) {
        return discoveryTimes[relatedGraph.verticesAndTheirIndices.get(vertice)] == -1
                && !verticesToBeExplored.contains(vertice);
    }

    public boolean containsCycle() {
        for (var edgeClassification : edgeClassifier.edgeClassifications) {
            if (edgeClassification == EdgeClassifications.RETURN) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void showTimes() {
        System.out.println("\n\tDEEP SEARCH TIMES");
        super.showTimes();
    }

    public void showEdgeClassifications() {
        System.out.println("\n\tDEEP SEARCH EDGE CLASSIFICATIONS\n");
        edgeClassifier.showEdgeClassifications();
    }
}