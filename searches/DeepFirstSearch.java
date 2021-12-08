package searches;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Collections;

import core.abstractions.AbstractGraph;
import core.abstractions.AbstractVertice;
import representations.SuccessorAdjacencyList;

public class DeepFirstSearch extends AbstractSearch {
    private final Map<AbstractVertice, List<AbstractVertice>> successorAdjacencyList;
    private final AbstractGraph relatedGraph;
    private Stack<AbstractVertice> verticesToBeExplored;
    private List<AbstractVertice> pathBetweenVertices;

    public DeepFirstSearch(AbstractGraph graph) {
        super(graph);
        successorAdjacencyList = new SuccessorAdjacencyList(graph).adjacencyList;
        relatedGraph = graph;
    }

    public DeepFirstSearch(AbstractGraph graph, SuccessorAdjacencyList successorAdjacencyList) {
        super(graph);
        this.successorAdjacencyList = successorAdjacencyList.adjacencyList;
        relatedGraph = graph;
    }

    public List<AbstractVertice> getPathBetweenVertices(String startVerticeName, String endVerticeName)
            throws IllegalArgumentException {

        AbstractVertice startVertice = relatedGraph.getVerticeByRepresentation(startVerticeName);
        AbstractVertice endVertice = relatedGraph.getVerticeByRepresentation(endVerticeName);

        throwExceptionIfVerticeIsNull(startVertice, startVerticeName);
        throwExceptionIfVerticeIsNull(endVertice, endVerticeName);

        return getPathBetweenVertices(startVertice, endVertice);
    }

    private void throwExceptionIfVerticeIsNull(AbstractVertice vertice, String verticeName) throws IllegalArgumentException {
        if (vertice == null) {
            throw new IllegalArgumentException("The vertice  " + verticeName + "  is not in the vertice set."
                    + "\nThe vertice set is: " + Arrays.toString(relatedGraph.getVertices()));
        }
    }

    public List<AbstractVertice> getPathBetweenVertices(AbstractVertice startVertice, AbstractVertice endVertice) {
        initializeTimeArrays();

        int timeNumber = 1, verticeIndexInVerticeSet;
        AbstractVertice currentVertice;

        pathBetweenVertices = new ArrayList<>();
        verticesToBeExplored = new Stack<>();

        verticesToBeExplored.add(startVertice);

        do {
            currentVertice = verticesToBeExplored.pop();
            verticeIndexInVerticeSet = relatedGraph.indexOfVertice(currentVertice);

            if (verticeIndexInVerticeSet == -1)
                return pathBetweenVertices;

            if (discoveryTimes[verticeIndexInVerticeSet] == -1)
                handleVerticeNotDiscovered(currentVertice, verticeIndexInVerticeSet, timeNumber);
            else
                handleVerticeAlreadyDiscovered(currentVertice, verticeIndexInVerticeSet, timeNumber);

            timeNumber++;
        } while (!currentVertice.equals(endVertice) && !verticesToBeExplored.isEmpty());

        return pathBetweenVertices;
    }

    private void handleVerticeNotDiscovered(AbstractVertice vertice, int verticeIndex, int timeNumber) {
        discoveryTimes[verticeIndex] = timeNumber;

        if (pathBetweenVertices != null)
            pathBetweenVertices.add(vertice);

        verticesToBeExplored.add(vertice);

        addVerticeChildrenToNotExploredVertices(vertice);
    }

    private void handleVerticeAlreadyDiscovered(AbstractVertice vertice, int verticeIndex, int timeNumber) {
        if (pathBetweenVertices != null)
            pathBetweenVertices.remove(vertice);

        endTimes[verticeIndex] = timeNumber;
    }

    @Override
    public void computeTimes() {
        AbstractVertice currentVertice;
        int verticeIndexInVerticeSet;

        verticesToBeExplored = new Stack<>();
        pathBetweenVertices = null;

        for (int timeNumber = 1; timeNumber <= 2 * relatedGraph.getNumberOfVertices(); timeNumber++) {
            currentVertice = getNextVerticeToBeExplored();
            verticeIndexInVerticeSet = relatedGraph.indexOfVertice(currentVertice);

            if (discoveryTimes[verticeIndexInVerticeSet] == -1)
                handleVerticeNotDiscovered(currentVertice, verticeIndexInVerticeSet, timeNumber);
            else
                handleVerticeAlreadyDiscovered(currentVertice, verticeIndexInVerticeSet, timeNumber);
        }
    }

    private AbstractVertice getNextVerticeToBeExplored() {
        if (verticesToBeExplored.isEmpty())
            return getNextNotDiscoveredVertice();
        else
            return verticesToBeExplored.pop();
    }

    private void addVerticeChildrenToNotExploredVertices(AbstractVertice vertice) {
        List<AbstractVertice> verticeChildren = getReversedVerticeChildren(vertice);

        for (AbstractVertice v : verticeChildren) {
            if (canAddVerticeToNotExploredVertices(v))
                verticesToBeExplored.add(v);
        }
    }

    private boolean canAddVerticeToNotExploredVertices(AbstractVertice vertice) {
        return discoveryTimes[relatedGraph.indexOfVertice(vertice)] == -1 && !verticesToBeExplored.contains(vertice);
    }

    private List<AbstractVertice> getReversedVerticeChildren(AbstractVertice vertice) {
        var verticeChildren = successorAdjacencyList.get(vertice);
        Collections.reverse(verticeChildren);
        return verticeChildren;
    }

    @Override
    public void showTimes() {
        System.out.println("\n\tDEEP SEARCH TIMES");
        super.showTimes();
    }
}