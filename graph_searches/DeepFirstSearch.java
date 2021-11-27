package aboutGraphs.searches;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import aboutGraphs.core.*;
import aboutGraphs.representations.SuccessorAdjacencyList;

public class DeepFirstSearch extends BaseSearchStructure {
    private final Map<Vertice, List<Vertice>> successorAdjacencyList;
    private final Graph relatedGraph;
    private Stack<Vertice> verticesToBeExplored;

    public DeepFirstSearch(Graph graph) {
        super(graph);
        successorAdjacencyList = new SuccessorAdjacencyList(graph).adjacencyList;
        relatedGraph = graph;
    }

    public List<Vertice> getPathBetweenVertices(String startVerticeName, String endVerticeName)
            throws IllegalArgumentException {

        Vertice startVertice = relatedGraph.getVerticeByName(startVerticeName);
        Vertice endVertice = relatedGraph.getVerticeByName(endVerticeName);

        throwExceptionIfVerticeIsNull(startVertice, startVerticeName);
        throwExceptionIfVerticeIsNull(endVertice, endVerticeName);

        return getPathBetweenVertices(startVertice, endVertice);
    }

    private void throwExceptionIfVerticeIsNull(Vertice vertice, String verticeName) throws IllegalArgumentException {
        if (vertice == null) {
            throw new IllegalArgumentException("The vertice  " + verticeName + "  is not in the vertice set."
                    + "\nThe vertice set is: " + relatedGraph.vertices);
        }
    }

    public List<Vertice> getPathBetweenVertices(Vertice startVertice, Vertice endVertice) {
        initializeTimeArrays();
        int timeNumber = 1, verticeIndexInVerticeSet;
        Vertice currentVertice;
        List<Vertice> pathBetweenVertices = new ArrayList<>();

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
                pathBetweenVertices.remove(currentVertice);
                endTimes[verticeIndexInVerticeSet] = timeNumber++;
            }
        } while (!currentVertice.equals(endVertice) && !verticesToBeExplored.isEmpty());

        return pathBetweenVertices;
    }

    public void computeTimes() {
        Vertice currentVertice;
        int verticeIndexInVerticeSet;

        verticesToBeExplored = new Stack<>();

        for (int timeNumber = 1; timeNumber <= 2 * relatedGraph.numberOfVertices; timeNumber++) {
            currentVertice = getNextVerticeToBeExplored();
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

    private Vertice getNextVerticeToBeExplored() {
        if (verticesToBeExplored.isEmpty())
            return getNextNotDiscoveredVerticeBasedOnVerticeSet();
        else
            return verticesToBeExplored.pop();
    }

    private void addVerticeChildrenToNotExploredVertices(Vertice vertice) {
        List<Vertice> verticeChildren = getReversedVerticeChildren(vertice);

        for (Vertice v : verticeChildren) {
            if (canAddVerticeToNotExploredVertices(v))
                verticesToBeExplored.add(v);
        }
    }

    private List<Vertice> getReversedVerticeChildren(Vertice vertice) {
        var verticeChildren = successorAdjacencyList.get(vertice);
        Collections.reverse(verticeChildren);
        return verticeChildren;
    }

    private void addVerticeChildrenToNotExploredVerticesAndClassifyEdgesAlongTheWay(Vertice vertice) {
        List<Vertice> verticeChildren = getReversedVerticeChildren(vertice);

        for (Vertice v : verticeChildren) {
            if (canAddVerticeToNotExploredVertices(v))
                verticesToBeExplored.add(v);

            classifyTheEdge(relatedGraph.getDirectedEdgeWithThisVertices(vertice, v));
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