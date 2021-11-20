import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class BreadthFirstSearch extends BaseSearchStructure {
    private final Map<Vertice, List<Vertice>> successorAdjacencyList;
    private Queue<Vertice> verticesToBeExplored;

    protected BreadthFirstSearch(Graph graph, Map<Vertice, List<Vertice>> graphSuccessorAdjacencyList) {
        super(graph);
        successorAdjacencyList = graphSuccessorAdjacencyList;
    }

    public void computeTimes() {
        Vertice currentVertice;
        int verticeIndexInVerticeSet;

        verticesToBeExplored = new ArrayDeque<>(relatedGraph.numberOfVertices);

        for (int timeNumber = 1; timeNumber <= 2 * relatedGraph.numberOfVertices; timeNumber++) {
            currentVertice = getNextNotExploredVertice();
            verticeIndexInVerticeSet = relatedGraph.vertices.indexOf(currentVertice);

            if (discoveryTimes[verticeIndexInVerticeSet] == -1) {
                discoveryTimes[verticeIndexInVerticeSet] = timeNumber;

                addVerticeChildrenToNotExploredVertices(currentVertice);
                verticesToBeExplored.add(currentVertice);
            } else if (endTimes[verticeIndexInVerticeSet] == -1) {
                endTimes[verticeIndexInVerticeSet] = timeNumber;
            }
        }
    }

    public List<Vertice> getPathBetweenVertices(String startVerticeName, String endVerticeName)
            throws IllegalArgumentException {

        Vertice startVertice = relatedGraph.getTheVerticeWithThisName(startVerticeName);
        Vertice endVertice = relatedGraph.getTheVerticeWithThisName(endVerticeName);

        throwExceptionIfVerticeIsNull(startVertice, startVerticeName);
        throwExceptionIfVerticeIsNull(endVertice, endVerticeName);

        return getPathBetweenVertices(startVertice, endVertice);
    }

    private void throwExceptionIfVerticeIsNull(Vertice vertice, String verticeName)
            throws IllegalArgumentException {
        if (vertice == null) {
            throw new IllegalArgumentException("The vertice  " + verticeName + "  is not in the vertice set."
                    + "\nThe vertice set is: " + relatedGraph.vertices);
        }
    }

    private List<Vertice> getPathBetweenVertices(Vertice startVertice, Vertice endVertice) {
        int timeNumber = 1, verticeIndexInVerticeSet;
        Vertice currentVertice;
        List<Vertice> pathBetweenVertices = new ArrayList<>();

        verticesToBeExplored = new ArrayDeque<>(relatedGraph.numberOfVertices);
        verticesToBeExplored.add(startVertice);

        do {
            currentVertice = verticesToBeExplored.poll();
            verticeIndexInVerticeSet = relatedGraph.vertices.indexOf(currentVertice);

            if (discoveryTimes[verticeIndexInVerticeSet] == -1) {
                discoveryTimes[verticeIndexInVerticeSet] = timeNumber++;

                addVerticeChildrenToNotExploredVertices(currentVertice);
                verticesToBeExplored.add(currentVertice);
                pathBetweenVertices.add(currentVertice);
            } else if (endTimes[verticeIndexInVerticeSet] == -1) {
                endTimes[verticeIndexInVerticeSet] = timeNumber++;
                pathBetweenVertices.remove(currentVertice);
            }
        } while (!currentVertice.equals(endVertice) && !verticesToBeExplored.isEmpty());

        return pathBetweenVertices;
    }

    private Vertice getNextNotExploredVertice() {
        if (verticesToBeExplored.isEmpty())
            return getNextNotDiscoveredVerticeBasedOnVerticeSet();
        else
            return verticesToBeExplored.poll();
    }

    private void addVerticeChildrenToNotExploredVertices(Vertice vertice) {
        VerticeComparatorInBFS verticeComparator = new VerticeComparatorInBFS(vertice, relatedGraph);
        var currentVerticeChildren = successorAdjacencyList.get(vertice);
        Collections.sort(currentVerticeChildren, verticeComparator);

        for (Vertice v : currentVerticeChildren) {
            if (canAddVerticeToNotExploredVertices(v)) {
                verticesToBeExplored.add(v);
            }
        }
    }

    private boolean canAddVerticeToNotExploredVertices(Vertice vertice) {
        return discoveryTimes[relatedGraph.vertices.indexOf(vertice)] == -1 && !verticesToBeExplored.contains(vertice);
    }

    @Override
    public void showTimes() {
        System.out.println("\n\tBREADTH SEARCH TIMES");
        super.showTimes();
    }
}