package searches;

import java.util.LinkedHashMap;
import java.util.Map;

import core.*;
import core.abstractions.AbstractVertice;

public class GraphHeuristics {
    private final Graph relatedGraph;
    private final String heuristicsStringRepresentation;
    public final Map<AbstractVertice, Integer> verticesAndTheirHeuristics;

    public GraphHeuristics(Graph graph, String heuristicsRepresentation) throws IllegalArgumentException {
        relatedGraph = graph;

        verticesAndTheirHeuristics = new LinkedHashMap<core.abstractions.AbstractVertice, Integer>(relatedGraph.numberOfVertices);
        heuristicsStringRepresentation = heuristicsRepresentation;

        GraphHeuristicsValidator.validateHeuristicsRepresentation(heuristicsRepresentation);

        fillHeuristicsForEachVertice();
    }

    private String[] splitHeuristicRepresentation() {
        String heuristicsWithoutBracket = heuristicsStringRepresentation.substring(1,
                heuristicsStringRepresentation.length() - 1);
        return heuristicsWithoutBracket.split(",");
    }

    private void fillHeuristicsForEachVertice() throws IllegalArgumentException {
        AbstractVertice verticeFound;
        String[] partsOfHeuristics = splitHeuristicRepresentation();
        String[] verticeNameAndHeuristicValue;

        for (String part : partsOfHeuristics) {
            verticeNameAndHeuristicValue = splitPartOfHeuristics(part);

            verticeFound = relatedGraph.getVerticeByRepresentation(verticeNameAndHeuristicValue[0]);

            throwExceptionIfVerticeIsNull(verticeFound, verticeNameAndHeuristicValue[0]);

            verticesAndTheirHeuristics.put(verticeFound, Integer.parseInt(verticeNameAndHeuristicValue[1]));
        }

        for (Vertice vertice : relatedGraph.vertices) {
            verticesAndTheirHeuristics.putIfAbsent(vertice, 0);
        }
    }

    private String[] splitPartOfHeuristics(String partOfHeuristic) {
        String[] verticeNameAndHeuristicValue = partOfHeuristic.trim().split(":");
        verticeNameAndHeuristicValue[0] = verticeNameAndHeuristicValue[0].trim();
        verticeNameAndHeuristicValue[1] = verticeNameAndHeuristicValue[1].trim();
        return verticeNameAndHeuristicValue;
    }

    private void throwExceptionIfVerticeIsNull(AbstractVertice verticeFound, String verticeName)
            throws IllegalArgumentException {
        if (verticeFound == null) {
            throw new IllegalArgumentException("The vertice " + verticeName + " is not in the vertice set."
                    + " The vertice set is: " + relatedGraph.vertices);
        }
    }
}
