import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class DeepFirstSearch extends BaseSearchStructure {
    private final Map<Vertice, List<Vertice>> successorAdjacencyList;
    private final Graph relatedGraph;
    private Stack<Vertice> verticesToBeExplored;

    public DeepFirstSearch(Graph graph, Map<Vertice, List<Vertice>> successorAdjacencyList) {
        super(graph);
        this.successorAdjacencyList = successorAdjacencyList;
        relatedGraph = graph;
    }

    public Vertice[] getThePathBetweenTheseVertices(String startVerticeName, String endVerticeName)
            throws IllegalArgumentException {

        Vertice startVertice = relatedGraph.getTheVerticeWithThisName(startVerticeName);
        Vertice endVertice = relatedGraph.getTheVerticeWithThisName(endVerticeName);

        throwExceptionIfVerticesAreNull(startVertice, endVertice);

        return getThePathBetweenTheseVertices(startVertice, endVertice);
    }

    private void throwExceptionIfVerticesAreNull(Vertice startVertice, Vertice endVertice)
            throws IllegalArgumentException {
        if (startVertice == null || endVertice == null) {
            throw new IllegalArgumentException();
        }
    }

    private Vertice[] getThePathBetweenTheseVertices(Vertice startVertice, Vertice endVertice) {
        int timeNumber = 1, verticeIndexInVerticeSet;
        Vertice currentVertice;
        Set<Vertice> pathBetweenVertices = new LinkedHashSet<>();

        verticesToBeExplored = new Stack<>();
        verticesToBeExplored.add(startVertice);

        do {
            currentVertice = verticesToBeExplored.pop();
            verticeIndexInVerticeSet = relatedGraph.vertices.indexOf(currentVertice);

            if (discoveryTimes[verticeIndexInVerticeSet] == -1) {
                discoveryTimes[verticeIndexInVerticeSet] = timeNumber++;

                pathBetweenVertices.add(currentVertice);
                verticesToBeExplored.add(currentVertice);
                addVerticeChildrenToNotExploredVertices(currentVertice);
            } else {
                endTimes[verticeIndexInVerticeSet] = timeNumber++;
            }
        } while (!currentVertice.equals(endVertice) && !verticesToBeExplored.isEmpty());

        Vertice[] path = new Vertice[pathBetweenVertices.size()];
        return pathBetweenVertices.toArray(path);
    }

    public void computeTimes() {
        Vertice currentVertice;
        int verticeIndexInVerticeSet;

        for (int timeNumber = 1; timeNumber <= 2 * relatedGraph.numberOfVertices; timeNumber++) {
            if (verticesToBeExplored.isEmpty())
                currentVertice = getNextNotDiscoveredVerticeBasedOnVerticeSet();
            else
                currentVertice = verticesToBeExplored.pop();

            verticeIndexInVerticeSet = relatedGraph.vertices.indexOf(currentVertice);

            if (discoveryTimes[verticeIndexInVerticeSet] == -1) {
                discoveryTimes[verticeIndexInVerticeSet] = timeNumber;

                verticesToBeExplored.add(currentVertice);
                addVerticeChildrenToNotExploredVerticesAndClassifyEdgesAlongTheWay(currentVertice);
            } else {
                endTimes[verticeIndexInVerticeSet] = timeNumber;
            }
        }
    }

    private void addVerticeChildrenToNotExploredVertices(Vertice vertice) {
        var verticeChildren = successorAdjacencyList.get(vertice);
        Collections.reverse(verticeChildren);

        for (Vertice v : verticeChildren) {
            if (canAddVerticeToNotExploredVertices(v))
                verticesToBeExplored.add(v);
        }
    }

    private void addVerticeChildrenToNotExploredVerticesAndClassifyEdgesAlongTheWay(Vertice vertice) {
        var verticeChildren = successorAdjacencyList.get(vertice);
        Collections.reverse(verticeChildren);

        for (Vertice v : verticeChildren) {
            if (canAddVerticeToNotExploredVertices(v))
                verticesToBeExplored.add(v);

            classifyTheEdge(relatedGraph.getEdgeThatContainsThisVertices(vertice, v));
        }
    }

    private boolean canAddVerticeToNotExploredVertices(Vertice vertice) {
        return discoveryTimes[relatedGraph.vertices.indexOf(vertice)] == -1 && !verticesToBeExplored.contains(vertice);
    }

    public boolean containsCycle() {
        for (var edgeClassification : edgeClassifications) {
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

    @Override
    public void showEdgeClassifications() {
        System.out.println("\n\tDEEP SEARCH EDGE CLASSIFICATIONS\n");
        super.showEdgeClassifications();
    }
}