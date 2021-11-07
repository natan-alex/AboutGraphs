import java.util.Iterator;
import java.util.Stack;

public class DeepFirstSearch extends BaseSearchStructure {
    private final SuccessorAdjacencyList successorAdjacencyList;
    private final Stack<Iterator<Vertice>> discoveredVertices;
    private final Stack<Vertice> listHeads;
    private int verticeIndexInVerticeSet;
    private Vertice currentVertice;
    private Edge edgeToBeClassified; 

    public DeepFirstSearch(Graph graph, SuccessorAdjacencyList graphSuccessorAdjacencyList) {
        super(graph);
        successorAdjacencyList = graphSuccessorAdjacencyList;

        discoveredVertices = new Stack<>();
        listHeads = new Stack<>();

        performDeepSearchAndComputeTimesInArrays();
    }

    private void performDeepSearchAndComputeTimesInArrays() {
        for (int timeNumber = 1; timeNumber <= 2 * relatedGraph.numberOfVertices; timeNumber++) {
            if (discoveredVertices.isEmpty()) {
                currentVertice = getNextNotDiscoveredVerticeBasedOnVerticeSet();
                verticeIndexInVerticeSet = relatedGraph.verticesAndTheirIndices.get(currentVertice);
            } else {
                findNextNotDiscoveredVerticeInLastIteratorAndClassifyEdgesAlongTheWay();
            }

            if (discoveryTimes[verticeIndexInVerticeSet] == -1) {
                discoveryTimes[verticeIndexInVerticeSet] = timeNumber;

                discoveredVertices.push(successorAdjacencyList.adjacencyList.get(currentVertice).iterator());
                listHeads.push(currentVertice);
            } else if (!discoveredVertices.lastElement().hasNext()) {
                verticeIndexInVerticeSet = relatedGraph.verticesAndTheirIndices.get(listHeads.lastElement());

                if (endTimes[verticeIndexInVerticeSet] == -1) {
                    endTimes[verticeIndexInVerticeSet] = timeNumber;
                }

                if (!discoveredVertices.isEmpty()) {
                    discoveredVertices.pop();
                    listHeads.pop();
                }
            }
        }
    }

    private void findNextNotDiscoveredVerticeInLastIteratorAndClassifyEdgesAlongTheWay() {
        while (discoveredVertices.lastElement().hasNext() && discoveryTimes[verticeIndexInVerticeSet] != -1) {
            currentVertice = discoveredVertices.lastElement().next();
            verticeIndexInVerticeSet = relatedGraph.verticesAndTheirIndices.get(currentVertice);
            edgeToBeClassified = getEdgeThatContainsThisVertices(listHeads.lastElement(), currentVertice);

            if (edgeToBeClassified != null)
                classifyTheEdge(edgeToBeClassified);
        }
    }

    public boolean containsCycle() {
        for (var edgeClassification : edgeClassifications) {
            if (edgeClassification == Edge.EdgeClassifications.RETURN) {
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

    @Override
    public void showEdgeClassifications() {
        System.out.println("\n\tDEEP SEARCH EDGE CLASSIFICATIONS");
        super.showEdgeClassifications();
    }
}