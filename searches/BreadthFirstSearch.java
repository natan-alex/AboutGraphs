package searches;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import core.*;
import core.abstractions.AbstractTypedGraph;
import core.abstractions.AbstractVertice;
import representations.SuccessorAdjacencyList;

public class BreadthFirstSearch extends AbstractSearch {
    private final AbstractTypedGraph relatedGraph;
    private final Map<AbstractVertice, List<AbstractVertice>> successorAdjacencyList;
    private Queue<AbstractVertice> verticesToBeExplored;

    protected BreadthFirstSearch(TypedGraph typedGraph) {
        super(typedGraph);
        relatedGraph = typedGraph;
        successorAdjacencyList = new SuccessorAdjacencyList(typedGraph).adjacencyList;
    }

    public void computeTimes() {
        AbstractVertice currentVertice;
        int verticeIndexInVerticeSet;

        verticesToBeExplored = new ArrayDeque<>(relatedGraph.getNumberOfVertices());

        for (int timeNumber = 1; timeNumber <= 2 * relatedGraph.getNumberOfVertices(); timeNumber++) {
            currentVertice = getNextNotExploredVertice();
            verticeIndexInVerticeSet = relatedGraph.indexOfVertice(currentVertice);

            if (discoveryTimes[verticeIndexInVerticeSet] == -1) {
                discoveryTimes[verticeIndexInVerticeSet] = timeNumber;

                addVerticeChildrenToNotExploredVertices(currentVertice);
                verticesToBeExplored.add(currentVertice);
            } else if (endTimes[verticeIndexInVerticeSet] == -1) {
                endTimes[verticeIndexInVerticeSet] = timeNumber;
            }
        }
    }

    // public List<Vertice> getPathBetweenVertices(String startVerticeName, String
    // endVerticeName)
    // throws IllegalArgumentException {

    // Vertice startVertice = relatedGraph.getVerticeByName(startVerticeName);
    // Vertice endVertice = relatedGraph.getVerticeByName(endVerticeName);

    // throwExceptionIfVerticeIsNull(startVertice, startVerticeName);
    // throwExceptionIfVerticeIsNull(endVertice, endVerticeName);

    // return getPathBetweenVertices(startVertice, endVertice);
    // }

    // private void throwExceptionIfVerticeIsNull(Vertice vertice, String
    // verticeName) throws IllegalArgumentException {
    // if (vertice == null) {
    // throw new IllegalArgumentException("The vertice " + verticeName + " is not in
    // the vertice set."
    // + "\nThe vertice set is: " + relatedGraph.vertices);
    // }
    // }

    // private List<Vertice> getPathBetweenVertices(Vertice startVertice, Vertice
    // endVertice) {
    // int timeNumber = 1, verticeIndexInVerticeSet;
    // Vertice currentVertice;
    // List<Vertice> pathBetweenVertices = new ArrayList<>();

    // verticesToBeExplored = new ArrayDeque<>(relatedGraph.getNumberOfVertices());
    // verticesToBeExplored.add(startVertice);

    // do {
    // currentVertice = verticesToBeExplored.poll();
    // verticeIndexInVerticeSet = relatedGraph.indexOfVertice(currentVertice);

    // if (discoveryTimes[verticeIndexInVerticeSet] == -1) {
    // discoveryTimes[verticeIndexInVerticeSet] = timeNumber++;

    // addVerticeChildrenToNotExploredVertices(currentVertice);
    // verticesToBeExplored.add(currentVertice);
    // pathBetweenVertices.add(currentVertice);
    // } else if (endTimes[verticeIndexInVerticeSet] == -1) {
    // endTimes[verticeIndexInVerticeSet] = timeNumber++;
    // pathBetweenVertices.remove(currentVertice);
    // }
    // } while (!currentVertice.equals(endVertice) &&
    // !verticesToBeExplored.isEmpty());

    // return pathBetweenVertices;
    // }

    private AbstractVertice getNextNotExploredVertice() {
        if (verticesToBeExplored.isEmpty())
            return getNextNotDiscoveredVertice();
        else
            return verticesToBeExplored.poll();
    }

    private void addVerticeChildrenToNotExploredVertices(AbstractVertice vertice) {
        VerticeComparatorInBFS verticeComparator = new VerticeComparatorInBFS(vertice, relatedGraph);
        var currentVerticeChildren = successorAdjacencyList.get(vertice);
        Collections.sort(currentVerticeChildren, verticeComparator);

        for (AbstractVertice v : currentVerticeChildren) {
            if (canAddVerticeToNotExploredVertices(v)) {
                verticesToBeExplored.add(v);
            }
        }
    }

    private boolean canAddVerticeToNotExploredVertices(AbstractVertice vertice) {
        return discoveryTimes[relatedGraph.indexOfVertice(vertice)] == -1 && !verticesToBeExplored.contains(vertice);
    }

    @Override
    public void showTimes() {
        System.out.println("\n\tBREADTH SEARCH TIMES");
        super.showTimes();
    }
}