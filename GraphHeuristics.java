import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GraphHeuristics {
    private final Graph relatedGraph;
    private final String[] partsOfHeuristics;
    public final Map<Vertice, Float> verticesAndTheirHeuristics;
    private String[] verticeNameAndHeuristicValue;
    private Optional<Vertice> verticeFound;
    private int currentVerticeIndex;

    public GraphHeuristics(Graph graph, String heuristicsRepresentation) {
        relatedGraph = graph;

        verticesAndTheirHeuristics = new LinkedHashMap<>(relatedGraph.numberOfVertices);

        checkIfHeuristicsRepresentationIsValid(heuristicsRepresentation);

        partsOfHeuristics = heuristicsRepresentation.substring(1, heuristicsRepresentation.length() - 1).split(",");
        
        fillHeuristicsForEachVertice();
    }

    private static Pattern PATTERN_TO_VALIDATE_THE_HEURISTICS = Pattern.compile(
            "^\\s*\\[\\s*(?:\\s*\\w+\\s*:\\s*\\d+(?:\\.\\d+)?\\s*,)*\\s*\\w+\\s*:\\s*\\d+(?:\\.\\d+)?\\s*\\]\\s*",
            Pattern.MULTILINE);

    private void checkIfHeuristicsRepresentationIsValid(String heuristicsRepresentation) throws IllegalArgumentException {
        if (heuristicsRepresentation == null || heuristicsRepresentation.isEmpty())
            return;

        Matcher matcher = PATTERN_TO_VALIDATE_THE_HEURISTICS.matcher(heuristicsRepresentation);

        if (!matcher.matches()) 
            throw new IllegalArgumentException("The heuristic " + heuristicsRepresentation
            + " is not a valid heuristic. A valid heuristic is something like [ a: 1, b: 2.3, c: 3.0 ]" 
            + " where the vertice is on the left of the : and the heuristic value is on the rigth.");
    }

    private void fillHeuristicsForEachVertice() 
        throws IllegalArgumentException {
        for (Vertice vertice : relatedGraph.verticesAndTheirIndices.keySet()) {
            currentVerticeIndex = relatedGraph.verticesAndTheirIndices.get(vertice);

            if (currentVerticeIndex >= partsOfHeuristics.length) {
                verticesAndTheirHeuristics.put(vertice, 0F);
            } else {
                fillVerticeNameAndHeuristicValueAccordingToCurrentVerticeIndex();

                verticeFound = getTheVerticeWithThisName(verticeNameAndHeuristicValue[0]);

                checkIfVerticeFoundIsEmpty();

                verticesAndTheirHeuristics.put(verticeFound.get(), Float.parseFloat(verticeNameAndHeuristicValue[1]));
            }
        }
    }

    private void fillVerticeNameAndHeuristicValueAccordingToCurrentVerticeIndex() {
        verticeNameAndHeuristicValue = partsOfHeuristics[currentVerticeIndex].trim().split(":");
        verticeNameAndHeuristicValue[0] = verticeNameAndHeuristicValue[0].trim();
        verticeNameAndHeuristicValue[1] = verticeNameAndHeuristicValue[1].trim();
    }

    private void checkIfVerticeFoundIsEmpty() {
        if (verticeFound.isEmpty()) {
            throw new IllegalArgumentException("The vertice " + verticeNameAndHeuristicValue[0]
                    + " is not in the vertice set." + " The vertice set is: "
                    + relatedGraph.verticesAndTheirIndices.keySet());
        }
    }

    private Optional<Vertice> getTheVerticeWithThisName(String name) {
        return relatedGraph.verticesAndTheirIndices.keySet().stream()
                .filter(vertice -> vertice.name.compareTo(name) == 0).findFirst();
    }
}
