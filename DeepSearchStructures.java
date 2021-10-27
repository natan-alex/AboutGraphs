import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

public class DeepSearchStructures {
    public final int[] discoveryTimes;
    public final int[] endTimes;
    public final Edge.EdgeClassifications[] edgeClassifications;
    private final Graph relatedGraph;
    private final SuccessorAdjacencyList successorAdjacencyList;

    public DeepSearchStructures(Graph graph, SuccessorAdjacencyList graphSuccessorAdjacencyList) {
        relatedGraph = graph;
        successorAdjacencyList = graphSuccessorAdjacencyList;

        discoveryTimes = new int[graph.numberOfVertices];
        endTimes = new int[graph.numberOfVertices];
        edgeClassifications = new Edge.EdgeClassifications[graph.numberOfEdges];

        for (int i = 0; i < graph.numberOfVertices; i++) {
            discoveryTimes[i] = -1;
            endTimes[i] = -1;
        }

        makeDeepSearchAndComputeTimesInArrays();
    }

    public void showTimes() {
        System.out.print("Discovery times: [ ");

        for (int discoveryTime : discoveryTimes)
            System.out.print(discoveryTime + " ");

        System.out.println("]");

        System.out.print("End times: [ ");

        for (int endTime : endTimes)
            System.out.print(endTime + " ");

        System.out.println("]");
    }

    public void makeDeepSearchAndComputeTimesInArrays() {
        Vertice currentVertice = successorAdjacencyList.adjacencyList.get(0).get(0);
        int verticeIndexInVerticeSet = relatedGraph.vertices.get(currentVertice);
        Stack<Iterator<Vertice>> discoveredVertices = new Stack<>();
        Stack<Vertice> listHeads = new Stack<>();
        Edge edgeToBeClassified = null;

        for (int timeNumber = 1; timeNumber <= 2 * relatedGraph.numberOfVertices; timeNumber++) {
            if (discoveryTimes[verticeIndexInVerticeSet] == -1) {
                discoveryTimes[verticeIndexInVerticeSet] = timeNumber;

                discoveredVertices.push(successorAdjacencyList.getInnerListWithThisHead(currentVertice).iterator());
                listHeads.push(discoveredVertices.lastElement().next());
            } else if (!discoveredVertices.lastElement().hasNext()) {
                verticeIndexInVerticeSet = relatedGraph.vertices.get(listHeads.lastElement());

                if (endTimes[verticeIndexInVerticeSet] == -1) {
                    endTimes[verticeIndexInVerticeSet] = timeNumber;
                }

                if (!discoveredVertices.isEmpty()) {
                    discoveredVertices.pop();
                    listHeads.pop();
                }
            }

            if (discoveredVertices.isEmpty()) {
                currentVertice = getNextNotDiscoveredVerticeBasedOnVerticeSet();
                verticeIndexInVerticeSet = currentVertice == null ? -1 : relatedGraph.vertices.get(currentVertice);
            } else {
                while (discoveredVertices.lastElement().hasNext() && discoveryTimes[verticeIndexInVerticeSet] != -1) {
                    currentVertice = discoveredVertices.lastElement().next();
                    verticeIndexInVerticeSet = relatedGraph.vertices.get(currentVertice);
                    edgeToBeClassified = getEdgeThatContainsThisVertices(listHeads.lastElement(), currentVertice);

                    if (edgeToBeClassified != null)
                        classifyTheEdge(edgeToBeClassified);
                }
            }
        }
    }

    private Edge getEdgeThatContainsThisVertices(Vertice firstVertice, Vertice secondVertice) {
        for (Map.Entry<Edge, Integer> entry : relatedGraph.edges.entrySet()) {
            if (entry.getKey().firstVertice.name.compareTo(firstVertice.name) == 0
                    && entry.getKey().secondVertice.name.compareTo(secondVertice.name) == 0) {
                return entry.getKey();
            }
        }

        return null;
    }

    private Vertice getNextNotDiscoveredVerticeBasedOnVerticeSet() {
        for (Map.Entry<Vertice, Integer> entry : relatedGraph.vertices.entrySet()) {
            if (discoveryTimes[entry.getValue()] == -1) {
                return entry.getKey();
            }
        }

        return null;
    }

    public void classifyTheEdge(Edge edge) {
        int edgeIndex = relatedGraph.edges.get(edge);
        int indexOfFirstVerticeInVerticeSet = relatedGraph.vertices.get(edge.firstVertice);
        int indexOfSecondVerticeInVerticeSet = relatedGraph.vertices.get(edge.secondVertice);

        if (edge.isDirected) {
            if (discoveryTimes[indexOfFirstVerticeInVerticeSet] != -1
                    && discoveryTimes[indexOfSecondVerticeInVerticeSet] == -1) {
                edgeClassifications[edgeIndex] = Edge.EdgeClassifications.TREE;
            } else if (discoveryTimes[indexOfFirstVerticeInVerticeSet] < discoveryTimes[indexOfSecondVerticeInVerticeSet]
                    && endTimes[indexOfSecondVerticeInVerticeSet] != -1) {
                edgeClassifications[edgeIndex] = Edge.EdgeClassifications.ADVANCE;
            } else if (discoveryTimes[indexOfFirstVerticeInVerticeSet] > discoveryTimes[indexOfSecondVerticeInVerticeSet]
                    && endTimes[indexOfSecondVerticeInVerticeSet] == -1) {
                edgeClassifications[edgeIndex] = Edge.EdgeClassifications.RETURN;
            } else {
                edgeClassifications[edgeIndex] = Edge.EdgeClassifications.CROSSING;
            }
        } else {
            if (discoveryTimes[indexOfFirstVerticeInVerticeSet] != -1
                    && discoveryTimes[indexOfSecondVerticeInVerticeSet] == -1) {
                edgeClassifications[edgeIndex] = Edge.EdgeClassifications.TREE;
            } else {
                edgeClassifications[edgeIndex] = Edge.EdgeClassifications.RETURN;
            }
        }
    }
}