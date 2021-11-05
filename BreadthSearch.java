import java.util.ArrayDeque;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BreadthSearch extends BaseSearchStructure {
    private final SuccessorAdjacencyList successorAdjacencyList;
    private final Queue<Vertice> discoveredVertices;
    private Vertice currentVertice;
    private int verticeIndexInVerticeSet;
    private Map<Vertice, Float> heuristics;

    private static Pattern PATTERN_TO_VALIDATE_THE_HEURISTICS = Pattern.compile(
            "^\\s*\\[\\s*(?:\\s*\\w+\\s*:\\s*\\d+(?:\\.\\d+)?\\s*,)*\\s*\\w+\\s*:\\s*\\d+(?:\\.\\d+)?\\s*\\]\\s*",
            Pattern.MULTILINE);

    protected BreadthSearch(Graph graph, SuccessorAdjacencyList graphSuccessorAdjacencyList) {
        super(graph);
        successorAdjacencyList = graphSuccessorAdjacencyList;

        discoveredVertices = new ArrayDeque<>();

        makeBreadthSearchAndComputeTimesInArrays();
    }

    protected BreadthSearch(Graph graph, SuccessorAdjacencyList graphSuccessorAdjacencyList,
        String heuristicsRepresentation) throws IllegalArgumentException {
        super(graph);

        heuristics = new LinkedHashMap<>(relatedGraph.numberOfVertices);
        checkIfHeuristicsRepresentationIsValid(heuristicsRepresentation);

        successorAdjacencyList = graphSuccessorAdjacencyList;

        discoveredVertices = new ArrayDeque<>(relatedGraph.numberOfVertices);

        makeBreadthSearchAndComputeTimesInArrays();
    }

    private void checkIfHeuristicsRepresentationIsValid(String heuristicsRepresentation) throws IllegalArgumentException {
        if (heuristicsRepresentation == null || heuristicsRepresentation.isEmpty())
            return;

        Matcher matcher = PATTERN_TO_VALIDATE_THE_HEURISTICS.matcher(heuristicsRepresentation);

        if (!matcher.matches()) 
            throw new IllegalArgumentException("The heuristic " + heuristicsRepresentation
            + " is not a valid heuristic. A valid heuristic is something like [ a: 1, b: 2.3, c: 3.0 ]" 
            + " where the vertice is on the left of the : and the heuristic value is on the rigth.");

        String[] partsOfHeuristics = heuristicsRepresentation.substring(1, heuristicsRepresentation.length() - 1)
                .split(",");
        
        iterateOverPartsOfHeuristicAndFillCorrespondingAttribute(partsOfHeuristics);
    }

    private void iterateOverPartsOfHeuristicAndFillCorrespondingAttribute(String[] partsOfHeuristics) 
        throws IllegalArgumentException {
        String[] verticeNameAndHeuristicValue;
        Optional<Vertice> verticeFound;
        int currentVerticeIndex;

        for (Vertice vertice : relatedGraph.vertices.keySet()) {
            currentVerticeIndex = relatedGraph.vertices.get(vertice);
            if (currentVerticeIndex >= partsOfHeuristics.length) {
                heuristics.put(vertice, 0F);
            } else {
                verticeNameAndHeuristicValue = partsOfHeuristics[currentVerticeIndex].trim().split(":");
                verticeNameAndHeuristicValue[0] = verticeNameAndHeuristicValue[0].trim();
                verticeNameAndHeuristicValue[1] = verticeNameAndHeuristicValue[1].trim();
                verticeFound = getTheVerticeWithThisName(verticeNameAndHeuristicValue[0]);

                if (verticeFound.isEmpty()) {
                    throw new IllegalArgumentException("The vertice " + verticeNameAndHeuristicValue[0]
                            + " is not in the vertice set." + " The vertice set is: " + relatedGraph.vertices.keySet());
                }

                heuristics.put(verticeFound.get(), Float.parseFloat(verticeNameAndHeuristicValue[1]));
            }
        }
    }

    private Optional<Vertice> getTheVerticeWithThisName(String name) {
        return relatedGraph.vertices.keySet().stream()
                .filter(vertice -> vertice.name.compareTo(name) == 0).findFirst();
    }

    private void makeBreadthSearchAndComputeTimesInArrays() {
        for (int timeNumber = 1; timeNumber <= 2 * relatedGraph.numberOfVertices; timeNumber++) {
            if (discoveredVertices.isEmpty()) {
                currentVertice = getNextNotDiscoveredVerticeBasedOnVerticeSet();
                verticeIndexInVerticeSet = currentVertice != null ? relatedGraph.vertices.get(currentVertice) : -1;
            } else {
                currentVertice = discoveredVertices.poll();
                verticeIndexInVerticeSet = relatedGraph.vertices.get(currentVertice);
            }

            if (discoveryTimes[verticeIndexInVerticeSet] == -1) {
                discoveryTimes[verticeIndexInVerticeSet] = timeNumber;

                for (Vertice vertice : successorAdjacencyList.adjacencyList.get(currentVertice)) {
                    if (!discoveredVertices.contains(vertice) && discoveryTimes[relatedGraph.vertices.get(vertice)] == -1) {
                        discoveredVertices.add(vertice);
                    }
                }

                discoveredVertices.add(currentVertice);
            } else if (endTimes[verticeIndexInVerticeSet] == -1) {
                endTimes[verticeIndexInVerticeSet] = timeNumber;
            }
        }
    }
}